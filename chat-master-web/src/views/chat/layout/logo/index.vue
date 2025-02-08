<script setup lang='ts'>
import { onMounted, computed, ref } from 'vue'
import logo from '@/assets/logo.jpg'
import wxapp from '@/assets/wxapp.jpg'
import { SvgIcon } from '@/components/common'
import { defineAsyncComponent } from 'vue'
import { useAppStore, useChatStore } from '@/store'
import { NPopover, NDropdown, useMessage, DropdownOption, menuProps, DropdownMenuProps } from 'naive-ui'
import { fetchModel } from '@/api/user'
import type { Theme } from '@/store/modules/app/helper'

const ms = useMessage()
const appStore = useAppStore()
const chatStore = useChatStore()
const Setting = defineAsyncComponent(() => import('@/components/common/Setting/index.vue'))
const theme = computed(() => appStore.theme)
const model = computed(() => chatStore.model)
const show = ref(false)
const models = ref<Chat.Model[]>()
const options = ref<any>()

// const themeOptions: { label: string; key: Theme; icon: string }[] = [
//     {
//         label: 'Auto',
//         key: 'auto',
//         icon: 'ri:contrast-line',
//     },
//     {
//         label: 'Light',
//         key: 'light',
//         icon: 'ri:sun-foggy-line',
//     },
//     {
//         label: 'Dark',
//         key: 'dark',
//         icon: 'ri:moon-foggy-line',
//     },
// ]

// 初始化加载
onMounted(() => {
    // 获取用户模型
    fetchModel<Chat.Model[]>().then((res) => {
        models.value = res.data;
        options.value = models.value.map(model => {
            const option = {
                label: model.name,
                key: model.model,
                props: {}
            }
            if (option.key === chatStore.model) {
                option.props = {
                    style: { backgroundColor: theme.value == 'light' ? '#F3F3F5' : '#767C82' }
                }
            }
            return option;
        })
        if (!chatStore.model) {
            chatStore.setModel(options.value[0].key)
        }
    })
})

// 变换主题
function changeTheme() {
    let value: Theme = theme.value == 'light' ? 'dark' : 'light';
    appStore.setTheme(value);
    updateOptions();
}

// 切换作图
function changePicture() {
    ms.warning("作图功能敬请期待～")
    return;
}

// 切换模型
async function handleSelect(key: string) {
    if (key == chatStore.model) return;
    await chatStore.setModel(key);
    updateOptions();
    ms.success("模型切换成功")
}

// 更新模型选中样式
function updateOptions() {
    options.value = options.value.map((v: { label: string; key: string; props: object }) => {
        const option = {
            label: v.label,
            key: v.key,
            props: {}
        }
        if (option.key === chatStore.model) {
            option.props = {
                style: { backgroundColor: theme.value == 'light' ? '#F3F3F5' : '#767C82' }
            }
        }
        return option;
    })
}
</script>
<template>
  <div>
    <!-- 侧边 -->
    <div class="flex h-full bg-white dark:bg-[#18181c] absolute left-0 z-50">
      <div class="flex flex-col items-center justify-between w-20 border-r dark:border-[#2d2d30]">
        <div class="flex flex-col items-center">
          <img
            :src="appStore.baseInfo && appStore.baseInfo.siteLogo ? appStore.baseInfo.siteLogo: logo"
            class="mt-3 w-[70px] h-[70px] cursor-pointer"
          />
          <div
            class="flex flex-col items-center mt-5 w-14 bg-slate-100 dark:bg-[#24272e] p-2 rounded-lg cursor-pointer hover:bg-slate-100"
          >
            <SvgIcon icon="ri:message-line" class="mb-2 text-xl" />
            <div class="text-xs font-semibold">{{ $t('chat.duiHua') }}</div>
          </div>
          <div
            class="flex flex-col items-center mt-5 w-14 dark:bg-[#18181c] p-2 rounded-lg cursor-pointer hover:bg-slate-100 hover:dark:bg-[#24272e]"
            @click="changePicture()"
          >
            <SvgIcon icon="icon-park-outline:picture" class="mb-2 text-xl" />
            <div class="text-xs font-semibold">{{ $t('chat.picture') }}</div>
          </div>
        </div>
        <div class="flex flex-col items-center">
          <div
            class="flex justify-center items-center rounded-3xl cursor-pointer mb-6 dark:bg-[#24272e] w-9 h-9 hover:bg-slate-200"
          >
            <NDropdown
              trigger="hover"
              placement="right-start"
              :options="options"
              @select="handleSelect"
            >
              <SvgIcon :name="model" width="32" height="32" />
            </NDropdown>
          </div>
          <div
            class="flex justify-center items-center rounded-3xl cursor-pointer mb-6 bg-slate-100 dark:bg-[#24272e] w-9 h-9 hover:bg-slate-200"
            @click="changeTheme()"
          >
            <SvgIcon
              :icon="theme == 'light' ? 'ri:sun-foggy-line' : 'ri:moon-foggy-line'"
              class="text-2xl"
            />
          </div>
          <div
            class="flex justify-center items-center rounded-3xl cursor-pointer mb-6 bg-slate-100 dark:bg-[#24272e] w-9 h-9 hover:bg-slate-200"
            @click="show = true"
          >
            <SvgIcon icon="lucide:user" class="text-2xl" />
          </div>
          <div
            class="flex justify-center items-center w-14 mb-4 bg-[#18181c] dark:bg-[#24272e] rounded-md h-7 cursor-pointer hover:bg-blue-600 hover:dark:bg-[#24272e]"
          >
            <NPopover
              trigger="hover"
              raw
              :show-arrow="false"
              placement="right"
              style="margin-left: 17px;"
            >
              <template #trigger>
                <div class="flex items-center">
                  <SvgIcon icon="fluent:phone-16-regular" class="text-lg text-white" />
                  <div class="flex text-white text-sm">{{ $t('common.app') }}</div>
                </div>
              </template>
              <div
                class="bg-[#fff] dark:bg-[#24272e]"
                style="width: 220px; height: 220px; padding: 20px 20px; display: flex; justify-content: center; align-items: center; flex-direction: column;"
              >
                <div style="font-weight: 500; margin-bottom: 10px;">{{ $t('common.appTip') }}</div>
                <img :src="wxapp" style="width: 150px; height: 150px;" />
              </div>
            </NPopover>
          </div>
        </div>
      </div>
    </div>
    <Setting v-if="show" v-model:visible="show" />
  </div>
</template>

<style>
.n-dropdown-menu {
  height: 174px;
  overflow-y: auto;
}
</style>