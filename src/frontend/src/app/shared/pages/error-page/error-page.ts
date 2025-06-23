import { Component } from '@angular/core';
import {ArrowLeftIcon, LucideAngularModule, ServerCrash} from 'lucide-angular';

@Component({
  selector: 'app-error-page',
  imports: [
    LucideAngularModule
  ],
  templateUrl: './error-page.html',
})
export class ErrorPage {
  protected readonly ServerCrash = ServerCrash;
  protected readonly ArrowLeftIcon = ArrowLeftIcon;
}
