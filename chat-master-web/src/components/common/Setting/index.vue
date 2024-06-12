<script setup lang='ts'>
import { computed, onMounted, ref } from "vue";
import {
  NModal,
  NAvatar,
  NForm,
  FormInst,
  NFormItem,
  NInput,
  NUpload,
  NSwitch,
  NTooltip,
  NIcon,
  NButton,
  useMessage,
  UploadFileInfo
} from "naive-ui";
import { Person } from "@vicons/ionicons5";
import { updateUser, updatePassword, updateContext } from "@/api/user";
import { useAuthStoreWithout } from "@/store/modules/auth";
import { useAppStore, useAuthStore } from '@/store'
import { router } from "@/router";
import { SvgIcon } from "@/components/common";
import { t } from "@/locales";
import { copyToClip } from "@/utils/copy";
import defaultAvatar from "@/assets/avatar.png";

const ms = useMessage();
const authStore = useAuthStoreWithout();
const appStore = useAppStore()

const active = ref<string>("base");
const loading = ref(false);
const formRef = ref<FormInst | null>(null);
const baseUrl = {
  baseURL: import.meta.env.VITE_GLOB_API_URL
};
const token = useAuthStore().token;

interface Props {
  visible: boolean;
}

interface Emit {
  (e: "update:visible", visible: boolean): void;
}

const props = defineProps<Props>();

const emit = defineEmits<Emit>();

const show = computed({
  get() {
    return props.visible;
  },
  set(visible: boolean) {
    emit("update:visible", visible);
  }
});

interface User {
  avatar: string;
  name: string;
  nickName: string;
  tel: string;
  context: boolean;
  num: number;
  oldPassword: string;
  newPassword: string;
}
const userValue = ref<User>({
  avatar: "",
  name: "",
  nickName: "",
  tel: "",
  context: false,
  num: 0,
  oldPassword: "",
  newPassword: ""
});

const rules = {
  name: {
    required: true,
    message: "请输入昵称",
    trigger: ["input", "blur"]
  },
  tel: {
    required: true,
    pattern: /^[1][3456789]\d{9}$/,
    message: "请输入正确的手机号码",
    trigger: ["input", "blur"]
  },
  oldPassword: [
    {
      required: true,
      message: "请输入旧密码",
      trigger: ["input", "blur"]
    },
    {
      trigger: ["blur", "change"],
      pattern: /^(?=^.*?[a-z])(?=^.*?\d).{6,32}$/,
      message: "请输入6-32位包含字母和数字的密码"
    }
  ],
  newPassword: [
    {
      required: true,
      message: "请输入新密码",
      trigger: ["input", "blur"]
    },
    {
      trigger: ["blur", "change"],
      pattern: /^(?=^.*?[a-z])(?=^.*?\d).{6,32}$/,
      message: "请输入6-32位包含字母和数字的密码"
    }
  ]
};

onMounted(async () => {
  if (!authStore.session) {
    router.push("/login");
    return;
  }
  await authStore.getSession();
  userValue.value = {
    avatar: authStore.session.avatar ? authStore.session.avatar : defaultAvatar,
    name: authStore.session.name,
    nickName: authStore.session.nickName,
    tel: authStore.session.tel,
    context: authStore.session.context,
    num: authStore.session.num,
    oldPassword: "",
    newPassword: ""
  };
});

/** 页面跳转 */
function handleJump(value: string) {
  active.value = value;
}

/** 关闭弹窗 */
function handleClose() {
  show.value = false;
}

/** 开启/关闭上下文 */
async function handleContext(value: boolean) {
  userValue.value.context = value;
  updateContext(userValue.value).then(res => {
    if (value) {
      ms.success("开启成功");
    } else {
      ms.warning("关闭成功");
    }
    authStore.getSession();
  });
}

/** 分享ChatMASTER */
async function handleShare() {
  let text: string = "http://gpt.panday94.xyz";
  await copyToClip(text);
  ms.success("复制成功");
}

/** 开通会员 */
function handleOpenMember() {
  ms.warning("功能暂未开放");
}

/** 修改个人信息 */
async function handleUpdateUser() {
  formRef.value?.validate(async (errors: any) => {
    if (errors) {
      return;
    }
    try {
      loading.value = true;
      updateUser(userValue.value).then(res => {
        if (res.code === 200) {
          ms.success("修改成功");
          show.value = false;
          authStore.getSession();
        } else {
          ms.error(res.msg ?? "修改失败");
        }
      });
    } finally {
      loading.value = false;
    }
  });
}

/** 上传头像成功回调 */
const handleUploadFinish = ({
  file,
  event
}: {
  file: UploadFileInfo;
  event?: ProgressEvent;
}) => {
  console.info((event?.target as XMLHttpRequest).response);
  const res = JSON.parse((event?.target as XMLHttpRequest).response);
  if (res.code != 200) {
    ms.error(res.msg ?? "头像修改失败");
    return;
  }
  userValue.value.avatar = res.data.fileUrl;
  authStore.getSession();
  ms.success("头像修改成功");
};

/** 修改密码 */
async function handleUpdatePassword() {
  formRef.value?.validate(async (errors: any) => {
    if (errors) {
      return;
    }
    if (userValue.value.oldPassword == userValue.value.newPassword) {
      ms.error("新密码与旧密码相同");
      return;
    }
    try {
      loading.value = true;
      updatePassword(userValue.value).then(res => {
        if (res.code === 200) {
          ms.success("修改成功");
          show.value = false;
        } else {
          ms.error(res.msg ?? "修改失败");
        }
      });
    } finally {
      loading.value = false;
    }
  });
}

/** 退出登录 */
function handleLogout() {
  authStore.removeToken();
  ms.success(t("退出成功"));
  appStore.setTheme("light");
  router.push("/login");
}
</script>
<style scoped>
.user-form {
  height: 45px;
  width: 380px;
  border-radius: 8px;
  display: flex;
  align-items: center;
}
</style>

<template>
  <NModal v-model:show="show" :auto-focus="false">
    <div class="w-4/12 bg-slate-50 dark:bg-[#18181c] p-6">
      <div v-if="active == 'base'" class="flex flex-col justify-center items-center">
        <div class="self-end cursor-pointer" @click="handleClose">
          <SvgIcon icon="ant-design:close-outlined" class="text-xl" />
        </div>
        <div class="flex justify-between items-center">
          <div class="flex flex-col justify-center items-center">
            <NAvatar
              v-if="userValue.avatar != ''"
              round
              object-fit="fill"
              :size="100"
              :src="userValue.avatar"
            />
            <NAvatar v-else round :size="100">
              <NIcon size="40">
                <Person />
              </NIcon>
            </NAvatar>
            <div class="mt-3 font-medium">{{ userValue.nickName }}</div>
            <div v-if="userValue.tel" class="mt-2 text-xs">手机号：{{ userValue.tel }}</div>
          </div>
          <div class="ml-28 flex flex-col justify-center items-center">
            <div class="flex items-center">
              <div class="font-medium text-[15px]">剩余电量</div>
              <NTooltip trigger="hover">
                <template #trigger>
                  <SvgIcon icon="octicon:info-16" class="ml-1" />
                </template>
                分享好友即可获得电量，开通会员可享更多权益
              </NTooltip>
            </div>
            <div class="mt-2 flex items-center">
              <span class="text-[15px]">{{ userValue.num }}</span>
              <SvgIcon icon="mingcute:lightning-fill" class="ml-1 text-2xl text-yellow-400" />
            </div>
            <div class="mt-4">
              <NButton block type="primary" @click="handleOpenMember">开通会员</NButton>
            </div>
          </div>
        </div>
        <div class="w-full mt-4 bg-white dark:bg-[#18181c] border dark:border-[#24272e] rounded-xl">
          <div
            class="w-full h-12 px-4 flex items-center border-b dark:border-[#24272e] cursor-pointer hover:bg-slate-200 hover:dark:bg-[#24272e]"
            @click="handleJump('general')"
          >
            <div class="w-7 h-7 bg-blue-600 flex items-center justify-center rounded-lg">
              <SvgIcon icon="basil:user-solid" class="text-xl text-white" />
            </div>
            <div class="ml-3 text-[17px] text-blue-600">设置个人资料</div>
            <div class="flex flex-1 justify-end">
              <SvgIcon icon="formkit:right" class="text-xl" />
            </div>
          </div>
          <div
            class="w-full h-12 px-4 flex items-center border-b dark:border-[#24272e] cursor-pointer hover:bg-slate-200 hover:dark:bg-[#24272e]"
          >
            <div class="w-7 h-7 bg-indigo-600 flex items-center justify-center rounded-lg">
              <SvgIcon icon="bitcoin-icons:message-filled" class="text-xl text-white" />
            </div>
            <div class="ml-3 text-[17px]">开启上下文</div>
            <div class="flex flex-1 justify-end">
              <NSwitch v-model:value="userValue.context" @update:value="handleContext" />
            </div>
          </div>
          <div
            class="w-full h-12 px-4 flex items-center cursor-pointer hover:bg-slate-200 hover:dark:bg-[#24272e]"
            @click="handleShare"
          >
            <div class="w-7 h-7 bg-green-600 flex items-center justify-center rounded-lg">
              <SvgIcon icon="majesticons:share" class="text-xl text-white" />
            </div>
            <div class="ml-3 text-[17px]">分享ChatMASTER给好友</div>
            <div></div>
          </div>
        </div>
        <div class="w-full mt-4 bg-white dark:bg-[#18181c] border dark:border-[#24272e] rounded-xl">
          <div
            class="w-full h-12 px-4 flex items-center border-b dark:border-[#24272e] cursor-pointer hover:bg-slate-200 hover:dark:bg-[#24272e]"
            @click="handleJump('password')"
          >
            <div class="w-7 h-7 bg-red-500 flex items-center justify-center rounded-lg">
              <SvgIcon icon="solar:lock-password-bold" class="text-xl text-white" />
            </div>
            <div class="ml-3 text-[17px]">修改密码</div>
            <div class="flex flex-1 justify-end">
              <SvgIcon icon="formkit:right" class="text-xl" />
            </div>
          </div>
          <div
            class="w-full h-12 px-4 flex items-center cursor-pointer hover:bg-slate-200 hover:dark:bg-[#24272e]"
            @click="handleLogout"
          >
            <div class="w-7 h-7 bg-purple-600 flex items-center justify-center rounded-lg">
              <SvgIcon icon="line-md:logout" class="text-xl text-white" />
            </div>
            <div class="ml-3 text-[17px]">退出登录</div>
          </div>
        </div>
      </div>
      <!-- 个人信息修改 -->
      <div v-if="active == 'general'" class="flex flex-col justify-center items-center">
        <div class="self-start cursor-pointer" @click="handleJump('base')">
          <SvgIcon icon="icon-park-solid:back" class="text-xl text-blue-600" />
        </div>
        <div class="cursor-pointer relative">
          <NUpload
            :show-file-list="false"
            :action="baseUrl.baseURL + '/app/user/avatar'"
            :headers="{
                        'Authorization': token
                    }"
            @finish="handleUploadFinish"
          >
            <NAvatar v-if="userValue.avatar != ''" round :size="100" :src="userValue.avatar" />
            <NAvatar v-else round :size="100">
              <NIcon size="40">
                <Person />
              </NIcon>
            </NAvatar>
            <SvgIcon
              icon="raphael:photo"
              class="text-4xl text-blue-600 absolute right-[-8px] bottom-[4px] z-10"
            />
          </NUpload>
        </div>
        <NForm ref="formRef" :model="userValue" :rules="rules">
          <NFormItem label="昵称" path="name">
            <NInput class="user-form" v-model:value="userValue.nickName" placeholder="请输入昵称" />
          </NFormItem>
          <NFormItem label="手机号" path="tel">
            <NInput
              class="user-form"
              :disabled="true"
              v-model:value="userValue.tel"
              placeholder="请输入手机号"
            />
          </NFormItem>
          <NButton class="user-form" block type="primary" @click="handleUpdateUser">确 认</NButton>
        </NForm>
      </div>
      <!-- 密码修改 -->
      <div v-if="active == 'password'" class="flex flex-col justify-center items-center">
        <div class="self-start cursor-pointer" @click="handleJump('base')">
          <SvgIcon icon="icon-park-solid:back" class="text-xl text-blue-600" />
        </div>
        <div class="cursor-pointer relative">
          <NAvatar v-if="userValue.avatar != ''" round :size="100" :src="userValue.avatar" />
          <NAvatar v-else round :size="100">
            <NIcon size="40">
              <Person />
            </NIcon>
          </NAvatar>
        </div>
        <NForm ref="formRef" :model="userValue" :rules="rules">
          <NFormItem label="旧密码" path="oldPassword">
            <NInput
              class="user-form"
              v-model:value="userValue.oldPassword"
              type="password"
              placeholder="请输入旧密码"
            />
          </NFormItem>
          <NFormItem label="新密码" path="newPassword">
            <NInput
              class="user-form"
              v-model:value="userValue.newPassword"
              type="password"
              placeholder="请输入新密码"
            />
          </NFormItem>
          <NButton class="user-form" block type="primary" @click="handleUpdatePassword">确 认</NButton>
        </NForm>
      </div>
    </div>
  </NModal>
</template>
