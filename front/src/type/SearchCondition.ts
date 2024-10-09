export interface SearchCondition {
  startDate: string;
  endDate: string;
  memberId: number;
  pageNumber: number;
}

export interface SearchBoard extends SearchCondition {
  searchType: string;
  searchText: string;
}