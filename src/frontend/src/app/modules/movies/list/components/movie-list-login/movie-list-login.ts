import {Component, inject} from '@angular/core';
import {AuthService} from '../../../../../core/services/auth-service';
import {LoaderCircle, LogIn, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-movie-list-login',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-list-login.html',
})
export class MovieListLogin {
  protected readonly LogIn = LogIn;
  protected readonly LoaderCircle = LoaderCircle;

  private readonly auth = inject(AuthService);
  loading: boolean = false;

  login() {
    this.loading = true;
    this.auth.login().subscribe({
      next: () => this.loading = false,
      error: () => this.loading = false,
    });
  }

}
