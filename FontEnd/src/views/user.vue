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
          <el-select v-model="searchGender" clearable placeholder="请选择性别" style="width: 10%">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
          <el-input style="width: 15%;" placeholder="请输入用户电话" v-model="searchTel" />
          <el-button size="media" @click="search(searchID, searchName, searchGender, searchTel)" icon="el-icon-search">搜索</el-button>
          <el-button type="primary" style="margin-left: 420px;" @click="dialogFormVisible = true">+ 新增人员</el-button>
          <el-dialog title="新增用户" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="dialogForm">
              <el-form-item label="U-id" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.uid" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="密码" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.pwd" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="姓名" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="性别" style="margin-left: 30px">
                <el-radio v-model="dialogForm.gender" label="1">男</el-radio>
                <el-radio v-model="dialogForm.gender" label="0">女</el-radio>
              </el-form-item>
              <el-form-item label="年龄" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.age" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="电话" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.tel" autocomplete="off"></el-input>
              </el-form-item>
              <div>
                <span style="font-weight: bold">用户类型&nbsp;&nbsp;</span>
                <el-select v-model="dialogForm.idtype" placeholder="请选择" filterable>
                  <el-option
                    v-for="item in typeClass"
                    :key="item.index"
                    :label="item"
                    :value="typeClass.indexOf(item)">
                    <span style="float: left">{{ item }}</span>
                    <!-- <span style="float: right; color: #8492a6; font-size: 13px">{{ item.id }}</span> -->
                  </el-option>
                </el-select>
              </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="AddData()">确 定</el-button>
            </div>
          </el-dialog>
        </el-col>
      </el-row>

      <el-row>
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
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" type="info" @click="handleInfo(scope.row)">
                修改
              </el-button>
              <el-dialog title="修改用户信息" :visible.sync="dialogFormVisible2" width="30%">
                <el-form :model="dialogForm2">
                  <el-form-item label="姓名" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.name" autocomplete="on"></el-input>
                  </el-form-item>
                  <el-form-item label="密码" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.pwd" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="年龄" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.age" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="电话" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.tel" autocomplete="off"></el-input>
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
      userData: [],
      oneData: {},
      searchID:'',
      searchName: '',
      searchGender: '',
      searchTel: '',
      descriptionData: '',
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
      dialogVisible: false,
      dialogFormVisible: false,
      dialogFormVisible2: false,
      formLabelWidth: '70px',
      typeClass: ['普通用户', '农场职工', '农场管理员', '系统管理员'],
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      statusFileter: ['男', '女'],
      name: '',
      token: '',
      type:''
    }
  },
  created() {
    this.fetchData()
    this.name = window.localStorage.getItem('name')
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
      }).filter(row => {
        return this.statusFileter.includes(row.gender)
      })
    }
  },
  methods: {
    AddData() {
      if(this.name=='root'){ 
        this.dialogFormVisible = false;
        const url = this.$store.state.settings.baseurl + '/user';
        axios.post(url, {
          "uid": this.dialogForm.uid,
          "pwd": this.dialogForm.pwd,
          "name": this.dialogForm.name,
          "gender": this.dialogForm.gender,
          "age": this.dialogForm.age,
          "tel": this.dialogForm.tel,
          "idtype": this.dialogForm.idtype
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
        let url = this.$store.state.settings.baseurl + '/user'
        axios.get(url, {
            headers: {
            'Authorization': this.token
            },
            params:{
              uid: searchID,
              name: searchName,
              gender: searchGender,
              tel: searchTel
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
      let url = this.$store.state.settings.baseurl + '/user'
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          let Ddata = response.data.data
          this.userData = Ddata
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].gender = Ddata[i].gender==1? '男':'女'
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
      this.dialogForm2.uid = row.uid
      this.dialogForm2.pwd = row.pwd
      this.dialogForm2.name = row.name
      this.dialogForm2.gender = row.gender
      this.dialogForm2.age = row.age
      this.dialogForm2.idtype = row.idtype
      this.dialogForm2.tel = row.tel
    },
    Update() {
      this.dialogFormVisible2 = false
      const url = this.$store.state.settings.baseurl + '/user'
      axios.put(url,{
        "uid": this.dialogForm2.uid,
        "name": this.dialogForm2.name,
        "pwd": this.dialogForm2.pwd,
        "age": this.dialogForm2.age,
        "tel": this.dialogForm2.tel
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
