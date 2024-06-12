<script setup lang='ts'>
import { onMounted, ref } from "vue";
import { fetchAgreement } from "@/api/common";
import { useRoute } from "vue-router";

const content = ref<string>("");
const route = useRoute();

onMounted(() => {
  const type: number = Number(route.query.type);
  fetchAgreement<any>(type).then(res => {
    if (res.code === 200) {
      content.value = res.data.content;
    }
  });
});
</script>

<template>
  <div class="flex justify-center p-8">
    <div class="w-[50%]" v-html="content" />
  </div>
</template>