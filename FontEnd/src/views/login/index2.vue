<template>
<div>
  <div class="login-container">
      <vue-particles
      color="#39AFFD"
      :particleOpacity="0.7"
      :particlesNumber="150"
      shapeType="circle"
      :particleSize="4"
      linesColor="#8DD1FE"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="2"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="false"
      clickMode="push"
      class="lizi"
    />
    <div class="content">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
        label-position="left">

        <div class="title-container">
          <h3 class="title">欢迎使用智慧农场管理平台</h3>
        </div>

        <el-form-item prop="uid">
          <span class="svg-container">
            <svg-icon icon-class="user" />
          </span>
          <el-input ref="uid" v-model="loginForm.uid" placeholder="U_id" name="name" type="text" tabindex="1"
            auto-complete="on" />
        </el-form-item>

        <el-form-item prop="pwd">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input :key="passwordType" ref="pwd" v-model="loginForm.pwd" :type="passwordType"
            placeholder="Password" name="password" tabindex="2" auto-complete="on" @keyup.enter.native="handleLogin" />
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>

        <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:10px;"
          @click.native.prevent="handleLogin">登录</el-button>
        <el-button type="text" @click="dialogFormVisible = true" style="color:#ffffff">注册</el-button>
        <div class="ant-divider ant-divider-vertical" role="separator"></div>
        <el-link href="http://localhost:8081/#/forgetpassword" target="_blank" style="color:#ffffff">忘记密码</el-link>

        <!-- <div class="title-container" style="padding-top: 20px;">
          <h3 class="title" style="margin-bottom: 5px;">快速登录</h3>
        </div> 
        <el-form-item>
          <el-select @change="quickAccess" style="width: 100%;">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item">
            </el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
    </div>
  </div>
  <div style="line-height: 60px">
    <el-dialog title="注册用户" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="dialogForm">
        <el-form-item label="U_id" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.uid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.pwd" type="password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" style="margin-left: 30px">
          <el-radio v-model="dialogForm.gender" label="1">男</el-radio>
          <el-radio v-model="dialogForm.gender" label="0">女</el-radio>
        </el-form-item>
        <el-form-item label="年龄" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.tel" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddData()">确 定</el-button>
      </div>
    </el-dialog>
    </div>
</div>
</template>

<script>
import {Message} from 'element-ui'
import axios from 'axios'
export default {
  name: 'LoginPage',
  data () {
    return {
      loginForm: {
        uid: '',
        pwd: ''
      },
      loginRules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      },
      loading: false,
      dialogFormVisible: false,
      passwordType: 'password',
      // options: [
      //   {'label': '用户'},
      //   {'label': '管理员'}
      // ],
      formLabelWidth: '70px',
      dialogForm: {
          uid: '',
          name: '',
          gender: '',
          pwd: '',
          age: '',
          tel: ''
      },
    }
  },
    created() {
    },
  methods: {
    // showPwd() {
    //   this.passwordType = this.passwordType === 'password' ? 'text' : 'password';
    // },
    showPwd () {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    AddData() {
      this.dialogFormVisible = false;
      const url = this.$store.state.settings.baseurl + '/user';
      axios.post(url, {
        "uid": this.dialogForm.uid,
        "name": this.dialogForm.name,
        "pwd": this.dialogForm.pwd,
        "gender": this.dialogForm.gender,
        "age": this.dialogForm.age,
        "tel": this.dialogForm.tel,
        "idtype": 0
      },
      {
        headers: {
          'Authorization': this.token
        }
      })
      .then(() => {
        this.$message({
          type: 'success',
          message: '注册成功!'
        });
      })
      .catch(error => {
          console.log(error)
      });
    },
    beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt5M = file.size / 1024 / 1024 < 5;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt5M) {
          this.$message.error('上传头像图片大小不能超过 5MB!');
        }
        return isJPG && isLt5M;
    },
    changeImage(file) {
      this.dialogForm.image = 'static/images/' + file.name
    },
    // quickAccess (e) {
    //   // console.log(e)
    //   Message.success(`快速登录角色${e.label}成功(${e.value})`)
    //   window.localStorage.setItem('name', e.label)
    //   // console.log(this.$store)
    //   this.$router.push({ path: '/index' })
    // },
    handleLogin () {
      // this.$router.push({ path: '/index' })
      console.log(this.$store.state.settings.baseurl)
      // 表单验证
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 开始请求
          this.loading = true

          const url = this.$store.state.settings.baseurl + '/login'
          axios.post(url, this.loginForm)
            .then(res => {
              let data = res.data
              if (data.code === 1) {
                window.localStorage.setItem('uid', this.loginForm.uid)
                window.localStorage.setItem('token', data.data)
                // console.log(this.$store)
                axios.get(url + "?uid=" + this.loginForm.uid) //获取用户权限等级
                  .then(response => {
                    Message.success('登录成功')
                    let type = response.data.data
                    window.localStorage.setItem('type', type)
                    this.$router.push({ path: '/index' })
                  }).catch(error => {
                    console.error(error)
                    Message.error('系统错误' + error)
                  }).finally(() => {
                    // 结束请求
                    this.loading = false
                  })
              } else {
                Message.error('登录失败，' + data.msg)
              }
            }).catch(error => {
              console.error(error)
              Message.error('系统错误' + error)
            }).finally(() => {
              // 结束请求
              this.loading = false
            })
        }
      })
    },
    test(){
      // asdhkajsdhka
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #00aaff;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    position: relative;
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    position: relative;
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }

}
</style>

<style lang="scss" scoped>
$bg: #0072ab;
$dark_gray: #889aa4;
$light_gray: #ffffff;

.login-container {
  min-height: 100%;
  width: 100%;
  // background-color: $bg;
  // background-image: url('../../../static/images/loginbg.jpg');
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .quick_form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }       
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: relative;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .ant-divider {
    box-sizing: border-box;
    color: rgba(0,0,0,.65);
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    font-feature-settings: "tnum";
    background: #e8e8e8;
  }

  .ant-divider, .ant-divider-vertical {
    position: relative;
    top: -.06em;
    display: inline-block;
    width: 1px;
    height: .9em;
    margin: 0 8px;
    vertical-align: middle;
  }
  .el-select {
    width: 120px;
  }
  .avatar-uploader .el-upload {
    border: 10px dashed #cfcccc;
    border-radius: 6px;
    cursor: pointer;
    position: relative;

  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 178px;
    line-height: 145px;
    
    text-align: center;
  }
  .avatar {
    width: 147px;
    height: 146px;
    display: block;
  }
  
}
.lizi{
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    background: #3e3e3e
}
</style>
