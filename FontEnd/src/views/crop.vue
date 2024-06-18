<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入农作物编号" v-model="searchID" />
          <el-input style="width: 20%;" placeholder="请输入农作物名称" v-model="searchName" />
          <el-button size="media" @click="search(searchID,searchName)" icon="el-icon-search">搜索</el-button>
         
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="cid" label="农作物编号" sortable>
          </el-table-column>
          <el-table-column prop="cname" label="农作物名称">
          </el-table-column>
          <el-table-column prop="cinfo" label="相关信息" sortable>
          </el-table-column>
          <el-table-column prop="wtime" label="所需灌溉间隔时间" sortable>
          </el-table-column>
          <el-table-column prop="ftime" label="所需施肥间隔时间" sortable>
          </el-table-column>
          <el-table-column label="操作" width="300px">
            <template slot-scope="scope">
              <el-button size="mini" type="info" @click="handleInfo(scope.row)">
                修改
              </el-button>
              <el-dialog title="修改农作物信息" :visible.sync="dialogFormVisible2" width="30%">
                <el-form :model="dialogForm2">
                  <el-form-item label="作物编号" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.cid" autocomplete="on"></el-input>
                  </el-form-item>
                  <el-form-item label="作物名称" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.cname" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="相关信息" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.cinfo" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="灌溉间隔时间" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.wtime" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="施肥间隔时间" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.ftime" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible2 = false">取 消</el-button>
                    <el-button type="primary" @click="Update()">确 定</el-button>
                </div>
              </el-dialog>
              <el-button size="mini" type="danger" @click="openDelete(scope.row)">
                删除
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
      searchID:'',
      searchName: '',
      dialogForm2: {
          cid:'',
          cname:'',
          cinfo: '',
          wtime: '',
          ftime: '',
      },
      dialogFormVisible: false,
      dialogFormVisible2: false,
      formLabelWidth: '100px',
      typeClass: ['普通用户', '农场职工', '农场管理员', '系统管理员'],
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      statusFileter: ['男', '女'],
      uid: '',
      token: '',
      type:''
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
    search(searchID, searchName) {
      if(this.name=='root'){       
        let url = this.$store.state.settings.baseurl + '/crop'
        axios.get(url, {
            headers: {
            'Authorization': this.token
            },
            params:{
              cid: searchID,
              cname: searchName,
            }
        })
        .then(response => {
          let Ddata = response.data.data
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].gender = Ddata[i].gender==1? '男':'女'
            Ddata[i].idtype = this.typeClass[Ddata[i].idtype]
          }
          this.formData = Ddata
        })
        .catch(error => {
          console.log(error)
        });
      }else{
        Message.error("没有此权限！")
      }
    },
    openDelete(row) {
        if(this.name=='root'){        
          this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRow(row);
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      }else{
        Message.error("没有此权限！")
      }
    },
    deleteRow (row) {
      let Cid = row.cid;
      const url = this.$store.state.settings.baseurl + '/crop/' + Cid;
      axios.delete(url, {
        headers: {
          'Authorization': this.token
        }
      })    
      .then(() => {
        this.fetchData();
      })      
      .catch(error => {
        console.log(error)
      })
    },
    fetchData () {
      let url = this.$store.state.settings.baseurl + '/crop'
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          let Ddata = response.data.data
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].idtype = this.typeClass[Ddata[i].idtype]
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
    handleInfo (row) {
      this.dialogFormVisible2 = true;
      this.dialogForm2.cid = row.cid
      this.dialogForm2.cname = row.cname
      this.dialogForm2.cinfo = row.cinfo
      this.dialogForm2.wtime = row.wtime
      this.dialogForm2.ftime = row.ftime

    },
    Update() {
      this.dialogFormVisible2 = false
      const url = this.$store.state.settings.baseurl + '/crop'
      axios.put(url,{
        "cid": this.dialogForm2.cid,
        "cname": this.dialogForm2.cname,
        "cinfo": this.dialogForm2.cinfo,
        "wtime": this.dialogForm2.wtime,
        "ftime": this.dialogForm2.ftime,
      },{
        headers: {
          'Authorization': this.token
        }
      })
      .then(() => {
        this.fetchData();
        this.$message({
            type: 'success',
            message: '修改成功!'
        })
      })  
      .catch(error => {
          console.log(error)
      });
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
