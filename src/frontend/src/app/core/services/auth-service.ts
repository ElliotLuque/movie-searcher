import {inject, Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, map, Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/api/v1/auth';
  private readonly http = inject(HttpClient)

  readonly status = signal<boolean>(false);

  constructor() {
    this.refreshStatus();
  }

  login(): void {
    this.http
      .post<void>(`${this.baseUrl}/login`, {}, { withCredentials: true })
      .subscribe({ next: () => this.refreshStatus() });
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
