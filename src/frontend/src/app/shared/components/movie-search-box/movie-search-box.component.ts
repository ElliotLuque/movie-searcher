import {Component, input} from '@angular/core';
import {LucideAngularModule, Search} from 'lucide-angular';
import {FormControl, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-movie-search-box',
  imports: [
    LucideAngularModule,
    ReactiveFormsModule
  ],
  templateUrl: './movie-search-box.component.html',
})
export class MovieSearchBox {
  protected readonly Search = Search;
  readonly searchControl =  input.required<FormControl<string>>();
}
