import { Component, input } from '@angular/core';
import {LucideAngularModule, LucideIconData} from 'lucide-angular';

@Component({
  selector: 'app-movie-attribute',
  imports: [
    LucideAngularModule,
  ],
  templateUrl: './movie-attribute.html',
})
export class MovieAttribute {
  icon = input<LucideIconData>();
  iconColor = input<string | undefined>();
  text = input<string>();
}
