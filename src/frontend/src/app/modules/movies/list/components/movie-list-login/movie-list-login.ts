import {Component, inject} from '@angular/core';
import {AuthService} from '../../../../../core/services/auth-service';
import {LogIn, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-movie-list-login',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-list-login.html',
})
export class MovieListLogin {
  private readonly auth = inject(AuthService);
  protected readonly LogIn = LogIn;

  login() {
    this.auth.login();
  }
}
