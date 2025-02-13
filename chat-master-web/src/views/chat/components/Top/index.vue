<script lang='ts' setup>
import { computed, onMounted, ref } from "vue";
import { NButton } from "naive-ui";
import * as emoji from "node-emoji";
import { listAssistantRandom } from "@/api/common";
import { t } from "@/locales";
import logom from "@/assets/logom.jpg";
import { SvgIcon } from "@/components/common";
import { getGreeting } from "@/utils/functions";
import { useBasicLayout } from "@/hooks/useBasicLayout";

const { isMobile } = useBasicLayout();

// 内容底部样式
const topClass = computed(() => {
  let classes = ["p-5"];
  if (isMobile.value) classes = ["p-3"];
  return classes;
});

// 问候语 上午好/下午好/晚上好
const greeting = computed(() => {
  return getGreeting();
});

// 事件
interface Emit {
  (ev: "handAssistant", data: number): void;
}

const emit = defineEmits<Emit>();

const randomAssistantValue = ref<Array<Assistant>>([]);

// 助手
interface Assistant {
  id: number;
  title: string;
  icon: string;
  tag: string;
  description: string;
  systemPrompt: string;
}

// 初始化加载
onMounted(() => {
  randomAssistant();
});

// 获取随机助手
function randomAssistant() {
  listAssistantRandom<Array<Assistant>>().then(res => {
    if (isMobile.value) {
      randomAssistantValue.value = res.data.splice(0, 1);
    } else {
      randomAssistantValue.value = res.data;
    }
  });
}

// 处理带表情文字
function parseEmoji(str: string) {
  return emoji.emojify(str);
}

// 选择快捷助手
function clickAssistant(assistantId: number) {
  emit("handAssistant", assistantId);
}

// 跳转码云仓库
function jumpMayun() {
  window.open("https://gitee.com/panday94/chat-master", "_blank");
}
</script>
<template>
  <div class="m-auto flex flex-col bg-white dark:bg-[#24272e] rounded-xl" :class="topClass">
    <div class="mb-8">
      <div class="flex mb-3 items-center">
        <img :src="logom" class="w-8 h-8" />
        <div class="text-xl font-medium pl-2">{{ parseEmoji(greeting) }}</div>
      </div>
      <div v-if="!isMobile" class="text-[15px] text-[#1a2029] dark:text-white pl-10">
        {{ t('chat.title') }}
        开源地址：
        <NButton quaternary type="primary" @click="jumpMayun()">传送门</NButton>
      </div>
      <div class="text-[15px] text-[#1a2029] dark:text-white pl-10">{{ t('chat.subTitle') }}</div>
      <div class="text-[15px] text-[#1a2029] dark:text-white pl-10">{{ t('chat.wxappTitle') }}</div>
    </div>
    <div class="mb-1 flex justify-between">
      <div class="text-[15px] pl-10 font-medium">{{ t('chat.try') }}</div>
      <div
        class="flex items-center pr-10 text-blue-500 hover:text-blue-600"
        @click="randomAssistant()"
      >
        <SvgIcon icon="mingcute:refresh-3-line" class="text-lg" />
        <span class="text-sm ml-1 cursor-pointer">{{ t('chat.refresh') }}</span>
      </div>
    </div>
    <div
      class="mt-4 text-[15px]"
      v-for="item in randomAssistantValue"
      :key="item.id"
      @click="clickAssistant(item.id)"
    >
      <div class="font-medium mb-2 ml-10">{{ parseEmoji(item.icon) }} {{ item.title }}</div>
      <div
        class="ml-8 text-blue-600 cursor-pointer hover:bg-neutral-100 hover:dark:bg-[#18181c] hover:rounded-md inline-block py-px px-2"
      >{{ item.description }}</div>
    </div>
  </div>
</template>