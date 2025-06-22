import {Component, input} from '@angular/core';

@Component({
  selector: 'app-movie-plot',
  imports: [],
  templateUrl: './movie-plot-section.component.html',
})
export class MoviePlotSection {
  plot = input<string>();
}
