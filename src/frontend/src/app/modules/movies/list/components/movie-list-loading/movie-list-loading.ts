import { Component } from '@angular/core';
import {LoaderCircle, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-movie-list-loading',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-list-loading.html',
})
export class MovieListLoading {
  protected readonly LoaderCircle = LoaderCircle;
}
