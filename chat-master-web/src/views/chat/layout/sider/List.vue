<script setup lang='ts'>
import { onMounted, ref, watch } from 'vue'
import { NPopconfirm, NScrollbar, useMessage } from 'naive-ui'
import { SvgIcon } from '@/components/common'
import { useAppStore, useChatStore } from '@/store'
import { useBasicLayout } from '@/hooks/useBasicLayout'
import { debounce } from '@/utils/functions/debounce'
import { listChat, removeChat } from '@/api'

const ms = useMessage()
const { isMobile } = useBasicLayout()
const appStore = useAppStore()
const chatStore = useChatStore()

// 获取会话列表内容
const dataSources = ref<Array<Chat.History>>();

onMounted(() => {
  listChatApi();
})

// 监听选中信息
watch(
  () => chatStore.active,
  () => {
    if (!chatStore.active) {
      return;
    }
    const index = dataSources.value? dataSources.value.findIndex(item => item.chatNumber === chatStore.active) : -1;
    if (index === -1) {
      listChatApi();
    }
  }
)

function listChatApi() {
  listChat<Array<Chat.History>>().then((res) => {
    dataSources.value = res.data;
  });
}

async function handleSelect({ chatNumber }: Chat.History) {
  if (isActive(chatNumber)) return
  await chatStore.setActive(chatNumber)
  if (isMobile.value) {
    appStore.setSiderCollapsed(true)
  }
}

// 是否选中当前会话
function isActive(chatNumber: string) {
  return chatStore.active === chatNumber
}

/** 删除会话 */
const handleDeleteDebounce = debounce(handleDelete, 600)

function handleDelete(chatNumber: string, event?: MouseEvent | TouchEvent) {
  event?.stopPropagation()
  removeChat(chatNumber).then((res)=> {
    if (res.code === 200) {
      listChatApi();
      chatStore.setActive('')
      ms.success('删除成功');
    } else {
      ms.error(res.msg);
    }
  })
  if (isMobile.value) {
    appStore.setSiderCollapsed(true)
  }
}
</script>

<template>
  <NScrollbar>
    <div class="flex flex-col">
      <template v-if="!dataSources || dataSources.length === 0">
        <div class="flex flex-col items-center mt-4 text-center text-neutral-300">
          <!-- <SvgIcon icon="ri:inbox-line" class="mb-2 text-3xl" /> -->
          <span>{{ $t('common.noData') }}</span>
        </div>
      </template>
      <template v-else>
        <div v-for="(item, index) of dataSources" :key="index">
          <a class="flex flex-1 items-center justify-between h-16 p-4 cursor-pointer hover:bg-neutral-100 hover:text-blue-500 hover:border-l-2 hover:border-blue-600 
            dark:hover:bg-[#24272e] dark:hover:text-white dark:hover:border-none hover:shadow hover:transition-all"
            :class="isActive(item.chatNumber) && ['border-blue-600', 'dark:border-none', 'bg-neutral-100', 'dark:bg-[#24272e]', 'text-blue-400', 'dark:text-white', 'border-l-2', 'shadow']"
            @click="handleSelect(item)">
            <div class="flex flex-col w-36">
              <div class="overflow-hidden  text-ellipsis whitespace-nowrap">{{ item.title }}</div>
              <div class="text-xs ">{{ item.createTime }}</div>
            </div>
            <div v-if="isActive(item.chatNumber)">
              <NPopconfirm placement="bottom" @positive-click="handleDeleteDebounce(item.chatNumber, $event)">
                <template #trigger>
                  <button class="p-1">
                    <SvgIcon icon="ri:delete-bin-line" />
                  </button>
                </template>
                {{ $t('chat.deleteHistoryConfirm') }}
              </NPopconfirm>
            </div>
          </a>
        </div>
      </template>
    </div>
  </NScrollbar>
</template>
