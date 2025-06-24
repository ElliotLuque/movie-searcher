import {inject, Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, Observable, of, tap} from 'rxjs';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = environment.baseApiUrl + '/auth';
  private readonly http = inject(HttpClient)

  readonly status = signal<boolean>(false);

  constructor() {
    this.refreshStatus();
  }

  login(): Observable<void> {
    return this.http
      .post<void>(`${this.baseUrl}/login`, {}, { withCredentials: true })
      .pipe(
        tap(() => this.refreshStatus())
      );
  }

  logout(): void {
    this.http
      .post<void>(`${this.baseUrl}/logout`, {}, { withCredentials: true })
      .subscribe({ next: () => this.status.set(false) });
  }

  private refreshStatus(): void {
    this.http
      .get<void>(`${this.baseUrl}/status`, {
        observe: 'response',
        withCredentials: true,
      })
      .pipe(
        map(res => res.ok),
        catchError(() => of(false))
      )
      .subscribe(v => this.status.set(v));
  }
}
