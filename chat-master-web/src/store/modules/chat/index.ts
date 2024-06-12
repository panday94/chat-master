import { defineStore } from 'pinia'
import { getLocalState, setLocalState } from './helper'
import { router } from '@/router'
import { listChatMessage } from '@/api'

export const useChatStore = defineStore('chat-store', {
  state: (): Chat.ChatState => getLocalState(),

  getters: {
    // 获取当前聊天内容
    getChatByChatNumber(state: Chat.ChatState) {
      return async (chatNumber?: string) => {
        if (!chatNumber) {
          return [];
        }
        let res = await listChatMessage<Chat.Message[]>({ chatNumber });
        const chatData: Chat.Chat[] = res.data.map(item => {
          let data: Chat.Chat = {
            conversationId: item.messageId,
            parentMessageId: item.parentMessageId,
            dateTime: item.createTime,
            contentType: item.contentType,
            text: item.contentType == 'text' ? item.content : '',
            images: item.contentType == 'image' && item.content ? JSON.parse(item.content).map((d: { b64Image: any }) => { return d.b64Image }) : [],
            model: item.model,
            inversion: item.role == 'user' ? true : false,
            error: false,
            loading: false
          }
          return data;
        });
        const index = this.chat.findIndex(item => item.chatNumber === chatNumber);
        if (index !== -1) {
          this.chat[index].data = chatData;
        } else {
          this.chat.push({ chatNumber, data: chatData })
        }
        return this.chat.find(item => item.chatNumber === chatNumber)?.data ?? []
      }
    },
  },

  actions: {
    // 设置是否开启上下文
    setUsingContext(context: boolean) {
      this.usingContext = context
      this.recordState()
    },

    // 设置激活对话框
    async setActive(chatNumber: string) {
      this.active = chatNumber
      this.recordState();
      // return await this.reloadRoute(chatNumber)
    },

    // 设置使用模型
    async setModel(model: string) {
      this.model = model
      this.recordState();
    },

    // 根据索引获取当前信息
    getChatByIndex(chatNumber: string, index: number) {
      if (!chatNumber || chatNumber === '') {
        if (this.chat.length) return this.chat[0].data[index]
        return null
      }
      const chatIndex = this.chat.findIndex(item => item.chatNumber === chatNumber)
      if (chatIndex !== -1) {
        return this.chat[chatIndex].data[index]
      }
      return null
    },

    // 缓存中添加对话内容
    addChat(chatNumber: string, chat: Chat.Chat) {
      const index = this.chat.findIndex(item => item.chatNumber === chatNumber)
      if (index !== -1) {
        this.chat[index].data.push(chat)
      } else {
        this.chat.push({ chatNumber, data: [chat] })
      }
      this.recordState()
    },

    // 缓存中修改对话内容
    updateChat(chatNumber: string, index: number, chat: Chat.Chat) {
      const chatIndex = this.chat.findIndex(item => item.chatNumber === chatNumber)
      if (chatIndex !== -1) {
        this.chat[chatIndex].data[index] = chat
      } else {
        this.chat.push({ chatNumber, data: [chat] })
      }
      this.recordState()
    },

    // 修改缓存中部分数据，加载状态
    updateChatLoading(chatNumber: string, index: number, chat: Partial<Chat.Chat>) {
      const chatIndex = this.chat.findIndex(item => item.chatNumber === chatNumber)
      if (chatIndex !== -1) {
        this.chat[chatIndex].data[index] = { ...this.chat[chatIndex].data[index], ...chat }
        this.recordState()
      }
    },

    // 删除当前记录
    deleteChatByUuid(chatNumber: string, index: number) {
      if (!chatNumber || chatNumber === '') {
        if (this.chat.length) {
          this.chat[0].data.splice(index, 1)
          this.recordState()
        }
        return
      }
      const chatIndex = this.chat.findIndex(item => item.chatNumber === chatNumber)
      if (chatIndex !== -1) {
        this.chat[chatIndex].data.splice(index, 1)
        this.recordState()
      }
    },

    // 清空当前对话内容
    clearChatByUuid(chatNumber: string) {
      if (!chatNumber || chatNumber === '') {
        if (this.chat.length) {
          this.chat[0].data = []
          this.recordState()
        }
        return
      }

      const index = this.chat.findIndex(item => item.chatNumber === chatNumber)
      if (index !== -1) {
        this.chat[index].data = []
        this.recordState()
      }
    },

    async reloadRoute(chatNumber?: string) {
      this.recordState();
      await router.push({ name: 'Chat', params: { chatNumber } })
    },

    recordState() {
      setLocalState(this.$state)
    },
  },
})
