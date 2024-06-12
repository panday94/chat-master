<template>
  <div class="dashboard-container">
    <div class="top">
      <div class="title">工作台</div>
      <div class="welcome">
        <div>
          <el-avatar shape="circle" :size="100" :src="userData.avatar"></el-avatar>
        </div>
        <div class="center">
          <div class="slogan">
            <span id="gushici"></span>
          </div>
          <div class="logan">欢迎您，{{ userData.nickName }} ！</div>
          <div class="position">{{ userData.deptName }}</div>
        </div>
        <div class="right">
          <div class="data">
            <div class="text">平台用户</div>
            <div class="number">{{ totalData.allUserCount }}</div>
          </div>
          <div class="data">
            <div class="text">模型数</div>
            <div class="number">{{ totalData.allModelCount }}</div>
          </div>
          <div class="data">
            <div class="text">订单金额</div>
            <div class="number">{{ totalData.allOrderAmount }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="content">
      <div class="left">
        <div class="project">
          <div class="title">
            <div class="title-left">项目介绍</div>
            <div class="title-right">
              <el-link href="https://www.yuque.com/the6/ct0azl/ehxcgoy0xg41l9c3" type="primary" target="_blank">查看部署文档</el-link>
            </div>
          </div>
          <div>
            <div style="margin: 14px 40px;">
              <div style="display: flex; align-items:center">欢迎使用 chat-master开源大模型系统！开源地址：<el-link href="https://gitee.com/panday94/chat-master" type="primary" target="_blank">传送门</el-link></div>
              <div>个人能力有限，诚邀志同道合的伙伴一起加入开发chat-master-plus。如有需求可查看readme添加作者微信！</div>
              <div><span style="color:darkgreen;"></span>移动端体验：<el-link href="https://gpt.panday94.xyz" type="primary" target="_blank">传送门</el-link></div>
            </div>
            <el-row :gutter="20">
              <el-col :sm="24" :lg="24">
                <blockquote class="text-warning" style="font-size: 14px">
                  领取阿里云通用云产品1888优惠券
                  <br />
                  <el-link
                    href="https://www.aliyun.com/minisite/goods?userCode=iqguofg4"
                    type="primary"
                    target="_blank"
                  >https://www.aliyun.com/minisite/goods?userCode=iqguofg4</el-link>
                  <br />领取腾讯云通用云产品2860优惠券
                  <br />
                  <el-link
                    href="https://curl.qcloud.com/11y0ob0f"
                    type="primary"
                    target="_blank"
                  >https://curl.qcloud.com/11y0ob0f</el-link>
                  <br />阿里云服务器折扣区
                  <el-link
                    href="https://www.aliyun.com/minisite/goods?userCode=iqguofg4"
                    type="primary"
                    target="_blank"
                  >>☛☛点我进入☚☚</el-link>&nbsp;&nbsp;&nbsp; 腾讯云服务器秒杀区
                  <el-link
                    href="https://curl.qcloud.com/11y0ob0f"
                    type="primary"
                    target="_blank"
                  >>☛☛点我进入☚☚</el-link>
                  <br />
                  <h4 class="text-danger">云产品通用红包，可叠加官网常规优惠使用。(仅限新用户)</h4>
                </blockquote>
              </el-col>
            </el-row>
          </div>
        </div>
        <div class="project">
          <div class="title">
            <div class="title-left">动态</div>
            <div class="title-right" @click="jumpTo('monitor/log/operlog')">更多</div>
          </div>
          <div class="log">
            <div class="log-content" v-for="item in logData" :key="item.id">
              <el-avatar
                shape="circle"
                :size="35"
                src="https://img1.baidu.com/it/u=1706103831,1363884341&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
              ></el-avatar>
              <div class="log-parent">
                <div class="log-font">
                  {{ item.createUser }}在
                  <span class="log-hover">{{ item.address }}</span>
                  操作了{{ item.title }}模块，
                  内容为：
                  <span
                    class="log-hover"
                  >{{ item.operation }}</span>
                </div>
                <div class="log-time">{{ item.createTime }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="right">
        <div class="project">
          <div class="title">
            <div class="title-left">快速开始 / 便捷导航</div>
          </div>
          <div class="nav">
            <div class="nav-content" @click="jumpTo('data')">数据中心</div>
            <div class="nav-content" @click="jumpTo('chat/chat')">任务中心</div>
            <div class="nav-content" @click="jumpTo('order/order')">订单管理</div>
            <div class="nav-content" @click="jumpTo('member/member')">会员中心</div>
            <div class="nav-content" @click="jumpTo('config/model')">模型管理</div>
            <div class="nav-content" @click="jumpTo('assistant/type')">助手中心</div>
            <div class="nav-content" @click="jumpTo('config/base')">站点配置</div>
          </div>
        </div>
        <div class="project">
          <div class="title">
            <div class="title-left">数据分析</div>
          </div>
          <div class="chart1" id="chart1"></div>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script>
import * as echarts from "echarts";
import { getUserProfile } from "@/api/sys/user";
import { getTotalData } from "@/api/sys/statistics";
import { list } from "@/api/monitor/operlog";
export default {
  name: "Index",
  data() {
    return {
      userData: {},
      totalData: {},
      logData: []
    };
  },
  created() {
    this.getUserInfo();
    this.getLogData();
    this.getTotalData();
  },
  mounted() {
    this.getData();
    this.getGushi();
  },
  methods: {
    jumpTo(url) {
      this.$router.push(url);
    },
    getUserInfo() {
      getUserProfile().then(res => {
        this.userData = res.data;
      });
    },
    getLogData() {
      var query = {
        current: 1,
        size: 10
      };
      list(query).then(res => {
        this.logData = res.data.records;
      });
    },
    getTotalData() {
      getTotalData().then(res => {
        this.totalData = res.data;
      });
    },
    getGushi() {
      var xhr = new XMLHttpRequest();
      xhr.open("get", "https://v1.jinrishici.com/all.json");
      xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
          var data = JSON.parse(xhr.responseText);
          var gushici = document.getElementById("gushici");
          gushici.innerText = data.content;
        }
      };
      xhr.send();
    },
    getData() {
      var option = {
        title: {
          text: ""
        },
        legend: {
          data: ["对话", "绘画", "智能体"],
          bottom: 10
        },
        radar: {
          shape: "circle",
          center: ["50%", "45%"],
          radius: "60%",
          indicator: [
            { name: "ChatGPT", max: 50 },
            { name: "文心一言", max: 6500 },
            { name: "通义千问", max: 16000 },
            { name: "讯飞星火", max: 30000 },
            { name: "智谱清言", max: 38000 }
          ]
        },
        series: [
          {
            type: "radar",
            data: [
              {
                value: [2, 4200, 3000, 20000, 35000],
                name: "对话"
              },
              {
                value: [1, 5000, 14000, 28000, 26000],
                name: "绘画"
              },
              {
                value: [1, 5000, 14000, 28000, 26000],
                name: "智能体"
              }
            ]
          }
        ]
      };
      var chartDom = document.getElementById("chart1");
      console.info(chartDom);
      var myChart = echarts.init(chartDom);
      myChart.setOption(option);
    }
  }
};
</script>
  
  <style lang="scss" scoped>
.dashboard-container {
  background-color: rgb(240, 242, 245);

  .top {
    padding: 20px;
    background-color: #fff;

    .title {
      color: rgba(0, 0, 0, 0.85);
      font-weight: 600;
      font-size: 20px;
      line-height: 32px;
      margin-bottom: 20px;
    }

    .welcome {
      display: flex;

      .center {
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin-left: 25px;

        .slogan {
          margin-bottom: 12px;
          color: rgba(0, 0, 0, 0.85);
          font-weight: 500;
          font-size: 20px;
          line-height: 22px;
        }

        .logan {
          margin-bottom: 12px;
          color: rgba(0, 0, 0, 0.85);
          font-weight: 450;
          font-size: 18px;
          line-height: 20px;
        }

        .position {
          color: rgba(0, 0, 0, 0.45);
          line-height: 22px;
          font-size: 14px;
        }
      }

      .right {
        display: flex;
        flex: 1;
        justify-content: flex-end;
        align-items: center;

        .data {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          padding: 0 20px;

          &:nth-of-type(2) {
            height: 100%;
            position: relative;

            &::before,
            &::after {
              position: absolute;
              height: 40%;
              width: 1px;
              background-color: #f0f0f0;
              top: 30%;
              content: "";
            }

            &::before {
              left: 0;
            }

            &::after {
              right: 0;
            }
          }

          .text {
            margin-bottom: 4px;
            color: rgba(0, 0, 0, 0.45);
            font-size: 14px;
          }

          .number {
            color: rgba(0, 0, 0, 0.85);
            font-size: 24px;
            margin-top: 5px;
          }
        }
      }
    }
  }

  .content {
    display: flex;
    padding: 0 20px;
    padding-bottom: 20px;

    .left {
      display: flex;
      flex-direction: column;
      width: 65%;
    }

    .right {
      flex: 1;
      flex-direction: column;
      margin-left: 20px;
    }
  }

  .project {
    margin-top: 20px;
    background-color: #fff;

    .title {
      min-height: 58px;
      line-height: 58px;
      display: flex;
      justify-content: space-between;
      border-bottom: 1px solid #e6ebf5;

      .title-left {
        padding-left: 20px;
        color: rgba(0, 0, 0, 0.85);
        font-weight: 500;
        font-size: 16px;
      }

      .title-right {
        padding-right: 20px;
        color: #1890ff;
        cursor: pointer;
        font-size: 14px;
      }
    }

    .app {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      grid-template-rows: 2;

      .app-content {
        margin: -1px -1px 0px 0px;
        border-radius: 0px;

        .app-content-top {
          display: flex;
          align-items: center;

          .name {
            margin-left: 12px;
            color: rgba(0, 0, 0, 0.85);
            font-size: 14px;
            line-height: 24px;
            font-weight: 500;
          }
        }

        .app-content-center {
          margin-top: 10px;
          color: rgba(0, 0, 0, 0.45);
          line-height: 22px;
          font-size: 14px;
        }

        .app-content-bottom {
          margin-top: 10px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 12px;
          line-height: 20px;
        }
      }
    }

    .log {
      padding: 0 20px;

      .log-content {
        display: flex;
        align-items: center;
        height: 80px;
        border-bottom: 1px solid #e6ebf5;
        margin-left: 20px;

        .log-parent {
          margin-left: 20px;

          .log-font {
            font-size: 14px;
          }

          .log-hover {
            font-size: 14px;
            color: #409eff;
            cursor: pointer;
          }

          .log-time {
            margin-top: 5px;
            font-size: 14px;
            color: rgba(0, 0, 0, 0.25);
          }
        }
      }
    }

    .nav {
      padding: 20px;
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      grid-template-rows: 2;

      .nav-content {
        height: 32px;
        line-height: 32px;
        color: rgba(0, 0, 0, 0.85);
        font-size: 14px;
        cursor: pointer;

        &:hover {
          color: #409eff;
        }
      }
    }

    .wxapp {
      padding: 0 20px;
      padding-bottom: 20px;
      display: grid;
      grid-template-columns: repeat(2, 1fr);

      .wxapp-content {
        display: flex;
        align-items: center;
        margin-top: 10px;

        .wxapp-font {
          color: rgba(0, 0, 0, 0.85);
          font-size: 14px;
          cursor: pointer;
          margin-left: 10px;

          &:hover {
            color: #409eff;
          }
        }
      }
    }

    .chart1 {
      height: 400px;
      width: 100%;
    }
  }
}
</style>