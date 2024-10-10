import { instanceWithAuth, instanceForAccessToken } from '@/api/Interceptors'
import type { BoardSaveData, BoardStarData, BoardUpdateData } from '@/type/BoardData'
import type { CommentData } from '@/type/CommentData'

export const getAccessTokenApi = async (): Promise<string> => {
  return instanceForAccessToken.post('/authService/auth/token/renew');
}

export const insertBoard = async (boardInsertData: BoardSaveData): Promise<void> => {
  return instanceWithAuth.post('/userBoardService/boards', boardInsertData);
}

export const updateBoard = async (boardUpdateData: BoardUpdateData): Promise<void> => {
  return instanceWithAuth.post('/userBoardService/boards/update', boardUpdateData);
}

export const deleteBoard = async (id: number) => {
  return instanceWithAuth.post(`/userBoardService/boards/delete/${id}`);
};

export const insertComment = async (CommentInsertData: CommentData) => {
  return instanceWithAuth.post('/userCommentService/comments', CommentInsertData);
};

export const updateComment = async (data: CommentData) => {
  return instanceWithAuth.put('/userCommentService/comments/update', data);
};

export const deleteComment = async (id: number) => {
  return instanceWithAuth.post(`/userCommentService/comments/delete/${id}`);
};

export const logout = () => {
  return instanceWithAuth.delete('members/logout');
};