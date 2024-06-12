<script setup lang='ts'>
import type { CSSProperties } from 'vue'
import { computed, watch } from 'vue'
import { NLayoutSider, useMessage } from 'naive-ui'
import List from './List.vue'
import { useAppStore, useChatStore } from '@/store'
import { useBasicLayout } from '@/hooks/useBasicLayout'
import { SvgIcon } from '@/components/common'

const ms = useMessage()
const appStore = useAppStore()
const chatStore = useChatStore()

const { isMobile } = useBasicLayout()

const collapsed = computed(() => appStore.siderCollapsed)

function handleAdd() {
  if (!chatStore.active) {
    ms.warning('当前已是最新会话');
  } else {
    chatStore.setActive('')
  }
  if (isMobile.value) {
    appStore.setSiderCollapsed(true)
  }
}

function handleUpdateCollapsed() {
  appStore.setSiderCollapsed(!collapsed.value)
}

const getMobileClass = computed<CSSProperties>(() => {
  if (isMobile.value) {
    return {
      position: 'fixed',
      zIndex: 50,
    }
  }
  return {
    left: '5rem',
    transition: 'none',
  }
})

const mobileSafeArea = computed(() => {
  if (isMobile.value) {
    return {
      paddingBottom: 'env(safe-area-inset-bottom)',
    }
  }
  return {}
})

watch(
  isMobile,
  (val) => {
    appStore.setSiderCollapsed(val)
  },
  {
    immediate: true,
    flush: 'post',
  },
)
</script>

<template>
  <NLayoutSider :collapsed="collapsed" :collapsed-width="0" :width="200" :show-trigger="isMobile ? false : 'arrow-circle'"
    collapse-mode="transform" position="absolute" :style="getMobileClass" @update-collapsed="handleUpdateCollapsed">
    <div class="flex flex-1 flex-col h-full" :style="mobileSafeArea">
      <main class="flex flex-col flex-1 min-h-0">
        <div class="w-[200px] p-4 pb-6">
          <div class="font-semibold mb-4">
            {{ $t('chat.historyRecod') }}
          </div>
          <div
            class="flex justify-center items-center bg-blue-600 dark:bg-[#24272e] text-white text-base pt-2 pb-2 rounded-lg cursor-pointer"
            block @click="handleAdd">
            <SvgIcon icon="fluent:add-32-filled" class="mr-2" />
            {{ $t('chat.newChatButton') }}
          </div>
        </div>
        <div class="flex-1 min-h-0 overflow-hidden">
          <List />
        </div>
      </main>
    </div>
  </NLayoutSider>

  <template v-if="isMobile">
    <div v-show="!collapsed" class="fixed inset-0 z-40 w-full h-full bg-black/40" @click="handleUpdateCollapsed" />
  </template>
</template>
