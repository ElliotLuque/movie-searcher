import { Component, inject } from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {ArrowLeftIcon, Calendar, Clock, LucideAngularModule, Star} from 'lucide-angular';
import {MovieApiService} from '../../../../core/services/movie-api.service';
import {Movie} from '../../../../core/models/movie.model';
import {MovieMissingImage} from '../../../../shared/components/movie-missing-image/movie-missing-image';
import {NgOptimizedImage} from '@angular/common';
import {MovieGenre} from '../../../../shared/components/movie-genre/movie-genre';
import {MoviePlotSection} from '../components/movie-plot-section/movie-plot-section.component';
import {MovieInformationSection} from '../components/movie-information-section/movie-information-section.component';
import {MovieAttribute} from '../components/movie-attribute/movie-attribute';
import {Title} from '@angular/platform-browser';

@Component({
  selector: 'app-movie-details',
  imports: [
    RouterLink,
    LucideAngularModule,
    MovieMissingImage,
    NgOptimizedImage,
    MovieGenre,
    MoviePlotSection,
    MovieInformationSection,
    MovieAttribute
  ],
  templateUrl: './movie-details-page.component.html',
})
export class MovieDetailsPage {
  // Icons
  protected readonly Star = Star;
  protected readonly ArrowLeftIcon = ArrowLeftIcon;
  protected readonly Calendar = Calendar;
  protected readonly Clock = Clock;

  private readonly api = inject(MovieApiService);
  private route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly titleService = inject(Title)

  movie?: Movie;

  constructor() {
    const imdbId = this.route.snapshot.paramMap.get('imdbId');

    if (imdbId) {
      this.api.getMovie(imdbId).subscribe({
        next: (movie) => {
          this.movie = movie
          this.titleService.setTitle(`${movie.title} - Movie Searcher`);
        },
        error: (err) => {
            switch (err.status) {
              case 401:
                void this.router.navigate(['/']);
                break;
              case 400:
                void this.router.navigate(['/invalid-search']);
                break;
              case 404:
                void this.router.navigate(['/not-found']);
                break;
              default:
                void this.router.navigate(['/error']);
                break;
            }
        }
      });
    }
  }
}
