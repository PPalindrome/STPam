<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="4">
        <el-upload
            ref="modelUploadRef"
            action="http://localhost:8081/files/upload/model"
            :before-upload="beforeUploadModel"
            :on-success="fileUploadSuccess"
        >
          <template #trigger>
            <el-button type="primary" >选择文件并上传&#8194;<el-icon><UploadFilled/></el-icon></el-button>
          </template>
        </el-upload>
        <el-input v-model="filterText" placeholder="搜索"/>
        <el-scrollbar height="600px">
        <el-tree
            ref="treeRef"
            class="filter-tree"
            :data="fileTreeInfo.fileTree"
            :props="defaultProps"
            default-expand-all
            :filter-node-method="filterNode"
            @node-click="getFileContent"
        />
        </el-scrollbar>
      </el-col>

      <el-col :span="20">
        主题：
        <el-button @click="changeTheme($event)">黑暗主题（点击切换主题）</el-button>
        &ensp;编程语言：
        <el-select v-model="language" class="m-2" placeholder="选择语言（默认python）">
          <el-option
              v-for="item in list"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @click="changeMode($event)"
          />
        </el-select>
        &ensp;说明文档：
        <el-button text @click="dialogVisible = true" type="warning">帮助文档</el-button>
        <el-dialog
            v-model="dialogVisible"
            title="代码编辑说明文档"
            width="30%"
            :before-close="handleClose"
        >
          <span>代码编辑说明文档</span>
          <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确认</el-button>
      </span>
          </template>
        </el-dialog>
        <el-popconfirm
            :icon="InfoFilled"
            icon-color="#626AEF"
            @confirm="saveFileCode"
            title="您确定要保存当前更改的代码吗?">
          <template #reference>
            <el-button type="primary" style="float: right">保存</el-button>
          </template>
        </el-popconfirm>
        <codemirror
            v-model="fileCode"
            placeholder="在此编辑您的代码..."
            :style="options.style"
            :mode="options.mode"
            :spellcheck="options.spellcheck"
            :autofocus="options.autofocus"
            :indent-with-tab="options.indentWithTab"
            :tabSize="options.tabSize"
            :extensions="options.extensions"
            @ready="log('ready', $event)"
            @change="log('change', $event)"
            @focus="log('focus', $event)"
            @blur="useEditedCode"
            style="margin-top: 1%"
        />

      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import {Codemirror} from "vue-codemirror";
import {python} from "@codemirror/lang-python";
import {cpp} from "@codemirror/lang-cpp";

import {oneDark} from "@codemirror/theme-one-dark";
import {reactive, ref, watch, toRefs, onMounted} from "vue";
import {ElMessageBox, ElTree} from 'element-plus'
import services from "../../utils/request";
import axios from "axios";
import {InfoFilled,} from '@element-plus/icons-vue'
import {shouldTooltipConfine} from "echarts/types/src/component/tooltip/helper";

// onMounted(() => {
//   queryTreeInfo()
// })
// 数据
let fileCode = ref(``);
let selectValue = "python"
let dateTime = "黑暗主题（点击切换主题）"
let log = console.log
let currentName:any=''
const beforeUploadModel=(rawFile)=>{
  currentName=rawFile.name
  return true
}
const options = reactive({
  style: {height: "650px"},
  mode: "text/x-c++src",
  spellcheck: true,
  autofocus: true,
  indentWithTab: true,
  tabSize: 2,
  extensions: [python(), oneDark], //传递给CodeMirror EditorState。创建({扩展})
});

const fileUploadSuccess=(res)=>{
  if(res.code==200){
    queryTreeInfo()
  }else{
    alert("错误！！！"+res.message)
  }
}
// 方法
// 失去焦点时,使用已编辑的代码
const useEditedCode = () => {
  console.log("@@@blur@@@code:", fileCode.value);
  console.log("@@@blur@@@cpp:", cpp);
}

// 改变主题
const changeTheme = (e) => {
  console.log("extensions:", options.extensions);
  if (e.target.innerHTML === "黑暗主题（点击切换主题）") {
    // console.log("dark!!!!!changge !!!!!")
    options.extensions = [];
    dateTime = e.target.innerHTML = "白色主题（点击切换主题）";
  } else {
    options.extensions = [oneDark];
    dateTime = e.target.innerHTML = "黑暗主题（点击切换主题）";
  }
}

// 改变模式
const changeMode = (e) => {
  console.log("selectValue:", language.value);
  if (language.value === "python") {
    if (dateTime === "黑暗主题（点击切换主题）")
      options.extensions = [python(), oneDark];
    else
      options.extensions = [python()];
    // selectValue = "cpp";
    // e.target.innerHTML = "python";
    options.mode = "text/x-python";
  } else {
    if (dateTime === "黑暗主题（点击切换主题）")
      options.extensions = [cpp(), oneDark];
    else options.extensions = [cpp()];
    // selectValue = "python";
    // e.target.innerHTML = "C++";
    options.mode = "text/x-c++src";
  }
}

// 下拉列表
const language = ref('')
const list = [
  {
    value: 'python',
    label: 'python',
  },
  {
    value: 'c++',
    label: 'c++',
  },
]

//帮助文档
const dialogVisible = ref(false)

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('您确定要关闭此帮助文档吗？')
      .then(() => {
        done()
      })
      .catch(() => {
        // catch error
      })
}

// 树形控件
interface Tree {
  id: number
  label: string
  children?: Tree[]
}

const filterText = ref('')
const treeRef = ref<InstanceType<typeof ElTree>>()

const defaultProps = {
  children: 'children',
  label: 'label',
}

watch(filterText, (val) => {
  treeRef.value!.filter(val)
})

const filterNode = (value: string, data: Tree) => {
  if (!value) return true
  return data.label.includes(value)
}
//文件数目录信息
const fileTreeInfo = reactive({
  fileTree: []
})

const queryTreeInfo = () => {
  // console.log(currentName)
  services.post('/directoryStructure/getFileDirectoryStructure',currentName).then(res => {
    fileTreeInfo.fileTree = res
  })
}
//文件相对请求路径
let filePath:string = ''
//树点击事件
const getFileContent = (v, e) => {
  filePath=''
  while(e.parent){
    filePath="/"+e.label+filePath
    e=e.parent
  }
  if(e.children){
    filePath="/"+e.label+filePath
  }
  console.log("树的目录是："+filePath)
  services.post('/fileEdit/getCode',filePath).then(res => {
    // console.log(res)
    fileCode.value = res
  })

}

//文件数组
const file=reactive({
  label:"",
  path:"",
  content:"",
})
//保存更改的文件代码
const saveFileCode = () => {
  console.log("当前文件路径为：",filePath)
  file.path=filePath
  file.content=fileCode.value
  services.post('/fileEdit/saveCode',file).then(res => {
    if(res){
      console.log("保存返回信息成功：")
    }
  })
}
</script>