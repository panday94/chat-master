<script lang="ts" setup>
import { NButton, NResult } from 'naive-ui'
import { useRouter } from 'vue-router'

const router = useRouter()

// 刷新页面返回首页
function goHome() {
  router.push('/')
}
</script>

<template>
  <div class="flex h-full dark:bg-neutral-800">
    <div class="px-4 m-auto space-y-4 text-center max-[400px]">
      <n-result status="500" title="服务器连接中，请稍后再试～" description="服务器出错可能说明该雇更多程序员了">
        <template #footer>
          <NButton type="primary" style="cursor: pointer;" @click="goHome">刷新</NButton>
        </template>
      </n-result>
    </div>
  </div>
</template>
