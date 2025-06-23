import { Component } from '@angular/core';
import {LucideAngularModule, Search} from 'lucide-angular';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-movie-list-placeholder-search',
  imports: [
    LucideAngularModule,
    RouterLink
  ],
  templateUrl: './movie-list-placeholder-search.html',
})
export class MovieListPlaceholderSearch {
  protected readonly Search = Search;
  protected readonly moviesToSearch = ["Star Wars", "Pulp Fiction", "Pirates of the Caribbean"];
}
