import {Component, inject, input} from '@angular/core';
import {MovieListPagination} from '../movie-list-pagination/movie-list-pagination';
import {MovieListItem} from '../movie-list-item/movie-list-item';
import {ActivatedRoute, Router} from '@angular/router';
import {MoviePage} from '../../../../../core/models/movie-page.model';

@Component({
  selector: 'app-movie-list-results',
  imports: [
    MovieListPagination,
    MovieListItem
  ],
  templateUrl: './movie-list-results.html',
})
export class MovieListResults {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  moviePage = input<MoviePage>({
    page: 0,
    pageSize: 0,
    totalElements: 0,
    totalPages: 0,
    results: []
  });

  goToPage(pageNumber: number): void {
    void this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { page: pageNumber },
      queryParamsHandling: 'merge',
      replaceUrl: true
    });
  }
}
