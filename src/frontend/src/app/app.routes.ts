import { Routes } from '@angular/router';
import {MovieListPage} from './modules/movies/list/pages/movie-list-page.component';
import {MovieDetailsPage} from './modules/movies/details/pages/movie-details-page.component';
import {NotFoundPage} from './shared/pages/not-found-page/not-found-page';
import {InvalidSearchPage} from './shared/pages/invalid-search-page/invalid-search-page.component';
import {ErrorPage} from './shared/pages/error-page/error-page';

export const routes: Routes = [
  { path: '', component: MovieListPage, title: 'Movie Searcher'},
  { path: 'movies/:imdbId', component: MovieDetailsPage, title: 'Movie Details - Movie Searcher' },
  { path: 'invalid-search', component: InvalidSearchPage, title: 'Invalid Search - Movie Searcher' },
  { path: 'not-found', component: NotFoundPage, title: 'Not Found - Movie Searcher' },
  { path: '**', component: ErrorPage, title: 'Error - Movie Searcher' },
];
