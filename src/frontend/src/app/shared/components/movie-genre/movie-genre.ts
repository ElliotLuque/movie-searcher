import {Component, input} from '@angular/core';

@Component({
  selector: 'app-movie-genre',
  imports: [],
  templateUrl: './movie-genre.html',
})
export class MovieGenre {
  readonly genre = input<string>();
}
