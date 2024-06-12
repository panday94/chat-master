import { useChatStore } from '@/store'

export function useChat() {
  const chatStore = useChatStore()

  // 根据下标获取对话内容
  const getChatByIndex = (chatNumber: string, index: number) => {
    return chatStore.getChatByIndex(chatNumber, index)
  }

  // 添加当前对话到缓存
  const addChat = (chatNumber: string, chat: Chat.Chat) => {
    chatStore.addChat(chatNumber, chat)
  }

  // 更新对话
  const updateChat = (chatNumber: string, index: number, chat: Chat.Chat) => {
    chatStore.updateChat(chatNumber, index, chat)
  }

  // 更新对话部分数据及加载状态
  const updateChatLoading = (chatNumber: string, index: number, chat: Partial<Chat.Chat>) => {
    chatStore.updateChatLoading(chatNumber, index, chat)
  }

  return {
    addChat,
    updateChat,
    updateChatLoading,
    getChatByIndex,
  }
}
