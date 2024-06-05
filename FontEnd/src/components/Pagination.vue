<template lang="">
    <div style="text-align: center;margin: 20px;">
        <el-pagination
            background
             layout="total,prev, pager, next,jumper"
            :total="total" :pageSize="pageSize" :current-page.sync="currentPageData" @current-change="changePage">
        </el-pagination>
    </div>
</template>
<script>
export default {
  props: {
    total: {
      type: Number,
      default: 100
    },
    pageSize: {
      type: Number,
      default: 10
    },
    currentPage: {
      type: Number,
      default: 1
    }
  },
  data () {
    return {
      currentPageData: 1
    }
  },
  mounted () {
    this.currentPageData = this.currentPage
  },
  methods: {
    changePage (page) {
      this.$emit('changePage', (page - 1) * this.pageSize + 1, page * this.pageSize)
    }
  }
}
</script>
<style lang="">

</style>
