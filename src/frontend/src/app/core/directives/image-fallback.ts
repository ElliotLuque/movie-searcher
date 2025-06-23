import {Directive, ElementRef, HostListener, inject, Renderer2, ViewContainerRef} from '@angular/core';
import {MovieMissingImage} from '../../shared/components/movie-missing-image/movie-missing-image';

@Directive({
  selector: 'img[imageFallback]'
})
export class ImageFallback {
  private vcr = inject(ViewContainerRef);
  private el = inject(ElementRef<HTMLImageElement>);
  private renderer = inject(Renderer2);

  @HostListener('error')
  onError() {
    const imgEl = this.el.nativeElement;
    const parent = this.renderer.parentNode(imgEl);

    this.renderer.removeChild(parent, imgEl);
    this.vcr.clear();
    this.vcr.createComponent(MovieMissingImage);
  }
}
