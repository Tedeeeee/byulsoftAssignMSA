import { instanceWithAuth, instanceForAccessToken } from '@/api/Interceptors'
import type { BoardSaveData, BoardUpdateData } from '@/type/BoardData'
import type { CommentData } from '@/type/CommentData'
import type { UserData } from '@/type/UserData'

export const getAccessTokenApi = async () => {
  return instanceForAccessToken.post('/authService/auth/token/renew');
}

export const getUserData = async () : Promise<UserData> => {
  return instanceWithAuth.get('/userService/users');
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
  return instanceWithAuth.post('/userCommentService/comments/update', data);
};

export const deleteComment = async (id: number) => {
  return instanceWithAuth.post(`/userCommentService/comments/delete/${id}`);
};

export const logout = () => {
  return instanceWithAuth.post('/authService/auth/logout');
};