export interface BoardStarData {
  boardStarId: number;
  boardId: number;
  boardStarType: string;
  boardStarShortReview: string;
  boardStarRating: number;
}

export interface BoardData {
  boardId: number;
  boardTitle: string;
  boardContent: string;
  memberNickname: string;
  boardRegion: string;
  boardCreatedAt: string;
  boardStars: BoardStarData[];
  boardView: number;
}

export type BoardListData = Omit<BoardData, 'boardStars'>;
export type BoardSaveData = Omit<BoardData, 'boardId' | 'boardCreatedAt' | 'memberNickname' | 'boardView'>;
export type BoardUpdateData = Pick<BoardData, 'boardId' | 'boardRegion' | 'boardTitle' | 'boardContent' | 'boardStars'>;
export type BoardHeaderData = Pick<BoardData, 'boardId' | 'boardRegion' | 'boardTitle' | 'boardCreatedAt' | 'memberNickname'>;