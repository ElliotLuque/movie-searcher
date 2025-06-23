import { Component } from '@angular/core';
import {CircleAlert, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-movie-list-error',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-list-error.html',
})
export class MovieListError {
  protected readonly CircleAlert = CircleAlert;
}
