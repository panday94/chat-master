import type { AxiosProgressEvent, GenericAbortSignal } from 'axios'
import { post, get, del } from '@/utils/request'

// 创建对话
export function fetchChatAPI<T = any>(data: Chat.ChatRequest) {
  return post<T>({
    url: '/v1/chat',
    data: data
  })
}

// 获取会话列表
export function listChat<T>() {
  return get<T>({
    url: '/v1/chat',
  })
}

// 删除会话列表
export function removeChat<T>(chatNumber: string) {
  return del<T>({
    url: '/v1/chat/' + chatNumber,
  })
}

// 发送内容
export function fetchChatMessageAPI<T = any>(data: Chat.MessageRequest) {
  return post<T>({
    url: '/v1/chat/message',
    data: data
  })
}

// 获取会话内容列表
export function listChatMessage<T>(data: object) {
  return get<T>({
    url: '/v1/chat/message',
    data
  })
}

// 根据消息id获取当前内容
export function fetchChatMessageById<T = any>(messageId: string) {
  return get<T>({
    url: '/v1/chat/message/' + messageId,
  })
}

// 删除消息id
export function deleteChatMessageById<T = any>(messageId: string) {
  return del<T>({
    url: '/v1/chat/message/' + messageId,
  })
}

// 流式响应聊天
export function fetchChatAPIProcess<T = any>(
  params: {
    conversationId: string
    signal?: GenericAbortSignal
    onDownloadProgress?: (progressEvent: AxiosProgressEvent) => void
  },
) {

  let data: Record<string, any> = {
    conversationId: params.conversationId
  }

  return get<T>({
    url: '/v1/chat/completions',
    data,
    signal: params.signal,
    onDownloadProgress: params.onDownloadProgress,
  })
}
