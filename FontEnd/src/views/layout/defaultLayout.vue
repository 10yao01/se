<template>
  <el-container direction="vertical" :padding="0">
    <!-- 导航栏 -->
    <el-header>
      <el-row :gutter="20" type="flex" align="center" class="header">
        <el-col :span="6" :offset="0" class="title">实验动物房管理子系统</el-col>
        <el-col :span="16" :offset="0">导航横向预留区域</el-col>
        <el-col :span="2" :offset="0">
          <el-dropdown>
            <div class="userDropdown">
              <el-avatar type="primary" src="../../static/images/default_avatar.png">
              </el-avatar>
              <span class="username">admin</span><i class="el-icon-arrow-down el-icon--right"></i>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>
    </el-header>
    <el-container direction="horizontal">
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <el-menu class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :router="true">

        </el-menu>
      </el-aside>
      <!-- 主窗口 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  mounted () {
  },
  methods: {
    handleOpen (key, keyPath) {
      console.log(key, keyPath)
    },
    handleClose (key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>
<style scoped>
.el-container {
  height: 100%;
}

.el-header {
  background-color: #2a354e;
  color: white;
}

.el-aside {
  background-color: #f0f0f3;
}

.el-main {
  background-color: #f5f5f5;
  height: calc(100vh - 60px);
}

.header{
  height: 60px;
  display: flex;
  align-items: center;
}

.title{
  font-size: 24px;
  font-weight: bold;
}
.userDropdown{
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  color: white;
}
.username{
  margin-left: 8px;
}

.mapContainer{
  height: 100%;
}
</style>
