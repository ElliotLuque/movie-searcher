import { Routes } from '@angular/router';
import {MovieListPage} from './modules/movies/list/pages/movie-list-page.component';
import {MovieDetailsPage} from './modules/movies/details/pages/movie-details-page.component';
import {NotFoundPage} from './shared/pages/not-found-page/not-found-page';
import {InvalidSearchPage} from './shared/pages/invalid-search-page/invalid-search-page.component';
import {ErrorPage} from './shared/pages/error-page/error-page';

export const routes: Routes = [
  { path: '', component: MovieListPage },
  { path: 'movies/:imdbId', component: MovieDetailsPage },
  { path: 'invalid-search', component: InvalidSearchPage },
  { path: 'not-found', component: NotFoundPage },
  { path: '**', component: ErrorPage },
];
