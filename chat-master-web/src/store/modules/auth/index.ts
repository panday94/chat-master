import { defineStore } from 'pinia'
import { getToken, removeToken, setToken } from './helper'
import { store } from '@/store'
import { fetchSession } from '@/api/user'

interface SessionResponse {
  id: boolean
  tel: string
  name: string
  nickName: string
  avatar: string
  password: string| null
  context: boolean | false
  num: number | 0
}

export interface AuthState {
  token: string | ''
  session: SessionResponse | null
}

export const useAuthStore = defineStore('auth-store', {
  state: (): AuthState => ({
    token: getToken(),
    session: null,
  }),

  getters: {
    // isChatGPTAPI(state): boolean {
    //   return state.session?.model === 'ChatGPTAPI'
    // },
  },

  actions: {
    async getSession() {
      try {
        const res = await fetchSession<SessionResponse>()
        if (res.code === 200) {
          this.session = res.data;
        }
        return Promise.resolve(res)
      } catch (error) {
        return Promise.reject(error)
      }
    },

    setToken(token: string) {
      this.token = token
      setToken(token)
    },

    removeToken() {
      this.token = ''
      removeToken()
    },
  },
})

export function useAuthStoreWithout() {
  return useAuthStore(store)
}
