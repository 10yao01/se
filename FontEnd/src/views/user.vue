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
          <el-input style="width: 15%;" placeholder="请输入用户性别" v-model="searchGender" />
          <el-input style="width: 15%;" placeholder="请输入用户电话" v-model="searchTel" />
          <el-button size="media" @click="search(searchID, searchName, searchGender, searchTel)" icon="el-icon-search">搜索</el-button>
         
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="uid" label="用户编号" sortable>
          </el-table-column>
          <el-table-column prop="pwd" label="密码" sortable>
          </el-table-column>
          <el-table-column prop="name" label="姓名">
          </el-table-column>
          <el-table-column prop="gender" label="性别" sortable>
          </el-table-column>
          <el-table-column prop="idtype" label="用户类别" sortable>
          </el-table-column>
          <el-table-column prop="age" label="年龄" sortable>
          </el-table-column>
          <el-table-column prop="tel" label="电话" sortable>
            <!-- <template slot-scope="scope">
              <el-tag :type="statusType[scope.row.status]" disable-transitions>{{ statusText[scope.row.status]
              }}：{{scope.row.score}}</el-tag>
            </template> -->
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
      areaData: [],
      oneData: {},
      searchID:'',
      searchName: '',
      searchGender: '',
      searchTel: '',
      descriptionData: '',
      dialogForm: {
          name: '',
          gender: '',
          age: '',
          image: '',
          areaname: ''
      },
      dialogForm2: {
          name: '',
          gender: '',
          age: '',
          image: '',
          areaname: ''
      },
      statusType: ['success','warning','danger'],
      statusText: ['优','良','差'],
      dialogVisible: false,
      dialogFormVisible: false,
      dialogFormVisible2: false,
      formLabelWidth: '70px',
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      statusFileter: ['男', '女'],
      name: '',
      token: '',
    }
  },
  created() {
    axios.get(this.$store.state.settings.baseurl + '/area',{
        headers: {
          'Authorization': this.token
        }
    })
        .then(response => {
          this.areaData = response.data.data
        })
        .catch(error => {
          console.log(error)
        })
    this.name = window.localStorage.getItem('name')
    this.token = window.localStorage.getItem('token')
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
      }).filter(row => {
        return this.statusFileter.includes(row.gender)
      })
    }
  },
  methods: {
    AddData() {
      if(this.name=='root'){ 
        this.dialogFormVisible = false;
        const url = this.$store.state.settings.baseurl + '/farmer';
        axios.post(url, {
          "fid": this.formData[this.formData.length-1].fid+1,
          "name": this.dialogForm.name,
          "gender": this.dialogForm.gender,
          "age": this.dialogForm.age,
          "areaname": this.dialogForm.areaname,
          "image": this.dialogForm.image
        },
        {
          headers: {
            'Authorization': this.token
          }
        })
        .then(() => {
          this.fetchData();
          this.$message({
            type: 'success',
            message: '新增成功!'
          });
        })
        .catch(error => {
            console.log(error)
        });
      }else{
        Message.error("没有此权限！")
      }
    },
    search(searchID, searchName, searchGender, searchTel) {
      if(this.name=='root'){       
        let url = this.$store.state.settings.baseurl + '/farmer?'
        if(searchID != ''){
          url = url + 'uid=' + searchID
          if(searchName != ''){
            url = url + '&name=' + searchName
          }
          if(searchGender != '') {
                url = url + '&gender=' + searchGender
            }
            if(searchArea != '') {
                url = url + '&tel=' + searchTel
            }
        }else if(searchName != '') {
            url = url + 'name=' + searchName
            if(searchGender != '') {
                url = url + '&gender=' + searchGender
            }
            if(searchArea != '') {
                url = url + '&tel=' + searchTel
            }
        }else if(searchGender != '') {
            url = url + 'gender=' + searchGender
            if(searchArea != '') {
                url = url + '&tel=' + searchTel
            }
        }else if(searchArea != '') {
            url = url + 'tel=' + searchTel
        }
        axios.get(url, {
            headers: {
            'Authorization': this.token
            }
        })
        .then(response => {
          let Ddata = response.data.data
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].gender = Ddata[i].gender==1? '男':'女'
            Ddata[i].status = ''
            if(Ddata[i].score >= 90){
              Ddata[i].status = 0
            }else if(Ddata[i].score >= 70){
              Ddata[i].status = 1
            }else{
              Ddata[i].status = 2
            }
          }
          this.formData=Ddata

        })
        .catch(error => {
          console.log(error)
        });
      }else{
        Message.error("没有此权限！")
      }
    },
    openDelete(index, row, rows) {
        if(this.name=='root'){        
          this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRow(index, row, rows);
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
    deleteRow (index, row, rows) {
      let Fid = row.fid;
      const url = this.$store.state.settings.baseurl + '/farmer/' + Fid;0
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
      let url = this.$store.state.settings.baseurl + '/user'
      if(this.name!='root'){
        url = url + '?name=' +this.name
      }
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          let Ddata = response.data.data
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].gender = Ddata[i].gender==1? '男':'女'
            Ddata[i].status = ''
            if(Ddata[i].score >= 90){
              Ddata[i].status = 0
            }else if(Ddata[i].score >= 70){
              Ddata[i].status = 1
            }else{
              Ddata[i].status = 2
            }
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
      this.dialogForm2.name = row.name
      this.dialogForm2.gender = row.gender
      this.dialogForm2.age = row.age
      this.dialogForm2.image = row.image
      this.dialogForm2.areaname = row.areaname
    },
    Update(fid) {
      this.dialogFormVisible2 = false
      const url = this.$store.state.settings.baseurl + '/farmer'
      axios.put(url,{
        "fid": fid,
        "name": this.dialogForm2.name,
        "gender": this.dialogForm2.gender=='男'? 1:2,
        "age": this.dialogForm2.age,
        "image": this.dialogForm2.image,
        "areaname": this.dialogForm2.areaname
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
    },
    beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt5M = file.size / 1024 / 1024 < 5;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt5M) {
          this.$message.error('上传头像图片大小不能超过 5MB!');
        }
        return isJPG && isLt5M;
    },
    changeImage(file) {
      this.dialogForm.image = 'static/images/' + file.name
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
