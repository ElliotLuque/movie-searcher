import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/api/v1/auth';
  private readonly http = inject(HttpClient)

  constructor() { }

  login(): Observable<void> {
    console.log('login');
    return this.http.post<void>(`${this.baseUrl}/login`, {});
  }
}
