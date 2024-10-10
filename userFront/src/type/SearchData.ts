export interface SearchData {
  startDate: string;
  endDate: string;
  memberId: number;
  pageNumber: number;
}

export interface SearchBoard extends SearchData {
  searchType: string;
  searchText: string;
  sortType: string;
  sortOrder: string;
}