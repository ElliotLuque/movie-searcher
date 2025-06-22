import { Component } from '@angular/core';
import {Film, LucideAngularModule, Search} from 'lucide-angular';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-not-found-page',
  imports: [
    LucideAngularModule,
    RouterLink
  ],
  templateUrl: './not-found-page.html',
})
export class NotFoundPage {
  protected readonly Film = Film;
  protected readonly Search = Search;

  protected readonly popularMovies = ["Star Wars", "Matrix", "Pirates of the Caribbean"];
}
