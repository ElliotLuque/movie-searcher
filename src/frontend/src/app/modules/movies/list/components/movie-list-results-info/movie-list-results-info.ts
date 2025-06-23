import { Component, input } from '@angular/core';

@Component({
  selector: 'app-movie-list-results-info',
  imports: [],
  templateUrl: './movie-list-results-info.html',
})
export class MovieListResultsInfo {
  total = input<number>(0);
  from = input<number>(0);
  to = input<number>(0);
}
