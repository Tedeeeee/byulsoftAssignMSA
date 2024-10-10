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
  boardStars: BoardStar[];
  boardView: number;
}