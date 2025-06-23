import {Component, input} from '@angular/core';
import {MoviePageItem} from '../../../../../core/models/movie-page.model';
import {RouterLink} from '@angular/router';
import {MovieMissingImage} from '../../../../../shared/components/movie-missing-image/movie-missing-image';

@Component({
  selector: 'app-movie-list-item',
  imports: [
    RouterLink,
    MovieMissingImage
  ],
  templateUrl: './movie-list-item.html',
})
export class MovieListItem {
  readonly movie = input.required<MoviePageItem>();
}
