<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-result icon="success" :title="`欢迎你${name}`" subTitle="请在左侧导航栏操作">
          <!-- <template slot="extra">
            <el-button type="primary" size="medium">返回</el-button>
          </template> -->
        </el-result>
      </el-col>
    </el-row>
    <!-- <h1>欢迎你 </h1> -->
  </div>
</template>
<script>
export default {
  data () {
    return {
      name: ''
    }
  },
  mounted () {
    this.name = window.localStorage.getItem('name')
  }
}
</script>
