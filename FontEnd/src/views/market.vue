<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <h5>当前用户：  <el-tag size="medium">{{ name }}</el-tag>&nbsp;&nbsp;&nbsp;<el-button type="danger" size="mini" @click="$router.push('/login')" plain>切换账户</el-button></h5>
      <el-row :gutter="20">
        <el-col :span="6" :offset="0" v-for="item in link" :key="item.baseURL" style="margin-bottom: 20px;">
          <el-card shadow="hover" class="box-card" style="cursor: pointer;height:70vh;" @click.native="handle(item.routeName)">
            <el-image style="width: 350px; height: 300px" :src="item.src"></el-image>
            <h3 style="margin-top: 0;">{{item.name}}</h3>
            <span>{{item.desc}}</span>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      link: [
        {
          routeName: '/shop',
          name: '用户购买',
          desc: '进行用户购买',
          src: 'static/images/shop.jpg'
        },
        {
          routeName: '/mark',
          name: '用户打分',
          desc: '进行用户打分',
          src: 'static/images/mark.jpg'
        },
        {
          routeName: '/check_batch',
          name: '批次审核',
          desc: '进行批次审核',
          src: 'static/images/check.jpg'
        }

      ],
      name: '',
      token: '',
    }
  },
  created () {
    this.name = window.localStorage.getItem('name')
    this.token = window.localStorage.getItem('token')
  },
  mounted () {
  },
  methods: {
    handle (routeName) {
      this.$router.push({ path: routeName })
    }
  }
}
</script>


<style>

  .image {
    width: 100%;
    display: block;
  }

  .el-header {
    background-color: #abb6c7;
    color: #333;
    /* text-align: center; */
    line-height: 60px;
  }
  .el-main {
    background-color: #fafcff;
    color: #333;
    /* text-align: center; */
    line-height: 60px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }

  .title {
    font-size: 27px;
    margin: 0px auto 40px auto;
    margin-left: 60px;
    font-weight: bold;
  }

</style>
