<script setup lang='ts'>
import { computed } from "vue";
import { NLayout, NLayoutContent, NWatermark } from "naive-ui";
import { useRouter } from "vue-router";
import Sider from "./sider/index.vue";
import Logo from "./logo/index.vue";
import { useBasicLayout } from "@/hooks/useBasicLayout";
import { useAppStore, useChatStore } from "@/store";

const router = useRouter();
const appStore = useAppStore();
const chatStore = useChatStore();

router.replace({ name: "Chat", params: { chatNumber: chatStore.active } });

const { isMobile } = useBasicLayout();

const theme = computed(() => appStore.theme)

const collapsed = computed(() => appStore.siderCollapsed);

const getMobileClass = computed(() => {
  if (isMobile.value) return ["rounded-none", "shadow-none"];
  return ["border", "rounded-md", "shadow-md", "dark:border-neutral-800"];
});

const getContainerClass = computed(() => {
  return [
    "h-full",
    !isMobile.value && !collapsed.value ? "pl-[280px]" : "pl-[80px]"
  ];
});
const getFontColor = computed(() => {
  return theme.value == 'light' ? '#eee' : '#24272e';
});
</script>

<template>
  <div class="h-full transition-all" :class="[isMobile ? 'p-0' : '']">
    <div class="h-full overflow-hidden" :class="getMobileClass">
      <NLayout class="z-40 transition" :class="getContainerClass" has-sider>
        <NWatermark
          content="ChatMASTER"
          cross
          fullscreen
          :font-size="14"
          :line-height="14"
          :font-color="getFontColor"
          :width="384"
          :height="384"
          :x-offset="12"
          :y-offset="60"
          :rotate="-15"
        />
        <Logo />
        <Sider />
        <NLayoutContent class="h-full">
          <RouterView v-slot="{ Component, route }">
            <component :is="Component" :key="route.fullPath" />
          </RouterView>
        </NLayoutContent>
      </NLayout>
    </div>
  </div>
</template>
