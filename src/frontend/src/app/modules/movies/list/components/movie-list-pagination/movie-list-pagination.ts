import {Component, computed, input, output} from '@angular/core';
import { MoviePage } from '../../../../../core/models/movie-page.model';
import {NgClass} from '@angular/common';

// VISIBLE BUTTONS
const MAX_VISIBLE = 5

@Component({
  selector: 'app-movie-list-pagination',
  imports: [
    NgClass
  ],
  templateUrl: './movie-list-pagination.html',
})
export class MovieListPagination {
  page = input.required<MoviePage>();         // ← obligatorio

  pageChange = output<number>();

  current       = computed(() => this.page().page);
  totalPages    = computed(() => this.page().totalPages);
  totalElements = computed(() => this.page().totalElements);
  pageSize      = computed(() => this.page().results.length);

  buttons = computed(() => {
    const cur = this.current();
    const tot = this.totalPages();
    const out: { page:number; label:string; active?:boolean; ellipsis?:true }[] = [];

    if (tot <= 1) return out;

    const half  = Math.floor(MAX_VISIBLE / 2);
    let start   = Math.max(1, cur - half);
    let end     = Math.min(tot, start + MAX_VISIBLE - 1);
    if (end - start + 1 < MAX_VISIBLE)
      start = Math.max(1, end - MAX_VISIBLE + 1);

    if (start > 1) out.push({ page: 1, label: '1' });
    if (start > 2) out.push({ page: -1, label: '…', ellipsis: true });

    for (let p = start; p <= end; p++) {
      out.push({ page: p, label: String(p), active: p === cur });
    }

    if (end < tot - 1) out.push({ page: -1, label: '…', ellipsis: true });
    if (end < tot)     out.push({ page: tot, label: String(tot) });

    return out;
  });

  hasMultiplePages = computed(() => this.totalPages() > 1);
  isFirst = () => this.current() === 1;
  isLast  = () => this.current() === this.totalPages();

  buttonClasses = (b: { active?: boolean }) => [
    b.active
      ? 'bg-blue-600 text-white hover:bg-blue-500'
      : 'hover:bg-gray-100 cursor-pointer',
  ];

  disabledClasses = (disabled: boolean) =>
    disabled ? 'text-gray-400 cursor-default pointer-events-none' : 'cursor-pointer hover:bg-gray-100';

  go(pageNumber: number) { this.pageChange.emit(pageNumber); }
}
