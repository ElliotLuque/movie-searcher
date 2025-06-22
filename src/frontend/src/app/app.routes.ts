import { Routes } from '@angular/router';
import {MovieListPage} from './modules/movies/list/movie-list-page.component';
import {MovieDetailsPage} from './modules/movies/details/pages/movie-details-page.component';
import {NotFoundPage} from './shared/pages/not-found-page/not-found-page';

export const routes: Routes = [
  { path: '', component: MovieListPage },
  { path: 'movies/:imdbId', component: MovieDetailsPage },
  { path: '**', component: NotFoundPage },
];
