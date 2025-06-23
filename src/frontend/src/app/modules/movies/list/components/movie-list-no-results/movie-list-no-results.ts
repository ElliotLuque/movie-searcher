import {Component, input} from '@angular/core';
import {Film, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-movie-list-no-results',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-list-no-results.html',
})
export class MovieListNoResults {
  protected readonly Film = Film;

  readonly query = input<string | null>();
}
