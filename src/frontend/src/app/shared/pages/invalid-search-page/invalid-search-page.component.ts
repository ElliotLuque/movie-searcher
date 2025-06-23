import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {ArrowLeftIcon, CircleX, LucideAngularModule} from 'lucide-angular';

@Component({
  selector: 'app-invalid-search',
  imports: [
    RouterLink,
    LucideAngularModule
  ],
  templateUrl: './invalid-search-page.component.html',
})
export class InvalidSearchPage {
  protected readonly ArrowLeftIcon = ArrowLeftIcon;
  protected readonly CircleX = CircleX;
}
