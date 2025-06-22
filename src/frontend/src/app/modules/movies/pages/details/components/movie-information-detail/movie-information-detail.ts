import {Component, input} from '@angular/core';
import {LucideAngularModule, LucideIconData} from 'lucide-angular';

@Component({
  selector: 'app-movie-information-detail',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './movie-information-detail.html',
})
export class MovieInformationDetail {
  readonly label = input<string>();
  readonly text = input<string>();
  readonly icon = input<LucideIconData>();
}
