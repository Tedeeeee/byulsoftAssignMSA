export interface CommentData {
  commentId: number;
  memberId: number;
  memberNickname: string;
  commentUpdatedAt: string;
  commentContent: string;
  showReplyForm: boolean;
  isEdit: boolean;
}