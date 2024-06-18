<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <h5>当前用户：  <el-tag size="medium" type="info">{{ this.uid }}</el-tag>&nbsp;&nbsp;&nbsp;<el-tag size="medium">{{ this.typeClass[this.type] }}</el-tag>&nbsp;&nbsp;&nbsp;<el-button type="danger" size="mini" @click="$router.push('/login')" plain>切换账户</el-button></h5>
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
          routeName: '/feed',
          name: '喂食',
          desc: '进行喂食登记',
          src: 'static/images/weishi.jpg'
        },
        {
          routeName: '/poultry',
          name: '家禽信息',
          desc: '进行家禽管理',
          src: 'static/images/jiaqin.jpg'
        },
        {
          routeName: '/poultryland',
          name: '养殖场信息',
          desc: '进行养殖场管理',
          src: 'static/images/yangzhichang.jpg'
        }

      ],
      uid: '',
      token: '',
      type: '',
      typeClass: ['普通用户', '农场职工', '农场管理员', '系统管理员']
    }
  },
  created () {
    this.uid = window.localStorage.getItem('uid')
    this.token = window.localStorage.getItem('token')
    this.type = window.localStorage.getItem('type')
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
