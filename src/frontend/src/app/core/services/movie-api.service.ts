import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {MoviePage} from '../models/movie-page.model';
import {Movie} from '../models/movie.model';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieApiService {
  private readonly baseUrl = environment.baseApiUrl + '/movies';
  private readonly http = inject(HttpClient)

  constructor() { }

  searchMovies(query: string, page: Number = 1): Observable<MoviePage> {
    return this.http.get<MoviePage>(`${this.baseUrl}?query=${query}&page=${page}`);
  }

  getMovie(imdbId: string): Observable<Movie> {
    return this.http.get<Movie>(`${this.baseUrl}/${imdbId}`);
  }
}
