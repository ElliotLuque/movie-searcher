import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from '../../../../core/services/auth-service';
import {MovieListPlaceholderSearch} from '../components/movie-list-placeholder-search/movie-list-placeholder-search';
import {ActivatedRoute, Router} from '@angular/router';
import {MovieListLogin} from '../components/movie-list-login/movie-list-login';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {catchError, debounceTime, distinctUntilChanged, map, of, shareReplay, startWith, switchMap} from 'rxjs';
import {MovieApiService} from '../../../../core/services/movie-api.service';
import {AsyncPipe} from '@angular/common';
import {MoviePage} from '../../../../core/models/movie-page.model';
import {MovieListNoResults} from '../components/movie-list-no-results/movie-list-no-results';
import {MovieListLoading} from '../components/movie-list-loading/movie-list-loading';
import {MovieListError} from '../components/movie-list-error/movie-list-error';
import {MovieSearchBox} from '../../../../shared/components/movie-search-box/movie-search-box.component';
import {MovieListHeader} from '../components/movie-list-header/movie-list-header';
import {MovieListResultsInfo} from '../components/movie-list-results-info/movie-list-results-info';
import {MovieListResults} from '../components/movie-list-results/movie-list-results';

type PageState =
  | { state: 'idle' }
  | { state: 'loading' }
  | { state: 'error'; error: any }
  | { state: 'loaded'; page: MoviePage };

@Component({
  selector: 'app-movie-list',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    AsyncPipe,
    MovieSearchBox,
    MovieListPlaceholderSearch,
    MovieListLogin,
    MovieListNoResults,
    MovieListLoading,
    MovieListError,
    MovieListHeader,
    MovieListResultsInfo,
    MovieListResults
  ],
  templateUrl: './movie-list-page.component.html',
})
export class MovieListPage implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly auth= inject(AuthService);
  private readonly movies = inject(MovieApiService);

  readonly isLoggedIn = this.auth.status;
  readonly searchControl = new FormControl<string>('', { nonNullable: true });

  moviesPageState$ = this.route.queryParamMap.pipe(
    map(params => ({
      query: (params.get('search') ?? '').trim(),
      page : +(params.get('page') ?? 1),
    })),
    distinctUntilChanged(
      (a, b) => a.query === b.query && a.page === b.page
    ),
    switchMap(({ query, page }) => {
      if (!query) {
        return of<PageState>({ state: 'idle' });
      }

      return this.movies.searchMovies(query, page).pipe(
        map(page => ({ state: 'loaded', page } as const)),
        startWith({ state: 'loading' } as const),
        catchError(error => of({ state: 'error', error } as const))
      );
    }),
    shareReplay({ bufferSize: 1, refCount: true })
  );

  readonly pageRange$ = this.moviesPageState$.pipe(
    map(pageState => {
      if (pageState.state !== 'loaded') return null;

      const page = pageState.page.page;
      const pageSize = pageState.page.pageSize;
      const total = pageState.page.totalElements;
      const count = pageState.page.results.length;

      const from = (page - 1) * pageSize + 1;
      const to   = from + count - 1;

      return { from, to, total };
    }),
    shareReplay({ bufferSize: 1, refCount: true })
  );

  readonly isLoading$ = this.moviesPageState$.pipe(
    map(s => s.state === 'loading')
  );

  readonly hasError$ = this.moviesPageState$.pipe(
    map(s => s.state === 'error')
  );

  readonly resultsPage$ = this.moviesPageState$.pipe(
    map(s => (s.state === 'loaded' ? s.page : null))
  );

  constructor() { }

  ngOnInit(): void {
    this.route.queryParamMap
      .pipe(distinctUntilChanged(
        (a, b) => a.get('search') === b.get('search')
      ))
      .subscribe(params => {
        const q = params.get('search') ?? '';
        this.searchControl.setValue(q, { emitEvent: false });
      });

    this.searchControl.valueChanges
      .pipe(
        debounceTime(250),
        distinctUntilChanged()
      )
      .subscribe(value => {
        const trimmed = value?.trim();
        const queryParams = trimmed
          ? { search: trimmed, page: 1 }
          : { search: null, page: null };

        void this.router.navigate([], {
          relativeTo: this.route,
          queryParams,
          queryParamsHandling: 'merge'
        });
      });
    }

  logout(): void {
    this.searchControl.setValue('');
    this.auth.logout();
    void this.router.navigate(['/'], {
      queryParams: {},
      replaceUrl: true
    });
  }

  get isSearching(): boolean {
    const search = this.route.snapshot.queryParamMap.get('search');
    return !!search && search.trim() !== '';
  }
}
