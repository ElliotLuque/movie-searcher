import {Component, input} from '@angular/core';
import {Movie} from '../../../../../core/models/movie.model';
import {MovieInformationDetail} from '../movie-information-detail/movie-information-detail';
import {User, Globe} from 'lucide-angular';

@Component({
  selector: 'app-movie-information',
  imports: [
    MovieInformationDetail
  ],
  templateUrl: './movie-information-section.component.html',
})
export class MovieInformationSection {
  protected readonly User = User;
  protected readonly Globe = Globe;

  readonly movie  = input<Movie>();
}
