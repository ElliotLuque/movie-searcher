export interface MoviePage {
  page: number;
  pageSize: number;
  totalPages: number;
  totalElements: number;
  results: MoviePageItem[];
}

export interface MoviePageItem {
  imdbId: string;
  title: string;
  imageUrl: string;
  year: number;
}
