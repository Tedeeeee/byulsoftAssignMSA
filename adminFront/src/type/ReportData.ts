export interface ReportData {
  reportId: number
  reportedMemberNickname: number
  reportContent: string
  reportType: string
  reportTypeId: number
  reportCreatedAt: string
  reportState: string
}

export const ReportType: Record<string, string> = {
  BOARD: '게시글',
  COMMENT: '댓글'
}

export const ReportState: Record<string, string> = {
  RECEIVE : '접수',
  COMPLETE: '처리 완료',
  REVOKE: '취소'
}
