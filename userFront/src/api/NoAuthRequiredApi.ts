import { instance } from '@/api/Interceptors'
import type { SearchBoard } from '@/type/SearchData'
import type { BoardData } from '@/type/BoardData'
import type { LoginData, UserData } from '@/type/UserData'
import type { CommentData } from '@/type/CommentData'

export const getBoardList = async (searchBoard: SearchBoard) : Promise<BoardData[]> => {
  return instance.get('/userBoardService/noAuth/boards', {
    params: {
      searchType: searchBoard.searchType,
      searchText: searchBoard.searchText,
      sortOrder: searchBoard.sortOrder,
      sortType: searchBoard.sortType,
      pageNumber: searchBoard.pageNumber,
    }
  });
}

export const register = (userData: UserData) : Promise<UserData> => {
  return instance.post('/userService/noAuth/users/register', userData)
}

export const checkEmail = async (email: string): Promise<boolean> => {
  return instance.get('/userService/noAuth/users/check-email', {
    params: {
      email: email,
    },
  });
};

export const checkNickname = async (nickname: string): Promise<boolean> => {
  return instance.get('/userService/noAuth/users/check-nickname', {
    params: {
      nickname: nickname,
    },
  });
};

export const login = async (loginData: LoginData) => {
  return instance.post('/authService/user/login', loginData);
}

export const getBoardById = async (boardId: number) : Promise<BoardData> => {
  return instance.get(`/userBoardService/noAuth/boards/${boardId}`);
}

export const getCommentById = async (boardId: number) : Promise<CommentData> => {
  return instance.get(`/userCommentService/noAuth/comments/boards/${boardId}`);
}