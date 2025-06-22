import { Routes } from '@angular/router';
import {MovieListPage} from './modules/movies/list/movie-list-page.component';
import {MovieDetailsPage} from './modules/movies/pages/details/movie-details-page.component';

export const routes: Routes = [
  { path: '', component: MovieListPage },
  { path: 'movies/:imdbId', component: MovieDetailsPage },
];
