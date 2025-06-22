import { Component } from '@angular/core';
import {Film, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-movie-missing-image',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-missing-image.html',
})
export class MovieMissingImage {
  protected readonly Film = Film;
}
