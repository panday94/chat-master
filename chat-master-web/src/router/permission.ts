import type { Router } from 'vue-router'
import { useAuthStoreWithout } from '@/store/modules/auth'
import { useChatStore } from '@/store'

export function setupPageGuard(router: Router) {
  router.beforeEach(async (to, from, next) => {
    const chatStore = useChatStore();
    const authStore = useAuthStoreWithout();
    if (to.path == '/login') {
      authStore.removeToken();
      next();
      return;
    }
    if (to.path == '/agreement') {
      next();
      return;
    }
    try {
      const res = await authStore.getSession();
      chatStore.setActive('');
      if (!res) {
        next({ name: 'Login' })
        return;
      } 
      if (res.code !== 200) {
        next();
        return;
      }
      if (to.path == '/login') {
        next({name: 'Index'});
        return;
      }
      next();
    } catch (error) {
      if (to.path !== '/500') {
        next({ name: '500' })
      } else {
        next()
      }
    }
  })
}
