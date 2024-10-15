export interface CommentData {
  commentId: number;
  memberId: number;
  memberNickname: string;
  commentCreatedAt: string;
  commentUpdatedAt: string;
  commentContent: string;
  showReplyForm: boolean;
  isEdit: boolean;
}

export type MyCommentList = Pick<CommentData, 'commentContent' | 'commentCreatedAt'>