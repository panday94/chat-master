<script setup lang='ts'>
import { computed, ref } from 'vue'
import { NDropdown, NPopover, useMessage } from 'naive-ui'
import AvatarComponent from './Avatar.vue'
import TextComponent from './Text.vue'
import ImageComponent from './Image.vue'
import { SvgIcon } from '@/components/common'
import { useIconRender } from '@/hooks/useIconRender'
import { t } from '@/locales'
import { useBasicLayout } from '@/hooks/useBasicLayout'
import { copyToClip } from '@/utils/copy'

interface Props {
  dateTime?: string
  contentType: string
  text?: string
  images: Array<string>
  model?: string
  inversion?: boolean
  error?: boolean
  loading?: boolean
}

interface Emit {
  (ev: 'regenerate'): void
  (ev: 'delete'): void
}

const props = defineProps<Props>()

const emit = defineEmits<Emit>()

const { isMobile } = useBasicLayout()

const { iconRender } = useIconRender()

const message = useMessage()

const textRef = ref<HTMLElement>()

const asRawText = ref(props.inversion)

const messageRef = ref<HTMLElement>()

const options = computed(() => {
  const common = [
    {
      label: t('chat.copy'),
      key: 'copyText',
      icon: iconRender({ icon: 'ri:file-copy-2-line' }),
    },
    {
      label: t('common.delete'),
      key: 'delete',
      icon: iconRender({ icon: 'ri:delete-bin-line' }),
    },
  ]

  if (!props.inversion) {
    common.unshift({
      label: asRawText.value ? t('chat.preview') : t('chat.showRawText'),
      key: 'toggleRenderType',
      icon: iconRender({ icon: asRawText.value ? 'ic:outline-code-off' : 'ic:outline-code' }),
    })
  }

  return common
})

function handleSelect(key: 'copyText' | 'delete' | 'toggleRenderType') {
  switch (key) {
    case 'copyText':
      handleCopy()
      return
    case 'toggleRenderType':
      asRawText.value = !asRawText.value
      return
    case 'delete':
      emit('delete')
  }
}

function handleRegenerate() {
  messageRef.value?.scrollIntoView()
  emit('regenerate')
}

async function handleCopy() {
  try {
    await copyToClip(props.text || '')
    message.success('复制成功')
  }
  catch {
    message.error('复制失败')
  }
}
</script>
 
<template>
  <div ref="messageRef" class="flex w-full p-5 overflow-hidden"
    :class="inversion ? [] : ['rounded-lg', 'bg-[#fff]', 'dark:bg-[#24272e]',]">
    <div class="flex items-center justify-center flex-shrink-0 h-8 overflow-hidden rounded-full basis-8"
      :class="[inversion ? 'mr-2' : 'mr-2']">
      <AvatarComponent :inversion="inversion" :loading="loading" :model="model" />
    </div>
    <div class="overflow-hidden w-full items-start"
      :class="inversion ? ['cursor-pointer', 'hover:bg-slate-100', 'dark:hover:bg-[#24272e]', 'hover:rounded-lg'] : ['']"
      @click="inversion ? handleCopy() : ''">
      <div class="flex gap-1 pl-1" :class="[inversion ? 'flex-row' : 'flex-row']">
        <TextComponent v-if="contentType == 'text'" ref="textRef" :inversion="inversion" :error="error" :text="text" :loading="loading"
          :as-raw-text="asRawText" />
        <ImageComponent v-else ref="textRef" :inversion="inversion" :error="error" :images="images" :loading="loading"
          :as-raw-text="asRawText" />
      </div>
      <div v-if="!inversion && !loading" class="mt-4 h-[33px] w-full flex items-center justify-between">
        <div class="text-[#B0B7C0] text-xs">{{ t('chat.messageTip') }}</div>
        <div v-if="contentType == 'text'" class="flex items-center">
          <div class="mr-3 flex items-center">
            <NPopover raw trigger="hover" :show-arrow="true" :arrow-style="{ backgroundColor: 'rgb(100 116 139)' }">
              <template #trigger>
                <button class="text-[18px] text-neutral-300 hover:text-neutral-800 dark:hover:text-neutral-300"
                  style="margin: 0;" @click="handleRegenerate">
                  <SvgIcon icon="ri:restart-line" />
                </button>
              </template>
              <div class="px-3 py-2 bg-slate-500 text-[#fff] rounded-md" style="transform-origin: inherit; ">
                {{ t('chat.regenerate') }}
              </div>
            </NPopover>
          </div>
          <div class="mr-3 flex items-center">
            <NPopover raw trigger="hover" :show-arrow="true" :arrow-style="{ backgroundColor: 'rgb(100 116 139)' }">
              <template #trigger>
                <button class="text-[18px] text-neutral-300 hover:text-neutral-800 dark:hover:text-neutral-300"
                  style="margin: 0;" @click="handleCopy">
                  <SvgIcon icon="ri:file-copy-2-line" />
                </button>
              </template>
              <div class="px-3 py-2 bg-slate-500 text-[#fff] rounded-md" style="">
                {{ t('chat.copy') }}
              </div>
            </NPopover>
          </div>
          <NDropdown :trigger="isMobile ? 'click' : 'hover'" :placement="!inversion ? 'right' : 'right'"
            :options="options" @select="handleSelect">
            <button class="text-[18px] transition text-neutral-300 hover:text-neutral-800 dark:hover:text-neutral-200">
              <SvgIcon icon="ri:more-2-fill" />
            </button>
          </NDropdown>
        </div>
      </div>
    </div>
  </div>
</template>
