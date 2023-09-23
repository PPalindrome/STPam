<template>
  <div ref="chart" style="width: 100%;height: 100%"></div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import * as echarts from 'echarts'
import chinaMap from '../../assets/maps/china.json'

const chart = ref();

onMounted(() => {
  init();
})

function init() {
  var myMap = echarts.init(chart.value);
  echarts.registerMap('china', chinaMap);

  var option = {
    title: {
      text: '长江中游洪涝灾害过程分析挖掘示范应用'
    },
    series: [{
      type: 'map',
      map: 'china',
      data: [
        {name: '湖北省', value: 500},
        {name: '河南省', value: 130},
        {name: '湖南省', value: 200},
        {name: '安徽省', value: 120},
        {name: '山西省', value: 50},
      ],
      label: {
        show: true,
        color: '#fff',
        fontSize: 10
      },
      itemStyle: {
        areaColor: '#219edb',  //区域颜色
        borderColor: '#fff',
      },
      emphasis: {
        label: {
          show: true,
          color: '#000',
          fontSize: 12
        },
        itemStyle: {
          areaColor: '#1bc1ad',  //区域颜色
          borderColor: '#ccc',
        },
      },
      zoom: 1.2,
    }],
    visualMap: {
      min: 0,
      max: 500,
      text: ['高', '低'],
      realtime: false,
      calculable: true,
      inRange: {
        color: ['lightskyblue', 'blue', 'red','purple']
      }
    },
  };

  myMap.setOption(option);

}
</script>

<style scoped>

</style>