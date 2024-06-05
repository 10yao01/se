<template>
  <div style="padding-top: 20px;">
    <div v-if="this.$route.params.buyid === undefined">
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
            <el-table-column prop="buyid" label="项目编号" sortable>
            </el-table-column>
            <el-table-column prop="name" label="项目名称" sortable>
            </el-table-column>
            <el-table-column prop="gongnumber" label="项目类型" sortable>
            </el-table-column>
            <el-table-column prop="munumber" label="项目负责人" sortable>
            </el-table-column>
            <el-table-column prop="price" label="预算" sortable>
            </el-table-column>
            <el-table-column prop="createUserName" label="申请时间" sortable>
            </el-table-column>
            <el-table-column prop="status" label="状态" sortable>
              <template slot-scope="scope">
                <el-tag :type="statusType[scope.row.status]" disable-transitions>{{ statusText[scope.row.status]
                }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="handleInfo(scope.row.buyid)">详情</el-button>
                <el-button type="text" size="small" @click="handleEdit(scope.row.buyid)" :disabled="![1,3].includes(scope.row.status)">修改</el-button>
                <el-button type="text" size="small" @click="commit(scope.row.buyid, 4)">作废</el-button>
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
    <div v-else>
      <el-row>
        <el-col :span="22" :offset="1">
          <el-descriptions class="margin-top" title="查看详情" :column="1">
            <el-descriptions-item label="项目编号">{{oneData.buyid}}</el-descriptions-item>
            <el-descriptions-item label="项目名称">{{oneData.name}}</el-descriptions-item>
            <el-descriptions-item label="项目所属单位">{{oneData.gongnumber}}</el-descriptions-item>
            <el-descriptions-item label="项目具体描述">{{oneData.munumber}}</el-descriptions-item>
            <el-descriptions-item label="预算金额">{{oneData.price}}</el-descriptions-item>
            <el-descriptions-item label="已获批金额">{{oneData.price_paid}}</el-descriptions-item>
            <el-descriptions-item label="申请人">
              {{oneData.createUserName}}
            </el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag size="small" :type="statusType[oneData.status]">{{statusText[oneData.status]}}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
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
        {
          buyid: 1,
          name: '脑电微状态分析',
          gongnumber: '实验',
          munumber: '陈孜治',
          price: 30000,
          createUserName: '2023/8/15',
          status: 1
        }
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
    if (this.$route.params.buyid) {
      const url = this.$store.state.settings.baseurl + '/yinzhong/' + this.$route.params.buyid
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
      axios.get(this.$store.state.settings.baseurl + '/yinzhong')
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
      axios.get(this.$store.state.settings.baseurl + `/yinzhong/commit/${buyid}/${nextState}`)
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
      this.$router.push(`/yinzhong/chaxun/${buyid}`)
    },
    handleStatusFilter (...args) {
      this.statusFileter = args
    },
    handleEdit (buyid) {
      this.$router.push(`/yinzhong/luru/${buyid}`)
    }
  }
}
</script>
<style scoped></style>
