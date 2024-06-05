<template>
  <div>
    加载中。。。
  </div>
</template>
<script>
export default{
  mounted () {
    if (!this.$route.params.uid) {
      this.$message.error('请先登录！')
    } else {
      window.localStorage.setItem('uid', this.$route.params.uid)
      window.localStorage.setItem('name', this.$route.params.name)
      window.localStorage.setItem('role', this.$route.params.role)
      this.$router.push('/welcome') // 你们系统的首页
    }
  }
}
</script>
