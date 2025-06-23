import {Component, inject} from '@angular/core';
import {AuthService} from '../../../../core/services/auth-service';
import {Film, LucideAngularModule, Search, LogOut} from 'lucide-angular';
import {MovieListPlaceholderSearch} from '../components/movie-list-placeholder-search/movie-list-placeholder-search';
import {ActivatedRoute, Router} from '@angular/router';
import {MovieListLogin} from '../components/movie-list-login/movie-list-login';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {catchError, debounceTime, distinctUntilChanged, map, of, shareReplay, startWith, switchMap} from 'rxjs';
import {MovieApiService} from '../../../../core/services/movie-api.service';
import {AsyncPipe} from '@angular/common';
import {MoviePage} from '../../../../core/models/movie-page.model';
import {MovieListItem} from '../components/movie-list-item/movie-list-item';
import {MovieListNoResults} from '../components/movie-list-no-results/movie-list-no-results';
import {MovieListLoading} from '../components/movie-list-loading/movie-list-loading';
import {MovieListError} from '../components/movie-list-error/movie-list-error';
import {MovieListPagination} from '../components/movie-list-pagination/movie-list-pagination';

const PAGE_SIZE = 10
type PageState =
  | { state: 'idle' }
  | { state: 'loading' }
  | { state: 'error'; error: any }
  | { state: 'loaded'; page: MoviePage };

@Component({
  selector: 'app-movie-list',
  imports: [
    LucideAngularModule,
    MovieListPlaceholderSearch,
    MovieListLogin,
    FormsModule,
    ReactiveFormsModule,
    AsyncPipe,
    MovieListItem,
    MovieListNoResults,
    MovieListLoading,
    MovieListError,
    MovieListPagination
  ],
  templateUrl: './movie-list-page.component.html',
})
export class MovieListPage {
  // Icons
  protected readonly Film = Film;
  protected readonly Search = Search;
  protected readonly LogOut = LogOut;

  protected readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly auth= inject(AuthService);
  private readonly movies = inject(MovieApiService);

  readonly isLoggedIn = this.auth.status;

  readonly searchControl = new FormControl<string>('');

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
      const total = pageState.page.totalElements;
      const count = pageState.page.results.length;

      const from = (page - 1) * PAGE_SIZE + 1; // TODO: Dato desde el servidor
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

  constructor() {
    const firstSearch = this.route.snapshot.queryParamMap.get('search') ?? '';
    this.searchControl.setValue(firstSearch);

    this.searchControl.valueChanges
      .pipe(
        debounceTime(250),
        distinctUntilChanged()
      )
      .subscribe((value) => {
        const trimmed = value?.trim();

        const queryParams = trimmed
          ? { search: trimmed, page: 1}
          : {search: null, page: null};

        void this.router.navigate([], {
          relativeTo: this.route,
          queryParams,
          queryParamsHandling: 'merge',
        });
    })
  }

  logout(): void {
    this.auth.logout();
  }

  goToPage(pageNumber: number): void {
    void this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { page: pageNumber },
      queryParamsHandling: 'merge',
      replaceUrl: true
    });
  }

  get isSearching(): boolean {
    const search = this.route.snapshot.queryParamMap.get('search');
    return !!search && search.trim() !== '';
  }
}
