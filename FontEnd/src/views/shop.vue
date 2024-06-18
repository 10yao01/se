<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入商品编号" v-model="searchGid" />
          <el-input style="width: 20%;" placeholder="请输入商品名称" v-model="searchGName" />
          <el-button size="media" @click="search(searchGid, searchGName)" icon="el-icon-search">搜索</el-button>
         
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="gid" label="商品编号" sortable>
          </el-table-column>
          <el-table-column prop="gname" label="商品名称" sortable>
            </el-table-column>
          <el-table-column prop="stock" label="余量" sortable>
            </el-table-column>
          <el-table-column prop="sale" label="销量" sortable>
            </el-table-column>
          <el-table-column prop="price" label="价格" sortable>
            </el-table-column>
          <el-table-column prop="score" label="商品评分" align="center" sortable>
            <template slot-scope="scope">
                <el-tag :type="stateType[Math.floor(scope.row.score)]" disable-transitions>{{ scope.row.score.toFixed(2)}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button size="mini" type="success" @click="handleInfo(scope.row)">
                购买
              </el-button>
              <el-dialog title="购买商品" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="dialogForm">
                  <el-form-item label="购买数量" style="margin-left: 30px">
                    <el-input v-model="dialogForm.amount" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">取 消</el-button>
                  <el-button type="primary" @click="Buy()">确 定</el-button>
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
      searchGid: '',
      searchGName: '',
      stock: '',
      dialogForm: {
          gid: '',
          gname: '',
          customerid: '',
          amount: ''
      },
      stateType: ['danger', 'danger', 'danger','warning','success'],
      dialogFormVisible: false,
      formLabelWidth: '70px',
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      uid: '',
      token: '',
      type: ''
    }
  },
  created() {
    this.fetchData()
    this.uid = window.localStorage.getItem('uid')
    this.token = window.localStorage.getItem('token')
    this.type = window.localStorage.getItem('type')
  },
  mounted () {
    if (this.$route.params.iid) {
      const url = this.$store.state.settings.baseurl + '/farmer/' + this.$route.params.iid
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
    Buy() {
      if(this.dialogForm.amount == ''){
        Message.error("购买数量不可为空！")
      }else if(this.dialogForm.amount > this.stock){
        Message.error("购买数量超过库存！")
      }else{
        let url = this.$store.state.settings.baseurl + '/order'
        axios.get(url,{
          headers: {
            'Authorization': this.token
          }
        })
          .then(response => {
            let Ddata = response.data.data
            let n = parseInt(Ddata.length)+1
            let oid = 'O' + n
            console.log(oid)
            axios.post(url, {
              "oid": oid,
              "customerid": this.dialogForm.customerid,
              "gid": this.dialogForm.gid,
              "gname": this.dialogForm.gname,
              "amount": this.dialogForm.amount,
            },
            {
              headers: {
                'Authorization': this.token
              }
            })
            .then(() => {
              this.fetchData()
              this.$message({
                type: 'success',
                message: '购买成功!'
              });
            })
            .catch(error => {
                console.log(error)
            })
          })
          .catch(error => {
            console.log(error)
          })
      }
    },
    search(searchGid, searchGName) {
      let url = this.$store.state.settings.baseurl + '/goods'
      axios.get(url,{
        headers: {
          'Authorization': this.token
        },
        params: {
          gid: searchGid,
          gname: searchGName
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
    fetchData () {
      let url = this.$store.state.settings.baseurl + '/goods'
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
    handleInfo (row) {
      this.dialogFormVisible = true;
      this.dialogForm.gname = row.gname
      this.dialogForm.gid = row.gid
      this.dialogForm.customerid = this.uid
      this.stock = row.stock
    }
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
