export interface CommentData {
  boardId: number;
  commentId: number;
  commentContent: string;
  commentCreatedAt: string;
  commentIsDelete: boolean;
}

export interface CommentDataInBoard {
  commentId: number;
  memberId: number;
  adminId: number;
  memberNickname: string;
  commentCreatedAt: string;
  commentUpdatedAt: string;
  commentContent: string;
  commentIsDelete: boolean;
  showReplyForm: boolean;
  isEdit: boolean;
}
