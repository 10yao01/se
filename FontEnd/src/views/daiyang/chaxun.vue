<template>
  <div style="padding-top: 20px;">
    <div v-if="this.$route.params.dyid === undefined">
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 30%;" placeholder="输入关键字搜索" v-model="searchText" />
          <el-button-group style="margin-left: 15px;">
            <el-button size="small" @click="handleStatusFilter(0, 1, 2, 3, 4)">全部</el-button>
            <el-button size="small" @click="handleStatusFilter(0)">已完成</el-button>
            <el-button size="small" @click="handleStatusFilter(1)">待审批</el-button>
            <el-button size="small" @click="handleStatusFilter(2)">待交付</el-button>
            <el-button size="small" @click="handleStatusFilter(3)">驳回</el-button>
            <el-button size="small" @click="handleStatusFilter(4)">作废</el-button>
          </el-button-group>

          <!-- <el-button class='button_insert' type="primary" style="margin-left: 15px;">新增数据</el-button> -->
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" style="width: 100%">
            <el-table-column prop="dyid" label="项目编号" sortable>
            </el-table-column>
            <el-table-column prop="name" label="项目名称" sortable>
            </el-table-column>
            <el-table-column prop="number" label="所属单位" sortable>
            </el-table-column>
            <el-table-column prop="price" label="预算金额" sortable>
            </el-table-column>
            <el-table-column prop="price_now" label="已批金额" sortable>
            </el-table-column>
            <el-table-column prop="jindu" label="支出进度" sortable>
            </el-table-column>
            <el-table-column prop="status" label="状态" sortable>
              <template slot-scope="scope">
                <el-tag :type="statusType[scope.row.status]" disable-transitions>{{ statusText[scope.row.status]
                }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12" :offset="12">
          <Pagination :total="filteredData.length" :currentPage="1" @changePage="changePage" :pageSize="pageSize">
          </Pagination>
        </el-col>
      </el-row>
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
      formData: [
      ],
      oneData: {},
      searchText: '',
      statusText: ['已完成', '待审批', '待交付', '驳回', '作废'],
      statusType: ['success', 'warning', 'info', 'danger', 'danger'],
      pageSize: 2,
      firstRecord: 1,
      lastRecord: 999,
      statusFileter: [0, 1, 2, 3, 4]
    }
  },
  mounted () {
    if (this.$route.params.dyid) {
      const url = this.$store.state.settings.baseurl + '/daiyang/' + this.$route.params.dyid
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
      axios.get(this.$store.state.settings.baseurl + '/daiyang')
        .then(response => {
          this.formData = response.data.data
          this.firstRecord = 1
          this.lastRecord = this.pageSize
        })
        .catch(error => {
          console.log(error)
        })
    },
    commit (dyid, nextState) {
      axios.get(this.$store.state.settings.baseurl + `/daiyang/commit/${dyid}/${nextState}`)
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
    handleApprove (dyid) {
      this.commit(dyid, 2)
    },
    handleDisapprove (dyid) {
      this.commit(dyid, 3)
    },
    changePage (first, last) {
      this.firstRecord = first
      this.lastRecord = last
    },
    handleInfo (dyid) {
      this.$router.push(`/daiyang/chaxun/${dyid}`)
    },
    handleStatusFilter (...args) {
      this.statusFileter = args
    },
    handleEdit (dyid) {
      this.$router.push(`/daiyang/luru/${dyid}`)
    }
  }
}
</script>
<style scoped></style>
