<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
            <el-input style="width: 20%;" placeholder="请输入职工编号" v-model="Eid" />
            <el-button size="media" @click="search(Eid)" icon="el-icon-search">搜索</el-button>
            <el-button size="medium" type="primary" style="margin-left: 950px;" @click="dialogFormVisible=true">
                打卡上班
            </el-button>
            <el-dialog title="上班时间" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="batchform">
                <el-form-item label="上班日期" :label-width="formLabelWidth">
                <el-date-picker v-model="batchform.date"
                    type="date"
                    placeholder="选择日期"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd">
                </el-date-picker>
                </el-form-item>
                <el-form-item label="上班时间" :label-width="formLabelWidth">
                <el-time-picker v-model="batchform.time"
                    type="time"
                    placeholder="选择时间"
                    format="HH:mm:SS"
                    value-format="HH:mm:SS">
                </el-time-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="shangbanUpdate()">确 定</el-button>
            </div>
            </el-dialog>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="eid" label="员工编号" sortable>
          </el-table-column>
            <el-table-column prop="date" label="打卡日期">
            </el-table-column>
            <el-table-column prop="come" label="上班时间" sortable>
            </el-table-column>
            <el-table-column prop="leave" label="下班时间" sortable>
            </el-table-column>
            <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button size="medium" type="warning" @click="dialogFormVisible2=true">
                    打卡下班
                </el-button>
                <el-dialog title="下班时间" :visible.sync="dialogFormVisible2" width="30%">
                <el-form :model="batchform">
                    <el-form-item label="下班时间" :label-width="formLabelWidth">
                    <el-time-picker v-model="batchform.time"
                        type="time"
                        placeholder="选择时间"
                        format="HH:mm:SS"
                        value-format="HH:mm:SS">
                    </el-time-picker>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible2 = false">取 消</el-button>
                <el-button type="primary" @click="xiabanUpdate(scope.row)">确 定</el-button>
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
      Eid:'',
      batchform: {
        date: '',
        time: ''
      },
      dialogFormVisible: false,
      dialogFormVisible2: false,
      formLabelWidth: '100px',
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      uid: '',
      token: '',
      type:''
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
    shangbanUpdate() {
        this.dialogFormVisible = false
        let url = this.$store.state.settings.baseurl + '/clock'
        axios.post(url, {
            "eid": this.uid,
            "date": this.batchform.date,
            "come": this.batchform.time,
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
                message: '上班打卡成功!'
            })
        })
        .catch(error => {
            console.log(error)
        })
    },
    xiabanUpdate(row) {
        this.dialogFormVisible2 = false
        if(row.leave != null){
            Message.error("您已下班，请勿重复打卡！")
            return 
        }
        let url = this.$store.state.settings.baseurl + '/clock'
        axios.put(url, {
            "eid": row.eid,
            "date": row.date,
            "leave": this.batchform.time,
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
                message: '下班打卡成功!'
            })
        })
        .catch(error => {
            console.log(error)
        })
    },
    search(Eid) {     
      if(this.type != 3){
        Message.error("抱歉，您没有此权限！")
        return 
      }
      let url = this.$store.state.settings.baseurl + '/clock'
      axios.get(url, {
          headers: {
          'Authorization': this.token
          },
          params:{
            eid: Eid,
          }
      })
      .then(response => {
        let Ddata = response.data.data
        this.formData = Ddata
      })
      .catch(error => {
        console.log(error)
      });
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
      let Fid = row.fid;
      const url = this.$store.state.settings.baseurl + '/farm/' + Fid;
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
      let url = this.$store.state.settings.baseurl + '/clock'
      if(this.type == 1){
        url = url + '?eid=' + this.uid
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
    handleInfo (row) {
      if(this.type == 1){
        Message.error("抱歉，您没有此权限！")
        return
      }
      this.dialogFormVisible2 = true;
      this.dialogForm2.fid = row.fid
      this.dialogForm2.fname = row.fname
      this.dialogForm2.cid = row.cid
      this.dialogForm2.cname = row.cname
      this.dialogForm2.camount = row.camount
      this.dialogForm2.pdate = row.pdate
      this.dialogForm2.wid =row.wid
      this.dialogForm2.harvestdate = row.harvestdate
      this.dialogForm2.stage = row.stage
      this.dialogForm2.posinfo = row.posinfo
    },
    Update() {
      this.dialogFormVisible2 = false
      const url = this.$store.state.settings.baseurl + '/farm'
      axios.put(url,{
        "fid": this.dialogForm2.fid,
        "fname": this.dialogForm2.fname,
        "cid": this.dialogForm2.cid,
        "cname": this.dialogForm2.cname,
        "camount": this.dialogForm2.camount,
        "pdate": this.dialogForm2.pdate,
        "wid": this.dialogForm2.wid,
        "harvestdate": this.dialogForm2.harvestdate,
        "stage": this.dialogForm2.stage,
        "posinfo": this.dialogForm2.posinfo
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
