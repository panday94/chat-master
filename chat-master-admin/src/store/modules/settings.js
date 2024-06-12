import defaultSettings from "@/settings";
import { getConfigKey } from "@/api/sys/common";
const {
  sideTheme,
  showSettings,
  topNav,
  tagsView,
  fixedHeader,
  sidebarLogo,
  dynamicTitle,
} = defaultSettings;
const storageSetting = JSON.parse(localStorage.getItem("layout-setting")) || "";
const state = {
  title: "",
  theme: storageSetting.theme || "#409EFF",
  sideTheme: storageSetting.sideTheme || sideTheme,
  showSettings: showSettings,
  topNav: storageSetting.topNav === undefined ? topNav : storageSetting.topNav,
  tagsView:
    storageSetting.tagsView === undefined ? tagsView : storageSetting.tagsView,
  fixedHeader:
    storageSetting.fixedHeader === undefined
      ? fixedHeader
      : storageSetting.fixedHeader,
  sidebarLogo:
    storageSetting.sidebarLogo === undefined
      ? sidebarLogo
      : storageSetting.sidebarLogo,
  dynamicTitle:
    storageSetting.dynamicTitle === undefined
      ? dynamicTitle
      : storageSetting.dynamicTitle,
  IMMoudule: false, //新增是否开启IM模块
};
const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (state.hasOwnProperty(key)) {
      state[key] = value;
    }
  },
  CHANGE_IM_STATUS: (state, val) => {
    state.IMMoudule = val;
  },
};

const actions = {
  // 修改布局设置
  changeSetting({ commit }, data) {
    commit("CHANGE_SETTING", data);
  },

  //获取IM配置状态
  changeIMstatus({ commit }) {
    getConfigKey("sys.module.IM").then((res) => {
      commit("CHANGE_IM_STATUS", res.data === "true");
    });
  },

  // 设置网页标题
  setTitle({ commit }, title) {
    state.title = title;
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
