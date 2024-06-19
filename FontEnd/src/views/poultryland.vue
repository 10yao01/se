<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入养殖场编号" v-model="LandID" />
          <el-input style="width: 20%;" placeholder="请输入养殖场名称" v-model="LandName" />
          <el-input style="width: 20%;" placeholder="请输入养殖场的家禽名称" v-model="CropName" />
          <el-input style="width: 20%;" placeholder="请输入养殖场管理员号" v-model="Uid" />
          <el-button size="media" @click="search(LandID,LandName,CropName,Uid)" icon="el-icon-search">搜索</el-button>
         
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="pid" label="养殖场编号" sortable>
          </el-table-column>
          <el-table-column prop="pname" label="养殖场名称" width="105px">
            
            </el-table-column>
            <el-table-column prop="poultryid" label="家禽编号" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="poultryname" label="家禽名称" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="poultrynum" label="养殖数量" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="starttime" label="养殖时间" sortable>
            </el-table-column>
            <el-table-column prop="wid" label="管理员号" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="maturetime" label="预计成熟时间" sortable>
            </el-table-column>
            <el-table-column prop="stage" label="成长阶段" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="posinfo" label="养殖场位置备注" sortable>
            </el-table-column>
            <el-table-column label="操作" width="220px"> 
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="weishi(scope.row)">
                喂食
              </el-button>
              <el-dialog title="喂食时间" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="dialogForm">
                  <el-form-item label="喂食时间" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm.ftime" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="weishiUpdate()">确 定</el-button>
                </div>
              </el-dialog>
              <el-button size="mini" type="info" @click="handleInfo(scope.row)">
                修改
              </el-button>
              <el-dialog title="修改养殖场信息" :visible.sync="dialogFormVisible2" width="30%">
                <el-form :model="dialogForm2">
                  <el-form-item label="养殖场编号" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.pid" autocomplete="on"></el-input>
                  </el-form-item>
                  <el-form-item label="养殖场名称" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.pname" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="家禽编号" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.poultryid" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="家禽名称" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.poultryname" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="养殖数量" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.poultrynum" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="养殖时间" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.starttime" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="管理员号" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.wid" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="预计成熟时间" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.maturetime" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="成长阶段" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.stage" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="养殖场位置备注" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.posinfo" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible2 = false">取 消</el-button>
                    <el-button type="primary" @click="Update()">确 定</el-button>
                </div>
              </el-dialog>
              <el-button size="mini" type="warning" @click="shangchuan(scope.row)">
                上传
              </el-button>
              <el-dialog title="上传时间" :visible.sync="dialogFormVisible3" width="30%">
                <el-form :model="batchform">
                  <el-form-item label="上传时间" :label-width="formLabelWidth">
                    <el-input v-model="batchform.bdate" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible3 = false">取 消</el-button>
                    <el-button type="primary" @click="shangchuanUpdate()">确 定</el-button>
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
      searchID:'',
      searchName: '',
      searchGender: '',
      searchTel: '',
      LandID: '',
      LandName: '',
      CropName: '',
      Uid: '',
      batchform: {
        bdate: '',
        originid: '',
        gid: '',
        gname: '',
        amount: ''
      },
      dialogForm: {
        fid: '',
        pid: '',
        ftime: ''
      },
      dialogForm2: {
        pid: '',
        pname: '',
        poultryid: '',
        poultryname: '',
        poultrynum: '',
        starttime: '',
        wid: '',
        maturetime: '',
        stage: '',
        posinfo: '',
      },
      dialogFormVisible: false,
      dialogFormVisible2: false,
      dialogFormVisible3: false,
      formLabelWidth: '100px',
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
    shangchuan(row) {
      this.dialogFormVisible3 = true
      this.batchform.originid = row.pid
      this.batchform.gname = row.poultryname
      this.batchform.amount = row.poultrynum
      this.batchform.gid = row.poultryid
    },
    shangchuanUpdate() {
      this.dialogFormVisible3 = false
      let url = this.$store.state.settings.baseurl + '/batch'
      axios.get(url,{
          headers: {
            'Authorization': this.token
          }
        })
          .then(response => {
            let Ddata = response.data.data
            let n = parseInt(Ddata.length)+1
            let bid = 'B' + n
            axios.post(url, {
              "bid": bid,
              "originid": this.batchform.originid,
              "bdate": this.batchform.bdate,
              "gid": this.batchform.gid,
              "gname": this.batchform.gname,
              "amount": this.batchform.amount,
              "isdealed": 0
            },
            {
              headers: {
                'Authorization': this.token
              }
            })
            .then(() => {
              this.$message({
                type: 'success',
                message: '上传成功!'
              })
              let url2 = this.$store.state.settings.baseurl + '/pasture'
              axios.put(url2, {
                "pid": this.batchform.originid,
                "poultrynum": 0
              },
              {
                headers: {
                'Authorization': this.token
              }
              })
              .then(() => {
                this.fetchData()
              })
              .catch(error => {
                console.log(error)
              })
            })
            .catch(error => {
                console.log(error)
            })
          })
          .catch(error => {
            console.log(error)
          })
    },
    weishi(row) {
      this.dialogFormVisible = true
      this.dialogForm.pid = row.pid
    },
    weishiUpdate() {
      this.dialogFormVisible = false
      let url = this.$store.state.settings.baseurl + '/feed'
      axios.get(url,{
          headers: {
            'Authorization': this.token
          }
        })
          .then(response => {
            let Ddata = response.data.data
            let n = parseInt(Ddata.length)+1
            let fid = 'F' + n
            axios.post(url, {
              "fid": fid,
              "pid": this.dialogForm.pid,
              "ftime": this.dialogForm.ftime
            },
            {
              headers: {
                'Authorization': this.token
              }
            })
            .then(() => {
              this.$message({
                type: 'success',
                message: '喂食成功!'
              });
            })
            .catch(error => {
                console.log(error)
            })
          })
          .catch(error => {
            console.log(error)
          })
    },
    search(LandID,LandName,CropName,Uid) {
      if(this.type == 1){
        Message.error("抱歉，您没有此权限！")
        return
      }     
      let url = this.$store.state.settings.baseurl + '/pasture'
      axios.get(url, {
          headers: {
          'Authorization': this.token
          },
          params:{
            pid: LandID,
            pname: LandName,
            pasturename: CropName,
            wid: Uid
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
    fetchData () {
      let url = this.$store.state.settings.baseurl + '/pasture'
      if(this.type == 1){
        url = url + '?wid=' + this.uid
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
      this.dialogForm2.pid = row.pid
      this.dialogForm2.pname = row.pname
      this.dialogForm2.poultryid = row.poultryid
      this.dialogForm2.poultryname = row.poultryname
      this.dialogForm2.poultrynum = row.poultrynum
      this.dialogForm2.starttime = row.starttime
      this.dialogForm2.wid = row.wid
      this.dialogForm2.maturetime = row.maturetime
      this.dialogForm2.stage = row.stage
      this.dialogForm2.posinfo = row.posinfo
    },
    Update() {
      this.dialogFormVisible2 = false
      const url = this.$store.state.settings.baseurl + '/pasture'
      axios.put(url,{
        "pid": this.dialogForm2.pid,
        "pname": this.dialogForm2.pname,
        "poultryid": this.dialogForm2.poultryid,
        "poultryname": this.dialogForm2.poultryname,
        "poultrynum": this.dialogForm2.poultrynum,
        "starttime": this.dialogForm2.starttime,
        "wid": this.dialogForm2.wid,
        "maturetime": this.dialogForm2.maturetime,
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
