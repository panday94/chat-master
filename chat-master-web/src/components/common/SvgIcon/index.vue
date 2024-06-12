<script setup lang='ts'>
import { computed, useAttrs } from 'vue'
import { Icon } from '@iconify/vue'

interface Props {
  icon?: string
  name?: string
  prefix?: string
  color?: string
  width?: string
  height?: string
}

const props = withDefaults(defineProps<Props>(), {
  icon: '',
  name: '',
  prefix: 'icon',
  color: '',
  width: '32',
  height: '32'
})

const svgName = computed(() => `#${props.prefix}-${props.name}`);

const attrs = useAttrs()

const bindAttrs = computed<{ class: string; style: string }>(() => ({
  class: (attrs.class as string) || '',
  style: (attrs.style as string) || '',
}))
</script>

<style scoped></style>

<template>
  <Icon v-if="icon" :icon="icon" v-bind="bindAttrs" />
  <svg v-else aria-hidden="true" class="fill-current focus:outline-none"
    :style="{ width: width + 'px', height: height + 'px', color: color }">
    <use :xlink:href="svgName" />
  </svg>
</template>
