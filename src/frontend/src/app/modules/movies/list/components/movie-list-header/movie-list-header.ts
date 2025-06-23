import {Component, input, output} from '@angular/core';
import {Film, LogOut, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-movie-list-header',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-list-header.html',
})
export class MovieListHeader {
  protected readonly Film = Film;
  protected readonly LogOut = LogOut;

  readonly isLoggedIn = input<boolean>(false);
  readonly logout = output<void>();
}
