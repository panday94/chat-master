<template>
  <div :class="className" id="chart" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from "echarts";
import "echarts/theme/macarons.js";
import resize from './mixins/resize'
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(document.getElementById("chart"))
      this.setOptions(this.chartData)
      //建议加上以下这一行代码，不加的效果图如下（当浏览器窗口缩小的时候）。超过了div的界限（红色边框）
      window.addEventListener("resize", function () {
        this.chart.resize();
      });

    },
    setOptions({ dateString, userData, taskData, orderCountData, orderAmountData } = {}) {
      this.chart.setOption({
        title: {
          text: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['注册数', '对话数', '订单数','订单金额']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: dateString
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '注册数',
            type: 'line',
            stack: 'Total',
            data: userData
          },
          {
            name: '对话数',
            type: 'line',
            stack: 'Total',
            data: taskData
          },
          {
            name: '订单数',
            type: 'line',
            stack: 'Total',
            data: orderCountData
          },
          {
            name: '订单金额',
            type: 'line',
            stack: 'Total',
            data: orderAmountData
          }
        ]
      })
    }
  }
}
</script>
