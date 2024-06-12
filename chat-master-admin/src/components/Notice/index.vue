<template>
  <div>
    <div @click="noticeClick">
      <svg-icon icon-class="notice" />
      <el-badge class="badge" :value="unReadCount"></el-badge>
    </div>
    <div>
      <el-drawer
        title="消息提醒"
        size="20%"
        append-to-body
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose"
      >
        <template slot="title">
          <div>
            消息提醒
            <el-button
              style="margin-left: 10px"
              @click="readAllClick()"
              size="mini"
              round
              plain
              type="primary"
              >一键已读</el-button
            >
          </div>
        </template>
        <div class="notice-content">
          <el-tabs v-model="activeType" @tab-click="handleClick">
            <el-tab-pane label="已读" name="1"></el-tab-pane>
            <el-tab-pane label="未读" name="0"></el-tab-pane>
          </el-tabs>
          <ul class="infinite-list" v-infinite-scroll="load">
            <li
              class="infinite-list-item"
              v-for="item in noticeData"
              :key="item.id"
            >
              <div class="item">
                <div class="item-read">
                  <div class="read">{{ readTips }}</div>
                </div>
                <div class="item-content">
                  <div class="content-title">
                    <div>{{ item.title }}</div>
                    <div>{{ item.createTime }}</div>
                  </div>
                  <div class="content-info">
                    <div v-if="item.type == 1">
                      <el-link @click="noticeContentShow(item)" type="info"
                        >通知消息，请点击查看详情</el-link
                      >
                    </div>
                    <div v-if="item.type == 2">
                      <div v-html="item.content"></div>
                    </div>
                  </div>
                  <el-button
                    class="readButton"
                    v-show="readShow"
                    @click="readClick(item.id)"
                    icon="el-icon-check"
                    size="mini"
                    round
                    >已读</el-button
                  >
                </div>
              </div>
            </li>
          </ul>
        </div>
      </el-drawer>
    </div>
    <div>
      <el-dialog :title="noticeTitle" :visible.sync="noticeContentView">
        <div v-html="noticeContent"></div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  listNoticeBySysUser,
  getUnreadCount,
  readNotice,
  readNoticeAll,
} from "@/api/sys/notice";

export default {
  data() {
    return {
      drawer: false,
      readShow: false,
      noticeContentView: false,
      direction: "rtl",
      total: 0,
      lastTotal: 0,
      activeType: 0,
      unReadCount: null,
      noticeTitle: null,
      noticeContent: null,
      readTips: "未读",
      noticeData: [],
      // 查询参数
      queryParams: {
        current: 1,
        size: 10,
        type: null,
      },
    };
  },
  created() {
    // this.getUnReadCount();
  },
  mounted() {
    //在mounted 声明周期中创建定时器
    // const timer = setInterval(() => {
    //   // 这里调用调用需要执行的方法，1为自定义的参数，由于特殊的需求它将用来区分，定时器调用和手工调用，然后执行不同的业务逻辑
    //   this.getUnReadCount();
    // }, 60000); // 每两秒执行1次
    // // 通过$once来监听定时器，在beforeDestroy钩子可以被清除
    // this.$once("hook:beforeDestroy", () => {
    //   // 在页面销毁时，销毁定时器
    //   clearInterval(timer);
    // });
  },
  methods: {
    noticeClick() {
      this.drawer = true;
      this.readShow = true;
      this.queryParams.type = this.activeType;
      this.getList();
    },
    handleClick() {
      this.queryParams.current = 1;
      this.queryParams.type = this.activeType;
      this.readTips = this.activeType == 0 ? "未读" : "已读";
      this.readShow = this.activeType == 0 ? true : false;
      this.noticeData = [];
      this.getList();
    },
    load() {
      if (this.lastTotal < 10) {
        return;
      }
      this.queryParams.current = this.queryParams.current + 1;
      this.getList();
    },
    handleClose(done) {
      this.queryParams.current = 1;
      this.noticeData = [];
      done();
    },
    readClick(id) {
      var data = {
        id: id,
      };
      readNotice(data).then((response) => {
        this.$modal.msgSuccess("已读成功");
        this.queryParams.current = 1;
        this.noticeData = [];
        // this.getUnReadCount();
        this.getList();
      });
    },
    readAllClick() {
      readNoticeAll().then((response) => {
        this.$modal.msgSuccess("一键已读成功");
        this.queryParams.current = 1;
        this.noticeData = [];
        // this.getUnReadCount();
        this.getList();
      });
    },
    noticeContentShow(item) {
      this.noticeContentView = true;
      this.noticeTitle = item.title;
      this.noticeContent = item.content;
    },
    /** 查询公告列表 */
    getList() {
      listNoticeBySysUser(this.queryParams).then((response) => {
        this.noticeData = this.noticeData.concat(response.data.records);
        this.total = response.data.total;
        this.lastTotal = response.data.records.length;
      });
    },
    /** 查询公告列表 */
    getUnReadCount() {
      getUnreadCount().then((response) => {
        this.unReadCount = response.data;
        if (this.unReadCount > 99) {
          this.unReadCount = "...";
        }
        if (this.unReadCount === 0) {
          this.unReadCount = null;
        }
      });
    },
  },
};
</script>

<style lang="scss">
.navbar {
  .el-drawer__header {
    margin-bottom: 0px;
  }
  ul {
    display: block;
    list-style-type: disc;
    margin-block-start: 0em;
    margin-block-end: 0em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 0px;
  }
}
</style>

<style lang="scss" scoped>
.badge {
  position: absolute;
  right: -2px;
  top: -5px;
  font-size: 12px;
  transform: scale(0.9);
}
.notice-content {
  margin-left: 20px;
  .infinite-list {
    display: flex;
    flex-direction: column;
    list-style-type: none;
    .infinite-list-item {
      width: 100%;
      padding-right: 20px;
      padding-bottom: 20px;
      border-bottom: 1px solid #f2f2f2;
      .item,
      .item-content,
      .content-title {
        display: flex;
        .item-read {
          width: 50px;
          height: 50px;
          display: flex;
          justify-content: center;
          align-items: center;
          margin-right: 10px;
          .read {
            height: 20px;
            width: 35px;
            font-size: 14px;
            line-height: 20px;
            text-align: center;
            background-color: coral;
            color: #fff;
          }
        }
        .item-content {
          flex: 1;
          flex-direction: column;
          .content-title {
            font-size: 15px;
            color: #333;
            justify-content: space-between;
            :first-child {
            }
          }
          .content-info {
            line-height: 25px;
            font-size: 14px;
            color: #666;
          }
          .readButton {
            align-self: flex-end;
          }
        }
      }
    }
  }
}
</style>

