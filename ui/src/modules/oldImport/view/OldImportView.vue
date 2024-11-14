<template>
    <div class="page">
      <el-alert type="warning" :closable="false">
        注: 只会导入分类、标签、文章、友链、附件、用户、评论等数据库数据，如数据已存在则会覆盖更新!，
        附件资源请手动将老版本根目录下resources/upload/attach目录中的内容复制到当前版本根目录resources/upload目录中,
        以上步骤操作完毕后,如出现附件访问不到的问题,可在系统管理->附件存储策略中调整默认存储策略中的存储路径位置
      </el-alert>
      <el-alert type="error" :closable="false">
        特别注意: 该插件建议在系统刚安装完毕后使用,不建议产生数据后再进行导入, 会造成新数据丢失!!!导入完成后请进行退出登录再登录操作!!!
      </el-alert>
      <el-form
          ref="addFormRef"
          :model="addForm"
          label-width="120px"
          status-icon
          :rules="addRule"
      >
        <el-form-item>
        </el-form-item>
        <el-form-item label="数据库IP" prop="dataBaseIp">
          <el-input v-model="addForm.dataBaseIp" placeholder="请输入老版本数据库IP"/>
        </el-form-item>

        <el-form-item label="数据库端口" prop="dataBasePort">
          <el-input v-model="addForm.dataBasePort" placeholder="请输入老版本数据库端口"/>
        </el-form-item>

        <el-form-item label="数据库名称" prop="dataBaseName">
          <el-input v-model="addForm.dataBaseName" placeholder="请输入老版本数据库名称"/>
        </el-form-item>

        <el-form-item label="数据库账户" prop="dataBaseUserName">
          <el-input v-model="addForm.dataBaseUserName" placeholder="请输入老版本数据库账户"/>
        </el-form-item>

        <el-form-item label="数据库密码" prop="dataBasePassword">
          <el-input v-model="addForm.dataBasePassword" placeholder="请输入老版本数据库密码"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitAddForm" :loading="loading">开始导入</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>
<script setup>
import {reactive, ref} from "vue";
import {oldVersionImportApi} from "@/modules/oldImport/api/oldImportApi.js";
import {ElMessage} from "element-plus";

let loading = ref(false);

const addForm = ref({
  dataBaseIp: '',
  dataBasePort: '',
  dataBaseName: '',
  dataBaseUserName: '',
  dataBasePassword: ''
});
const addRule = reactive({
  dataBaseIp: [{required: true, message: '请输入老版本数据库IP', trigger: 'blur'}],
  dataBasePort: [{required: true, message: '请输入老版本数据库端口', trigger: 'blur'}],
  dataBaseName: [{required: true, message: '请输入老版本数据库名称', trigger: 'blur'}],
  dataBaseUserName: [{required: true, message: '请输入老版本数据库账户', trigger: 'blur'}],
  dataBasePassword: [{required: true, message: '请输入老版本数据库密码', trigger: 'blur'}],

});
const addFormRef = ref();

function submitAddForm() {
  addFormRef.value.validate(valid => {
    if (valid) {
      loading.value = true;
      oldVersionImportApi(addForm.value).then(res => {
        if (res.code ===200) {
          ElMessage.success('导入完成');
        } else {
          ElMessage.success('导入失败');
        }
        loading.value = false;
      })
    }
  })
}

function resetAddForm() {
  addForm.value = {
    id: '',
    name: '',
    thumbnail: '',
    color: '',
    slug: ''
  }
  if (addFormRef.value) {
    addFormRef.value.resetFields();
  }
}

</script>
<style scoped></style>