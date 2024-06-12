<template>
  <div ref="waterRef" class="waterfall-container overflow-hidden overflow-y-auto no-scrollbar">
    <div
      class="waterfall-item bg-sky-50 hover:bg-sky-100 dark:bg-[#24272e] hover:dark:bg-[#18181c] rounded-md cursor-pointer"
      v-for="item in waterfallList"
      :style="{
                  top: item.top + 'px',
                  left: item.left + 'px',
                  width: defaultOptions.waterfallWidth + 'px',
                  height: item.height + 'px'
              }"
      @click="clickAssistant(item.id)"
    >
      <div
        class="waterfall-img-box"
        v-if="item.coverImg"
        :style="{ height: item.imgHeight + 'px' }"
      >
        <img
          class="waterfall-img"
          :style="{ width: defaultOptions.waterfallWidth - 20 + 'px' }"
          :src="item.coverImg"
        />
      </div>
      <div class="item-info" :style="{ width: defaultOptions.waterfallWidth + 'px' }">
        <div class="item-content font-medium">{{ parseEmoji(item.icon) }} {{ item.title }}</div>
        <div class="item-content text-xs leading-5 mb-1">{{ item.description }}</div>
        <div class="px-2.5 flex justify-between items-center">
          <div class="flex items-center text-slate-600 dark:text-slate-300">
            <SvgIcon icon="icon-park-outline:star-one" />
            <div class="ml-1 text-xs">{{ item.stars ? item.stars : '1.2w' }}</div>
          </div>
          <div
            class="hover:bg-white hover:text-blue-600 hover:rounded-md w-6 h-6 flex items-center justify-center"
            @click.stop="editAssistant(item.systemPrompt)"
          >
            <SvgIcon icon="line-md:edit-twotone" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
    
  <script lang="ts" setup>
import { computed, ref, watch, onMounted, onBeforeUnmount } from "vue";
import { SvgIcon } from "@/components/common";
import { listAssistantByType } from "@/api/common";
import * as emoji from "node-emoji";

// props参数类型
interface propsType {
  assistantTypeId: number;
}

// 事件
interface Emit {
  (ev: "handAssistant", data: number): void;
  (ev: "editAssistant", data: string): void;
}

const props = defineProps<propsType>();
const emit = defineEmits<Emit>();

// 瀑布流默认配置项，单位为px
interface defaultOptionsTyp {
  waterfallWidth: number; // 每一列的宽度
  waterfallHeight: number; // 图片下方有内容的高度、如果图片下方有内容此处必须有值
  waterfallCol: number; //多少列
  waterfallRight: number; //右边距 / 两列图片间的间隔
  waterfallBottom: number; //下边距
  waterfallMargin: number; //外边距
  waterfallDeviationHeigth: any[]; //存放瀑布流各个列的高度
}

const defaultOptions = ref<defaultOptionsTyp>({
  waterfallWidth: 40,
  waterfallHeight: 40,
  waterfallCol: 2,
  waterfallRight: 10,
  waterfallBottom: 10,
  waterfallMargin: 10,
  waterfallDeviationHeigth: []
});

// 计算每项的宽度(即图片/内容宽度)
const calculationValue = async () => {
  // 获取容器元素
  const waterfallListObj = <HTMLElement>(
    document.querySelector(".waterfall-container")
  );
  // 容器宽度
  const containerWidth = waterfallListObj.offsetWidth;
  // 内容宽度 = （容器宽度 - 两列图片间的间隔 * (列数 - 1) - 外边距/内容与容器边框的距离 * 2）/ 列数
  // (列数 - 1) ----> 多列之间的间隔数
  // 外边距/内容与容器边框的距离 * 2  ---->  只有最两侧有边距
  defaultOptions.value.waterfallWidth =
    (containerWidth -
      defaultOptions.value.waterfallRight *
        (defaultOptions.value.waterfallCol - 1) -
      defaultOptions.value.waterfallMargin * 2) /
    defaultOptions.value.waterfallCol;
  // 初始化偏移高度数组,该数组用于存放每一列的高度
  defaultOptions.value.waterfallDeviationHeigth = new Array(
    defaultOptions.value.waterfallCol
  );
  for (
    let i = 0;
    i < defaultOptions.value.waterfallDeviationHeigth.length;
    i++
  ) {
    defaultOptions.value.waterfallDeviationHeigth[i] = 0;
  }
  loadingData();
};

// 加载内容
const loadingData = async () => {
  for (
    let i = current.value === 1 ? 0 : (current.value - 1) * size.value;
    i < waterfallList.value.length;
    i++
  ) {
    const aImg = new Image();
    aImg.src = waterfallList.value[i]["coverImg"];
    // 注意：图片加载完成的顺序不一样的，所以在页面显示图片的顺序每次都可能不一样
    aImg.onload = aImg.onerror = (e: any) => {
      // 获取容器元素
      const itemInfoObj = document.querySelectorAll(".item-info") as NodeListOf<
        HTMLElement
      >;
      // 容器宽度
      const itemInfoHeight = itemInfoObj[i].offsetHeight;
      // 图片高度按比例缩放
      waterfallList.value[i].imgHeight =
        (defaultOptions.value.waterfallWidth / (aImg.width || 10)) *
        aImg.height;
      // 获取图片高度比
      waterfallList.value[i].imgHeightPercent =
        (waterfallList.value[i].imgHeight /
          defaultOptions.value.waterfallWidth) *
        100;
      // 整体高度 = 图片高度 `
      waterfallList.value[i].height =
        itemInfoHeight + waterfallList.value[i].imgHeight + 20;
      // 图片加载失败就设为空
      if (e.type === "error") {
        waterfallList.value[i].coverImg = "";
      }
      // 记录最后一个元素位置
      lastItem.value = itemInfoObj[i];
      // 进行瀑布流布局
      waterfallFlowLayout(waterfallList.value[i]);
    };
  }
};
const waterfallFlowLayout = (item: any) => {
  // 找到最短的列的索引 ---> 索引代表当前数据在第几列
  const shortestIndex = getShortestCol();
  // 获取每块数据距离顶部的距离
  item["top"] = defaultOptions.value.waterfallDeviationHeigth[shortestIndex];
  // 计算距离左边的距离
  // left = 索引 * （距离右边的距离 + 内容宽度）+ 外边距
  // 索引为 0 时 即数据在第一列距离左边的距离就是外边距
  item["left"] =
    shortestIndex *
      (defaultOptions.value.waterfallRight +
        defaultOptions.value.waterfallWidth) +
    defaultOptions.value.waterfallMargin;
  // 内容高度 = 图片高度 + 距离底部的距离
  defaultOptions.value.waterfallDeviationHeigth[shortestIndex] +=
    item.height + defaultOptions.value.waterfallBottom;
};
/**
 * 找到最短的列并返回索引
 * @returns {number} 索引
 */
const getShortestCol = () => {
  // 获取最短的内容
  const shortest = Math.min.apply(
    null,
    defaultOptions.value.waterfallDeviationHeigth
  );
  // 返回索引
  return defaultOptions.value.waterfallDeviationHeigth.indexOf(shortest);
};

// 选择快捷助手
function clickAssistant(assistantId: number) {
  emit("handAssistant", assistantId);
}

// 编辑快捷助手
function editAssistant(systemPrompt: string) {
  emit("editAssistant", systemPrompt);
}

// 助手
interface Assistant {
  id: number;
  title: string;
  icon: string;
  tag: string;
  description: string;
  systemPrompt: string;
  stars: string;
  coverImg: string;
  top: string;
  left: string;
  imgHeight: number;
  imgHeightPercent: number;
  height: number;
}

//存放计算好的数据
const typeId = computed(() => {
  return props.assistantTypeId;
});
const waterfallList = ref<Array<Assistant>>([]);
const newWaterfallList = ref<Array<Assistant>>([]);
const current = ref<number>(1);
const size = ref<number>(20);
const waterRef = ref();
const lastItem = ref();
const observer: any = ref(null);

// 使用 watch 来监听 waterfallDataList 的变化
watch(typeId, async newVal => {
  observer.value.unobserve(lastItem.value);
  current.value = 1;
  waterfallList.value = [];
  await loadWaterfallData();
  setTimeout(() => {
    waterRef.value.scrollTo({ top: 0, behavior: "auto" });
    observer.value.observe(lastItem.value);
  }, 500);
});

const handleScroll = async () => {
  observer.value.unobserve(lastItem.value);
  if (newWaterfallList.value && newWaterfallList.value.length == 0) {
    return;
  }
  current.value++;
  await loadWaterfallData();
  setTimeout(() => {
    // 将观察器对象绑定在列表中的最后一个子元素上，以便能够监听它的可见性变化
    observer.value.observe(lastItem.value);
  }, 500);
};

onMounted(async () => {
  // 加载数据
  await loadWaterfallData();
  // options 中将容器元素作为根节点（root）
  const options = {
    root: waterRef.value,
    threshold: 0.5 //将阈值设置为 0.5，表示当观察元素的一半进入视图时就触发回调函数
  };
  observer.value = new IntersectionObserver(entries => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        handleScroll();
      }
    });
  }, options);
  setTimeout(() => {
    // 将观察器对象绑定在列表中的最后一个子元素上，以便能够监听它的可见性变化
    observer.value.observe(lastItem.value);
  }, 500);
});

onBeforeUnmount(() => {
  // 停止监听
  observer.value.disconnect();
});

// 加载数据
async function loadWaterfallData() {
  const res = await listAssistantByType<Array<Assistant>>({
    current: current.value,
    size: size.value,
    typeId: typeId.value
  });
  newWaterfallList.value = res.data;
  waterfallList.value = waterfallList.value.concat(res.data);
  if (current.value === 1) {
    await calculationValue();
  } else {
    await loadingData();
  }
}

// 处理带表情文字
function parseEmoji(str: string) {
  return emoji.emojify(str);
}
</script>
    
  <style scoped lang="less">
// * {
//     margin: 0;
//     padding: 0;
// }

.waterfall-container {
  position: relative;
  margin: 0 auto;
  width: 510px;
  height: 100%;
}

.waterfall-container:after {
  content: "";
  display: block;
  clear: both;
}

.waterfall-item {
  float: left;
  position: absolute;

  .waterfall-img-box {
    padding: 8px 8px 0 8px;
    border-radius: 10px;

    .waterfall-img {
      border-radius: 10px;
      height: 100%;
    }
  }

  .item-info {
    position: relative;
    top: 10px;
    height: auto;

    .item-content {
      position: relative;
      top: 0px;
      padding: 0 10px;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      /*设置方向*/
      -webkit-line-clamp: 3;
      /*设置超过为省略号的行数*/
      overflow: hidden;
    }
  }
}

.waterfall-item:last-child {
  margin-bottom: 0px;
}
</style>
    
    