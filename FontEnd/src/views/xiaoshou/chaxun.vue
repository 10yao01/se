<template>
  <div style="padding-top: 20px;">
    <div v-if="this.$route.params.orderid === undefined">
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 30%;" placeholder="输入关键字搜索" v-model="searchText" />
          <el-button-group style="margin-left: 15px;">
            <el-button size="small" @click="handleStatusFilter(0, 1, 2)">全部</el-button>
          </el-button-group>

          <!-- <el-button class='button_insert' type="primary" style="margin-left: 15px;">新增数据</el-button> -->
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="22" :offset="1">
          <el-table :data="formData" border height="250" style="width: 100%">
            <el-table-column prop="id" label="地区编号" sortable>
            </el-table-column>
            <el-table-column prop="name" label="地区名" sortable>
            </el-table-column>
            <el-table-column prop="extent" label="地区面积" sortable>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <!-- <el-row :gutter="20">
        <el-col :span="12" :offset="12">
          <Pagination :total="filteredData.length" :currentPage="1" @changePage="changePage" :pageSize="pageSize">
          </Pagination>
        </el-col>
      </el-row> -->
    </div>

  </div>
</template>
<script>
import axios from 'axios'
import Pagination from '../../components/Pagination'
export default {
  components: {
    Pagination
  },
  data () {
    return {
      formData: [],
      oneData: {},
      searchText: '',
      statusText: ['已完成', '进行中', '作废'],
      statusType: ['success', 'warning', 'danger'],
      pageSize: 2,
      firstRecord: 1,
      lastRecord: 999,
      statusFileter: [0, 1, 2]
    }
  },
  created(){
    //页面加载时就从本地通过localstorage获取存储的token值
    this.token =  localStorage.getItem('token')
  },
  mounted () {
    if (this.$route.params.orderid) {
      const url = this.$store.state.settings.baseurl + '/xiaoshou/' + this.$route.params.orderid
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.oneData = res.data.data
        }
      })
    } else {
      this.fetchData()
      this.lastRecord = this.pageSize
    }
  },
  computed: {
    pagedData () {
      return this.filteredData.slice(this.firstRecord - 1, this.lastRecord)
    },
    filteredData () {
      const searchTerm = (this.searchText || '').trim().toLowerCase()
      return this.formData.filter(row => {
        return Object.keys(row).some(key => {
          const value = row[key]
          if (value == null || value === '') { // 空或未定义等情况要同时判断
            return false
          } else {
            const strValue = String(value)
            try {
              return strValue.toLowerCase().includes(searchTerm)
            } catch (e) {
              console.warn(`Unable to convert ${key}: ${value} to string.`)
              return false
            }
          }
        })
      }).filter(row => {
        return this.statusFileter.includes(row.status)
      })
    }
  },
  methods: {
    onSubmit () {

    },
    fetchData () {
      axios.get(this.$store.state.settings.baseurl + '/area', {
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          this.formData = response.data.data
          this.firstRecord = 1
          this.lastRecord = this.pageSize
        })
        .catch(error => {
          console.log(error)
        })
    },
    commit (buyid, nextState) {
      axios.get(this.$store.state.settings.baseurl + `/xiaoshou/commit/${buyid}/${nextState}`)
        .then(response => {
          if (response.data.code === 200) {
            this.$message.success('操作成功')
            this.fetchData()
          } else {
            this.$message.error('操作失败')
            this.fetchData()
          }
        })
        .catch(error => {
          this.$message.error('网络异常' + error)
        })
    },
    handleApprove (buyid) {
      this.commit(buyid, 2)
    },
    handleDisapprove (buyid) {
      this.commit(buyid, 3)
    },
    changePage (first, last) {
      this.firstRecord = first
      this.lastRecord = last
    },
    handleInfo (buyid) {
      this.$router.push(`/xiaoshou/chaxun/${buyid}`)
    },
    handleStatusFilter (...args) {
      this.statusFileter = args
    },
    handleEdit (orderid) {
      this.$router.push(`/xiaoshou/luru/${orderid}`)
    }
  }
}
</script>
<style scoped></style>
