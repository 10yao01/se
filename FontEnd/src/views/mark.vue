<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入订单编号" v-model="searchOid" />
          <el-input style="width: 20%;" placeholder="请输入商品名称" v-model="searchGname" />
          <el-button size="media" @click="search(searchOid, searchGname)" icon="el-icon-search">搜索</el-button>
         
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="oid" label="订单编号" sortable>
          </el-table-column>
          <el-table-column prop="customerid" label="用户账号" sortable>
            </el-table-column>
          <el-table-column prop="gid" label="商品编号" sortable>
            </el-table-column>
          <el-table-column prop="gname" label="商品名称" sortable>
            </el-table-column>
          <el-table-column prop="amount" label="购买数量" sortable>
          </el-table-column>
          <el-table-column prop="price" label="购买价格" sortable>
          </el-table-column>
          <el-table-column prop="score" label="评分" sortable width="100px" align="center">
            <template slot-scope="scope">
                <el-tag :type="stateType[scope.row.score]" disable-transitions>{{ scope.row.score==0? '未打分':scope.row.score }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="info" @click="Mark(scope.row)">
                打分
              </el-button>
              <el-dialog title="商品评分" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="dialogForm">
                  <el-form-item label="分数" style="margin-left: 30px">
                    <el-radio v-model="dialogForm.score" label="1">1分</el-radio>
                    <el-radio v-model="dialogForm.score" label="2">2分</el-radio>
                    <el-radio v-model="dialogForm.score" label="3">3分</el-radio>
                    <el-radio v-model="dialogForm.score" label="4">4分</el-radio>
                    <el-radio v-model="dialogForm.score" label="5">5分</el-radio>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">取 消</el-button>
                  <el-button type="primary" @click="Update()">确 定</el-button>
                </div>
              </el-dialog>
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
      searchOid: '',
      searchGname: '',
      descriptionData: '',
      dialogFormVisible: false,
      dialogForm: {
          oid: '',
          score: ''
      },
      formLabelWidth: '70px',
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      stateType: ['danger', 'danger', 'danger', 'danger','warning','success'],
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
    Mark(row) {
      if(row.customerid != this.uid){
        
        Message.error("抱歉，您没有此权限！")
        return
      }
      if(row.score != 0) {
        Message.error("请勿重复打分！")
      }else{
        this.dialogFormVisible = true
        this.dialogForm.oid = row.oid
      }
    },
    Update() {
      this.dialogFormVisible = false
      let url = this.$store.state.settings.baseurl + '/order'
      axios.put(url, {
        "oid": this.dialogForm.oid,
        "score": this.dialogForm.score
      },{
        headers: {
          'Authorization': this.token
        }
      })
      .then(() => {
        this.fetchData();
        this.$message({
            type: 'success',
            message: '打分成功!'
        })
      })  
      .catch(error => {
          console.log(error)
      })
    },
    search(searchOid, searchGName) {
      let url = this.$store.state.settings.baseurl + '/order'
      if(this.type == 0 || this.type == 1){
        url = url + '?customerid=' + this.uid
      }
      axios.get(url, {
          headers: {
          'Authorization': this.token
          },
          params:{
              oid: searchOid,
              gname: searchGName
            }
      })
      .then(response => {
        let Ddata = response.data.data 
        this.formData = Ddata
      })
      .catch(error => {
        console.log(error)
      })
    },
    fetchData () {
      let url = this.$store.state.settings.baseurl + '/order'
      if(this.type == 0 || this.type == 1){
        url = url + '?customerid=' + this.uid
      }
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          let Ddata = response.data.data
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
