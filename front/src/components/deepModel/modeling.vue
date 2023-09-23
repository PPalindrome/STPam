<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="4">
        <el-select v-model="value" :placeholder="$t('menu.modeling.model')" @focus="queryModel" @change="queryTreeInfo">
          <el-option
            v-for="item in models.ModelData"
            :key="item.mname"
            :label="item.mname"
            :value="item.filename"
          />
        </el-select>
        <el-input v-model="filterText" :placeholder="$t('button.search')"/>
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
        {{$t('menu.modeling.theme')}}
        <el-button @click="changeTheme($event)">{{$t('menu.modeling.dark')}}</el-button>
        <el-button>{{ $t('menu.modeling.optimize') }}</el-button>
        <el-button>{{ $t('menu.modeling.sensitive') }}</el-button>
        <el-popconfirm
            :icon="InfoFilled"
            icon-color="#626AEF"
            @confirm="saveFileCode"
            :title="$t('menu.modeling.confim')">
          <template #reference>
            <el-button type="primary" style="float: right">{{ $t('menu.modeling.save') }}</el-button>
          </template>
        </el-popconfirm>
        <codemirror
            v-model="fileCode"
            :placeholder="$t('menu.modeling.edit')"
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

import {InfoFilled,} from '@element-plus/icons-vue'

// 数据
let fileCode = ref(``);
let selectValue = "python"
let log = console.log

// 语言下拉列表
import {getCurrentInstance} from "vue";
//？表示前面如果返回null，后面不执行
let $this=getCurrentInstance()?.appContext.config.globalProperties
const language = ref('')
let dateTime
let lan=$this.$i18n.locale
if(lan="en")dateTime="Dark"
else dateTime="黑暗主题"
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
//选择模型
const value=ref('')
const models =reactive({
  ModelData: [
    {
      filename: '暂无',
      version: '暂无',
      mname: '暂无',
      author: '暂无',
      date: '暂无',
    },
  ]
})
const queryModel=()=>{
  services.post('/queryModels').then(res=>{
    models.ModelData=res
  })
}

const options = reactive({
  style: {height: "500px"},
  mode: "text/x-c++src",
  spellcheck: true,
  autofocus: true,
  indentWithTab: true,
  tabSize: 2,
  extensions: [python(), oneDark], //传递给CodeMirror EditorState。创建({扩展})
});


// 方法
// 失去焦点时,使用已编辑的代码
const useEditedCode = () => {
  console.log("@@@blur@@@code:", fileCode.value);
  console.log("@@@blur@@@cpp:", cpp);
}

// 改变主题
const changeTheme = (e) => {
  console.log("extensions:", options.extensions);
  if (e.target.innerHTML === "Dark"||e.target.innerHTML === "黑暗主题") {
    // console.log("dark!!!!!changge !!!!!")
    options.extensions = [];
    if(lan=="en") dateTime = e.target.innerHTML = "Daytime";
    else dateTime = e.target.innerHTML = "白日主题";
  } else {
    options.extensions = [oneDark];
    if(lan=="en") dateTime = e.target.innerHTML = "Dark";
    else dateTime = e.target.innerHTML = "黑暗主题";
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

//显示目录树
const queryTreeInfo = (e) => {
  console.log("e是："+e)
  services.post('/directoryStructure/getFileDirectoryStructure',e).then(res => {
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