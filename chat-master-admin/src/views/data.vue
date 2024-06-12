<template>
  <div class="dashboard-editor-container">

    <div class="dashboard-search">
      <el-form inline>
        <el-row>
          <el-form-item>
            <el-button @click="handleYearClick">年</el-button>
          </el-form-item>
          <el-form-item>
            <el-button ref="monthClick" @click="handleMonthClick">月</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="handleDayClick">日</el-button>
          </el-form-item>
          <el-form-item>
            <el-date-picker ref="datePicker" v-model="dateRange" type="daterange" align="right" range-separator="至"
              start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions" style="width: 400px;"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="">
            <el-button type="primary" @click="search">筛选</el-button>
            <el-button @click="reset">重置</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>

    <panel-group @handleSetLineChartData="handleSetLineChartData" :chart-data="panelChartData"/>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart :chart-data="raddarChartData"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart :chart-data="pieChartData" />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart :chart-data="barChartData"/>
        </div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import { getLineData, getBarData, getTotalData, getPieData, getRaddarData } from "@/api/sys/statistics"
import { parseTime } from "@/utils/common.js";

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145],
    ticketCount: [15, 22, 32, 11, 40, 28, 55],
    ticketPrice: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

const raddarChartData = [
  [
    "星期二",
    21,
    11
  ],
  [
    "星期三",
    23,
    12
  ],
  [
    "星期四",
    12,
    22
  ],
  [
    "星期五",
    1,
    8
  ],
  [
    "星期六",
    22,
    27
  ],
  [
    "星期日",
    23,
    12
  ],
  [
    "星期一",
    43,
    23
  ],
  [
    "星期二",
    15,
    2
  ]
]

const pieChartData = {
  "a": 12,
  "b": 8,
  "c": 30,
  "d": 15,
  "e": 3
}

const barChartData = [
  {
    "date": "2023-01-25",
    "value": 12
  },
  {
    "date": "2023-01-26",
    "value": 14
  },
  {
    "date": "2023-01-27",
    "value": 4
  },
  {
    "date": "2023-01-28",
    "value": 8
  },
  {
    "date": "2023-01-29",
    "value": 2
  },
  {
    "date": "2023-01-30",
    "value": 0
  },
  {
    "date": "2023-01-31",
    "value": 19
  }
]

export default {
  name: 'Data',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      queryParams: {},
      dateRange: [], // 选中的日期范围
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      panelChartData: {},
      lineChartData: lineChartData.newVisitis,
      raddarChartData: raddarChartData,
      pieChartData: pieChartData,
      barChartData: barChartData,
    }
  },
  created() {
    this.initData();
  },
  methods: {
    handleSetLineChartData(type) {
      // this.lineChartData = lineChartData[type]
    },
    handleYearClick() {
      const end = new Date()
      const start = new Date()
      start.setFullYear(start.getFullYear() - 1)
      this.dateRange = [parseTime(start, '{y}-{m}-{d}'), parseTime(end, '{y}-{m}-{d}')]
      this.search();
    },
    handleMonthClick() {
      const end = new Date()
      const start = new Date()
      start.setMonth(start.getMonth() - 1)
      this.dateRange = [parseTime(start, '{y}-{m}-{d}'), parseTime(end, '{y}-{m}-{d}')]
      this.search();
    },
    handleDayClick() {
      const end = new Date()
      const start = new Date()
      this.dateRange = [parseTime(start, '{y}-{m}-{d}'), parseTime(end, '{y}-{m}-{d}')]
      this.search();
    },
    reset() {
      this.dateRange = [];
      this.search();
    },
    initData() {
      this.search();
    },
    search() {
      this.queryParams.startDate = this.dateRange[0];
      this.queryParams.endDate = this.dateRange[1];
      this.getTotalData();
      this.getLineData();
      this.getRaddarChartData();
      this.getPieChartData();
      this.getBarChartData();
    },
    getTotalData() {
      getTotalData(this.queryParams).then(res => {
        this.panelChartData = res.data;
        console.info(this.panelChartData)
      })
    },
    getLineData() {
      getLineData(this.queryParams).then(res => {
        this.lineChartData = {
          dateString: this._.map(res.data, 'dateString'),
          userData: this._.map(res.data, 'userData'),
          taskData: this._.map(res.data, 'taskData'),
          orderCountData: this._.map(res.data, 'orderCountData'),
          orderAmountData: this._.map(res.data, 'orderAmountData')
        }
      })
    },
    getRaddarChartData() {
      getRaddarData(this.queryParams).then(res => {
        let data = res.data;
        data.unshift(['product', '对话', "绘画"]);
        this.raddarChartData = data;
      })
    },
    getPieChartData() {
      getPieData(this.queryParams).then(res => {
        this.pieChartData = res.data;
      })
    },
    getBarChartData() {
      getBarData(this.queryParams).then(res => {
        this.barChartData = res.data;
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 20px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

.dashboard-search {
  /* padding: 10px; */
  padding-top: 20px;
  padding-left: 20px;
  padding-bottom: 10px;
  /* margin: 0 20px; */
  color: #505050;
  background-color: #FFFFFF;
  border-radius: 5px;
  margin-bottom: 30px;
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
