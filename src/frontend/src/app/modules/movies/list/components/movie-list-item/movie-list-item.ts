import {Component, input} from '@angular/core';
import {MoviePageItem} from '../../../../../core/models/movie-page.model';
import {RouterLink} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';
import {ImageFallback} from '../../../../../core/directives/image-fallback';

@Component({
  selector: 'app-movie-list-item',
  imports: [
    RouterLink,
    NgOptimizedImage,
    ImageFallback
  ],
  templateUrl: './movie-list-item.html',
})
export class MovieListItem {
  readonly movie = input.required<MoviePageItem>();
}
