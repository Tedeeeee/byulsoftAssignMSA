export interface BoardData {
  boardId: number;
  boardTitle: string;
  memberNickname: string;
  boardCreatedAt: string;
  boardIsDelete: boolean;
}

export interface BoardStarData {
  boardStarId: number;
  boardId: number;
  boardStarType: string;
  boardStarShortReview: string;
  boardStarRating: number;
}

export interface BoardDetailData extends BoardData {
  boardContent: string
  boardRegion: string;
  boardStars: BoardStarData[];
}

export type BoardHeaderData = Pick<BoardDetailData, 'boardId' | 'boardRegion' | 'boardTitle' | 'boardCreatedAt' | 'memberNickname'>;

