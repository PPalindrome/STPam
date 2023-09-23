<template>
  <div>
    <h4 class="sec-title">帮助手册</h4>
    <div style="text-align: center;">
      <p class="p-service">
          模块介绍
      </p>
    </div>
    <div style="padding: 1rem">
      <el-row>
      <el-col :span="5 " class="ab1 main-about-grid1">
      <img src="../../assets/dataset.png" style="width: 3vw;height: 6vh">
        <h4 class="aboutbtm-head">多源时空数据</h4>
        <h5>查看数据</h5>
        <p>查看已经拉取到服务器可直接使用的数据</p>
        <h5>数据预处理</h5>
        <p>对已上传的数据进行预处理</p>
        <h5>数据源适配</h5>
        <p>适配外部数据源进行数据下载</p>

      </el-col>
      <el-col :span="5" class="ab2 main-about-grid2">
        <img src="../../assets/coding.png" style="width: 3vw;height: 6vh;margin-top:2rem;margin-left: 2rem">
        <h4 class="aboutbtm-head" style="margin-left: 2rem">在线建模</h4>
        <h5 style="margin-left: 2rem">在线建模</h5>
        <p style="margin-left: 2rem">在线编辑模型代码并进行保存</p>
      </el-col>
      <el-col :span="5" class="ab3 main-about-grid3">
        <img src="../../assets/time.png" style="width: 3vw;height: 6vh;margin-top:2rem;margin-left: 2rem">
        <h4 class="aboutbtm-head" style="margin-left: 2rem">时空过程任务发布</h4>
        <h5 style="margin-left: 2rem">时空过程任务发布</h5>
        <p style="margin-left: 2rem">待编辑</p>
      </el-col>
      <el-col :span="5" class="ab4 main-about-grid4">
        <img src="../../assets/compute.png" style="width: 3vw;height: 6vh;margin-top:2rem;margin-left: 2rem">
        <h4 class="aboutbtm-head" style="margin-left: 2rem">时空过程高效计算</h4>
        <h5 style="margin-left: 2rem">分布式计算</h5>
        <p style="margin-left: 2rem">待编辑</p>
        <h5 style="margin-left: 2rem">增量学习</h5>
        <p style="margin-left: 2rem">待编辑</p>
      </el-col>
        <el-col :span="5" class="ab5 main-about-grid5">
          <img src="../../assets/product.png" style="width: 3vw;height: 6vh;margin-top:2rem;margin-left: 2rem">
          <h4 class="aboutbtm-head" style="margin-left: 2rem">产品示范</h4>
          <h5 style="margin-left: 2rem">示范应用</h5>
          <p style="margin-left: 2rem">待编辑</p>
          <h5 style="margin-left: 2rem">产品展示</h5>
          <p style="margin-left: 2rem">待编辑</p>
        </el-col>
      </el-row>
    </div>
  </div>

  <div style="text-align: center;">
    <p class="p-service">
      具体示例
    </p>
  </div>
  <el-carousel :interval="5000" arrow="always" height="60vh" indicator-position="outside">
    <el-carousel-item v-for="item in carouseData" :key="item">
      <div style="width:120vw;height: 60vh" class="container">
        <el-row>
          <el-col :span="10">
          <div style="padding: 4vh">
                <h2 class="titleone" style="font-size: 1.5vw">{{item.head}}</h2>
                <p style="font-size: 1vw;margin-top: 2vh">{{item.content}}</p>
          </div>
          </el-col>
          <el-col :span="12" >
           <div style="width:55vw;height: 55vh;margin-top:3vh ">
              <img :src="(item.url)" alt="" style="width:95%;height:100%"/>
           </div>
          </el-col>
        </el-row>
      </div>

    </el-carousel-item>
  </el-carousel>
</template>

<style scoped>
.container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 1vw;
}
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
  font-size: 1rem;
}

.el-carousel__item{
  background-color: #07ad87;
}
div#services {
  padding-bottom: 7em;
}
.main-pos {
  position: relative;
}
.sec-title {
  font-size: 3vw;
  text-transform: uppercase;
  margin-bottom: 0.5em;
  color: #07ad87;
  text-align: center;
  font-weight: 500;
}
.help-manual{
  font-size: 4em;
  padding: 0px 12px 6px;
  letter-spacing: 1px;
  text-transform: uppercase;
  color: rgba(216, 216, 216, 0.79);
  font-weight: 800;
}
.p-service{
  line-height: 1.8;
  font-size: 2vw;
  color: #000;
  letter-spacing: 1px;
  width: 70%;
  margin: 1em auto;
}
.main-about-grid1 {
  border-top: 8px solid #4ddba6;
}
.ab1 {
  height: 35rem;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  width: 20%;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  padding: 2em;
  -webkit-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  -moz-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
}
.ab1 h4{
  font-size: 1.5vw;
}
.ab1 h5{
  font-size: 1vw;
}
.ab1 p{
  font-size: 1vw;
}
.main-about-grid2 {
  border-top: 8px solid #fc7e46;
}
.ab2 {
  height: 35rem;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  width: 20%;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  -webkit-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  -moz-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
}
.ab2 h4{
  font-size: 1.5vw;
}
.ab2 h5{
  font-size: 1vw;
}
.ab2 p{
  font-size: 1vw;
}
.main-about-grid3 {
  border-top: 8px solid #00b9ef;
}
.ab3 {
  height: 35rem;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  width: 20%;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  -webkit-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  -moz-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
}
.ab3 h4{
  font-size: 1.5vw;
}
.ab3 h5{
  font-size: 1vw;
}
.ab3 p{
  font-size: 1vw;
}
.main-about-grid4 {
  border-top: 8px solid #eb5273;
}
.ab4 {
  height: 35rem;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  width: 20%;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  -webkit-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  -moz-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
}
.ab4 h4{
  font-size: 1.5vw;
}
.ab4 h5{
  font-size: 1vw;
}
.ab4 p{
  font-size: 1vw;
}
h4.aboutbtm-head {
  color: #000;
  text-transform: capitalize;
  font-weight: 300;
  margin: 0.2em 0 0.5em;
}
.main-about-grid5 {
  border-top: 8px solid #F09F00;
}
.ab5 {
  height: 35rem;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  width: 20%;
  -webkit-flex: 1;
  -ms-flex: 1;
  flex: 1;
  -webkit-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  -moz-box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
  box-shadow: 5px 10px 10px 0px rgba(216, 216, 216, 0.78);
}
.ab5 h4{
  font-size: 1.5vw;
}
.ab5 h5{
  font-size: 1vw;
}
.ab5 p{
  font-size: 1vw;
}
h5.aboutbtm-head {
  color: #000;
  text-transform: capitalize;
  font-weight: 300;
  margin: 0.2em 0 0.5em;
  font-size: 2em;
}
.ab1 h5 {
  color: #4ddba6;
  letter-spacing: 1px;
  text-transform: uppercase;
  font-weight: 600;
  margin: 1em 0 0.2em;
}
.ab2 h5 {
  color:  #fc7e46;
  letter-spacing: 1px;
  text-transform: uppercase;
  font-weight: 600;
  margin: 1em 0 0.2em;
  margin-left: 20px;
}
.ab3 h5 {
  color:  #00b9ef;
  letter-spacing: 1px;
  text-transform: uppercase;
  font-weight: 600;
  margin: 1em 0 0.2em;
  margin-left: 20px;
}
.ab4 h4{
  margin-left: 20px;
}
.ab4 h5 {
  color: #eb5273;
  letter-spacing: 1px;
  text-transform: uppercase;
  font-weight: 600;
  margin: 1em 0 0.2em;
  margin-left: 20px;
}
.ab4 p {
  margin-left: 20px;
}
.ab5 h4{
  margin-left: 20px;
}
.ab5 h5 {
  color: #F09F00;
  letter-spacing: 1px;
  text-transform: uppercase;
  font-weight: 600;
  margin: 1em 0 0.2em;
  margin-left: 20px;
}
.ab5 p {
  margin-left: 20px;
}
p {
  margin: 0;
  line-height: 1.8;
  color: #000;
  letter-spacing: 1px;
}
span h2{
  font-size: 1.5vw;
}
span p{
  font-size: 1vw;
  margin-top: 1vw;
}
</style>


<script setup>
import image1 from '../../assets/img1.png'
import image2 from '../../assets/img2.png'
import image3 from '../../assets/img3.png'
import image4 from '../../assets/img4.png'
import image5 from '../../assets/img5.png'
import image6 from '../../assets/img6.png'
const carouseData = [
  {url: image1,head:"数据源配置",content:"这一部分主要是对于数据源进行配置。首先可以在表单中进行数据源类型的选择，" +
        "目前提供了Mysql、Orcal、PostgreSql以及HDFS。其次就可以根据选择的数据源类型来填写需要配置数据源的具体内容。最后根据配置好的内容" +
        "点击开始适配按钮即可完成适配。适配好后会展示出数据库中的表信息，选择相应的数据表来进行下载。"},
  {url: image2,head:"多源数据信息上传",content: "在这一部分可以选择上传本地数据文件到服务器，也可以选择适配远程数据源进行数据文件下载" +
        "如果选择上传本地数据，选择好数据文件点击上传至服务器按钮即可；如果选择适配远程数据源则会跳转到数据源配置页面进行数据源适配"+
        "此外该页面还会分页展示已上传到服务器的数据文件"
  },
  {url: image3,head:"在线建模"},
  {url: image4,head:"时空过程任务发布"},
  {url: image5,head:"分布式计算"},
  {url: image6,head:"增量学习"}
]
</script>
