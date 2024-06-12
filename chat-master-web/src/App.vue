<script setup lang="ts">
import { NConfigProvider } from "naive-ui";
import { NaiveProvider } from "@/components/common";
import { useTheme } from "@/hooks/useTheme";
import { useLanguage } from "@/hooks/useLanguage";

const { theme, themeOverrides } = useTheme();
const { language } = useLanguage();
</script>

<template>
  <NConfigProvider
    class="h-full"
    :theme="theme"
    :theme-overrides="themeOverrides"
    :locale="language"
  >
    <!-- App.vue -->
    <n-message-provider>
      <content />
    </n-message-provider>
    <NaiveProvider>
      <RouterView />
    </NaiveProvider>
  </NConfigProvider>
</template>
