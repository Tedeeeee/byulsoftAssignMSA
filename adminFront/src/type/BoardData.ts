export interface BoardData {
  boardId: number;
  boardTitle: string;
  memberNickname: string;
  boardCreatedAt: string;
  boardIsDelete: boolean;
}

export interface BoardDetailData extends BoardData {

}


