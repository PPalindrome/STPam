<template>

  <div>
    <div ref="chart" style="width: 80%;height: 400px"></div>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted} from "vue";
import * as echarts from 'echarts';

//创建dom引用
const chart = ref();
//获取父组件参数（也可以通过解构方式，将props替换为{chartType,chartData},就不需要在下面props.啥啥啥了
const props=defineProps({
  chartType: {
    type:String
  },
  chartData:{
    type:Array
  }
})
onMounted(()=>{
  init();
})
// 基于准备好的dom，初始化echarts实例
function init() {
  let myChart = echarts.init(chart.value);


// 指定图表的配置项和数据
  let option = {
    title: {
      text: '多源时空数据分析'
    },
    tooltip: {},
    legend: {
      data: ['水位']
    },
    xAxis: {
      data: ['黄冈', '武汉', '鄱阳湖', '洞庭湖', '汉江', '长江']
    },
    yAxis: {},
    series: [
      {
        name: '水位',
        type: props.chartType,
        data: props.chartData,
      }
    ]
  };


// 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
}
</script>

<style scoped>

</style>