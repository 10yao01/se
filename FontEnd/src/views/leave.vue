<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入员工编号" v-model="searchEid" />
          <el-button size="media" @click="search(searchEid)" icon="el-icon-search">搜索</el-button>
            <el-button size="medium" type="primary" style="margin-left: 950px;" @click="dialogFormVisible=true">
                请假申请
            </el-button>
            <el-dialog title="请假申请" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="batchform">
                <el-form-item label="请假开始日期" :label-width="formLabelWidth">
                <el-date-picker v-model="batchform.startdate"
                    type="date"
                    placeholder="选择日期"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd">
                </el-date-picker>
                </el-form-item>
                <el-form-item label="请假结束时间" :label-width="formLabelWidth">
                <el-date-picker v-model="batchform.backdate"
                    type="date"
                    placeholder="选择时间"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd">
                </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="qingjiaUpdate()">确 定</el-button>
            </div>
            </el-dialog>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="eid" label="员工编号" sortable>
          </el-table-column>
          <el-table-column prop="startdate" label="请假开始日期" sortable>
            </el-table-column>
          <el-table-column prop="backdate" label="请假结束日期" sortable>
          </el-table-column>
          <el-table-column prop="isdealed" label="状态" sortable>
            <template slot-scope="scope">
                <el-tag :type="stateType[scope.row.isdealed]" disable-transitions>{{ state[scope.row.isdealed] }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" type="info" @click="Pass(scope.row)">
                通过
              </el-button>
              <el-button size="mini" type="danger" @click="NoPass(scope.row)">
                不通过
              </el-button>
            </template>
          </el-table-column>
          </el-table>
          <Pagination :total="filteredData.length" :currentPage="1" @changePage="changePage" :pageSize="pageSize">
          </Pagination>
        </el-col>
      </el-row>
    </el-main>

 </el-container>
</template>
<script>
import {Message} from 'element-ui'
import axios from 'axios'
import Pagination from '../components/Pagination'
export default {
  components: {
    Pagination
  },
  data () {
    return {
      formData: [],
      oneData: {},
      searchEid: '',
      descriptionData: '',
      batchform: {
        startdate: '',
        backdate: ''
      },
      dialogFormVisible: false,
      formLabelWidth: '110px',
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      stateType: ['danger','warning','success'],
      state: ['审批不通过', '未审批', '审批通过'],
      uid: '',
      token: '',
      type: ''
    }
  },
  created() {
    this.uid = window.localStorage.getItem('uid')
    this.token = window.localStorage.getItem('token')
    this.type = window.localStorage.getItem('type')
    this.fetchData()
  },
  computed: {
    pagedData () {
      return this.filteredData.slice(this.firstRecord - 1, this.lastRecord)
    },
    filteredData () {
      const searchTerm = ('').trim().toLowerCase()
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
      })
    }
  },
  methods: {
    qingjiaUpdate() {
        this.dialogFormVisible = false
        let url = this.$store.state.settings.baseurl + '/leave'
        axios.post(url, {
            "eid": this.uid,
            "startdate": this.batchform.startdate,
            "backdate": this.batchform.backdate,
        },
        {
            headers: {
            'Authorization': this.token
            }
        })
        .then(()=> {
            this.fetchData()
            this.$message({
                type: 'success',
                message: '请假申请成功!'
            })
        })
        .catch(error => {
            console.log(error)
        })
    },
    Pass(row) {
      if(this.type == 1 || this.type == 2){
        Message.error("抱歉，您没有此权限！")
        return 
      }
      let url = this.$store.state.settings.baseurl + '/leave'
      if(row.isdealed != 1){
        Message.error("请勿重复审核！")
      }else{
        axios.put(url,{
        "eid": row.eid,
        'startdate': row.startdate,
        "isdealed": 1
      },{
        headers: {
          'Authorization': this.token
        }
      })
      .then(() => {
        this.fetchData()
        this.$message({
            type: 'success',
            message: '审核成功!'
        })
      })
      }
    },
    NoPass(row) {
      if(this.type == 1 || this.type == 2){
        Message.error("抱歉，您没有此权限！")
        return 
      }
      let url = this.$store.state.settings.baseurl + '/leave'
      if(row.isdealed != 1){
        Message.error("请勿重复审核！")
      }else{
        axios.put(url,{
        "eid": row.eid,
        'startdate': row.startdate,
        "isdealed": -1
      },{
        headers: {
          'Authorization': this.token
        }
      })
      .then(() => {
        this.fetchData()
        this.$message({
            type: 'success',
            message: '审核成功!'
        })
      })
      }
    },
    search(searchEid) {
      if(this.type == 1 && this.type == 2){
        Message.error("抱歉，您没有此权限！")
        return
      }
      let url = this.$store.state.settings.baseurl + '/leave'
      axios.get(url, {
          headers: {
          'Authorization': this.token
          },
          params:{
              eid: searchEid
            }
      })
      .then(response => {
        let Ddata = response.data.data
        for(let i = 0;i<Ddata.length;i++){
            Ddata[i].isdealed = Ddata[i].isdealed+1
        }    
        this.formData = Ddata
      })
      .catch(error => {
        console.log(error)
      })
    },
    fetchData () {
      let url = this.$store.state.settings.baseurl + '/leave'
      if(this.type == 1 || this.type == 2){
        url = url + '?eid=' + this.uid
      }
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          let Ddata = response.data.data
          for(let i = 0;i<Ddata.length;i++) {
            Ddata[i].isdealed = Ddata[i].isdealed + 1
          }
          this.formData=Ddata
          this.firstRecord = 1
          this.lastRecord = this.pageSize
        })
        .catch(error => {
          console.log(error)
        })
    },
    changePage (first, last) {
      this.firstRecord = first
      this.lastRecord = last
    },
  }
}
</script>
<style scoped>
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
    margin-bottom: 60px;
  }
  .title {
    font-size: 27px;
    margin: 0px auto 40px auto;
    margin-left: 60px;
    font-weight: bold;
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
</style>
