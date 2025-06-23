export interface MoviePage {
  page: number;
  totalPages: number;
  totalElements: number;
  results: MoviePageItem[];
}

export interface MoviePageItem {
  imdbId: string;
  title: string;
  year: number;
}
