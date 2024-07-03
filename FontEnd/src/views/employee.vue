<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 15%;" placeholder="请输入用户ID" v-model="searchID" />
          <el-input style="width: 15%;" placeholder="请输入用户姓名" v-model="searchName" />
          <el-input style="width: 15%;" placeholder="请输入用户电话" v-model="searchTel" />
          <el-button size="media" @click="search(searchID, searchName, searchGender, searchTel)" icon="el-icon-search">搜索</el-button>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="eid" label="员工编号" sortable>
          </el-table-column>
          <el-table-column prop="ename" label="姓名" sortable>
          </el-table-column>
          <el-table-column prop="tel" label="电话" sortable>
          </el-table-column>
          <el-table-column prop="perform" label="本月绩效" sortable>
          </el-table-column>
          <el-table-column prop="salary" label="本月应发工资" sortable>
          </el-table-column>
          <el-table-column prop="isgot" label="是否发放工资" sortable>
            <template slot-scope="scope">
                <el-tag :type="stateType[scope.row.isgot]" disable-transitions>{{ scope.row.isgot==0? '未发放':'已发放' }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="300px">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="Update(scope.row)">
                发工资
              </el-button>
              <el-button size="mini" type="info" @click="jixiao(scope.row)">
                查看绩效
              </el-button>
              <el-dialog title="绩效详情" :visible.sync="dialogFormVisible" width="50%">
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
      searchGender: '',
      searchTel: '',
      dialogForm: {
          uid:'',
          pwd:'',
          name: '',
          age: '',
          gender:'',
          idtype: '',
          tel: ''
      },
      dialogForm2: {
          uid:'',
          pwd:'',
          name: '',
          age: '',
          gender:'',
          idtype: '',
          tel: ''
      },
      dialogFormVisible: false,
      formLabelWidth: '70px',
      typeClass: ['普通用户', '农场职工', '农场管理员', '系统管理员'],
      salarytype: ['未发放', '已发放'],
      stateType: ['danger', 'success'],
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
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
    jixiao(row) {
        let url = this.$store.state.settings.baseurl + '/employee'
        axios.get(url,{
            headers: {
                'Authorization': this.token
            },
            params: {
                eid: row.eid
            }
        })
        .then(response => {
            let data = response.data.data[0].performance
            let date = new Array(data.length) // 日期 x轴
            let performance = new Array(data.length) // 绩效 y轴
            for(let i = 0;i<data.length;i++){
                date[i] = data[i].date
            }
            for(let i = 0;i<data.length;i++){
                performance[i] = data[i].performance
            }
            // console.log(date)
            // console.log(performance)
            this.dialogFormVisible = true
        })
        .catch(error => {
            console.log(error)
        })
    },
    search(searchID, searchName, searchTel) {
      if(this.type == 3){       
        let url = this.$store.state.settings.baseurl + '/employee'
        axios.get(url, {
            headers: {
            'Authorization': this.token
            },
            params:{
              eid: searchID,
              ename: searchName,
              tel: searchTel
            }
        })
        .then(response => {
          let Ddata = response.data.data
          let perform = new Array(Ddata.length)
          for(let i = 0;i<Ddata.length;i++){
            let n = Ddata[i].performance.length-1
            perform[i] = Ddata[i].performance[n].performance
          }
          Ddata = Ddata.map(obj => {
              obj.perform = '1';
              return obj;
          });
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].perform = perform[i]
          }
          this.formData = Ddata
        })
        .catch(error => {
          console.log(error)
        });
      }else{
        Message.error("抱歉，您没有此权限！")
      }
    },
    openDelete(row) {
        if(this.type == 3){        
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
        Message.error("抱歉，您没有此权限！")
      }
    },
    deleteRow (row) {
      let Uid = row.uid;
      const url = this.$store.state.settings.baseurl + '/user/' + Uid;
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
      let url = this.$store.state.settings.baseurl + '/employee'
      if (this.type != 3){
        url = url + '?eid=' + this.uid
      }
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          let Ddata = response.data.data
          let perform = new Array(Ddata.length)
          for(let i = 0;i<Ddata.length;i++){
            let n = Ddata[i].performance.length-1
            perform[i] = Ddata[i].performance[n].performance
          }
          Ddata = Ddata.map(obj => {
              obj.perform = '1';
              return obj;
          });
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].perform = perform[i]
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
      this.dialogForm2.uid = row.uid
      this.dialogForm2.pwd = row.pwd
      this.dialogForm2.name = row.name
      this.dialogForm2.gender = row.gender
      this.dialogForm2.age = row.age
      this.dialogForm2.idtype = row.idtype
      this.dialogForm2.tel = row.tel
    },
    Update(row) {
        if(this.type != 3) {
            Message.error("抱歉，您没有此权限！")
            return
        }
        if(row.isgot == 1){
            Message.error("请勿重复发工资！")
            return
        }
        const url = this.$store.state.settings.baseurl + '/employee'
        axios.put(url,{
            "eid": row.eid,
            "isgot": 1
        },{
        headers: {
            'Authorization': this.token
        }
        })
        .then(() => {
        this.fetchData();
        this.$message({
            type: 'success',
            message: '发工资了!'
        })
        })  
        .catch(error => {
            console.log(error)
        })
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
