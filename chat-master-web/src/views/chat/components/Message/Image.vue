<script lang="ts" setup>
import { computed, ref } from 'vue'
import { useBasicLayout } from '@/hooks/useBasicLayout'
import { NImage } from 'naive-ui'
import imageLoading from '@/assets/imageLoading.gif'

interface Props {
  inversion?: boolean
  error?: boolean
  images?: Array<string>
  loading?: boolean
  asRawText?: boolean
}

const props = defineProps<Props>()
const textRef = ref<HTMLElement>()
const { isMobile } = useBasicLayout()


const wrapClass = computed(() => {
  return [
    'text-wrap',
    'min-w-[20px]',
    'rounded-md',
    isMobile.value ? 'p-2' : '',
    props.inversion ? '' : '',
    // props.inversion ? 'dark:text-[#eee]' : 'dark:bg-[#1e1e20]',
    props.inversion ? 'message-request' : 'message-reply',
    { 'text-red-500': props.error },
  ]
})

const images = computed(() => {
  return props.images ?? []
})
</script>

<template>
  <div :class="wrapClass">
    <div ref="textRef" class="leading-7 break-words text-[15px]">
      <div v-if="images.length == 0">
        <NImage :width="300" :height="300" :src="imageLoading" />
        <span>AI创作中</span>
      </div>
      <div v-else>
        <NImage v-for="item in images" :width="300" :height="300" :key="item" lazy
          :src="'data:image/png;base64,' + item" />
      </div>
    </div>
  </div>
</template>

<style lang="less">
@import url(./style.less);
</style>
