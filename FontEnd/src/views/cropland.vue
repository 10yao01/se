<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入农田编号" v-model="LandID" />
          <el-input style="width: 20%;" placeholder="请输入农田名称" v-model="LandName" />
          <el-input style="width: 20%;" placeholder="请输入农田种植的作物名称" v-model="CropName" />
          <el-input style="width: 20%;" placeholder="请输入农田管理员号" v-model="Uid" />
          <el-button size="media" @click="search(LandID,LandName,CropName,Uid)" icon="el-icon-search">搜索</el-button>
         
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="fid" label="农田编号"  width="105px" sortable>
          </el-table-column>
          <el-table-column prop="fname" label="农田名称">
            
            </el-table-column>
            <el-table-column prop="cid" label="作物编号" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="cname" label="种植作物" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="camount" label="种植数量" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="pdate" label="种植时间" sortable>
            </el-table-column>
            <el-table-column prop="wid" label="管理员号" sortable>
            </el-table-column>
            <el-table-column prop="harvestdate" label="预计收成时间" sortable>
            </el-table-column>
            <el-table-column prop="stage" label="成长阶段" width="105px" sortable>
            </el-table-column>
            <el-table-column prop="posinfo" label="农场位置备注" sortable>
            </el-table-column>
            <el-table-column label="操作" width="280px">
            <template slot-scope="scope">
              <el-button size="mini" type="success" @click="guangai(scope.row)">
                灌溉
              </el-button>
              <el-dialog title="灌溉时间" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="dialogForm">
                  <el-form-item label="灌溉时间" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm.optime" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="guangaiUpdate()">确 定</el-button>
                </div>
              </el-dialog>
              <el-button size="mini" type="primary" @click="shifei(scope.row)">
                施肥
              </el-button>
              <el-dialog title="施肥时间" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="dialogForm">
                  <el-form-item label="施肥时间" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm.optime" autocomplete="off"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="shifeiUpdate()">确 定</el-button>
                </div>
              </el-dialog>
              <el-button size="mini" type="info" @click="handleInfo(scope.row)">
                修改
              </el-button>
              <el-dialog title="修改农田信息" :visible.sync="dialogFormVisible2" width="30%">
                <el-form :model="dialogForm2">
                  <el-form-item label="养殖场编号" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.fid" autocomplete="on"></el-input>
                  </el-form-item>
                  <el-form-item label="养殖场名称" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.fname" autocomplete="on"></el-input>
                  </el-form-item>
                  <el-form-item label="作物编号" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.cid" autocomplete="on"></el-input>
                  </el-form-item>
                  <el-form-item label="种植作物" :label-width="formLabelWidth">
                    <el-input v-model="dialogForm2.cname" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="种植数量" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.camount" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="种植时间" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.pdate" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="管理员号" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.wid" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="预计收成时间" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.harvestdate" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="成长阶段" :label-width="formLabelWidth">
                      <el-input v-model="dialogForm2.stage" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="农场位置备注" :label-width="formLabelWidth">
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
          fid:'',
          farmid:'',
          optype: '',
          optime: '',
      },
      dialogForm2: {
          fid: '',
          fname: '',
          cid: '',
          cname: '',
          camount: '',
          pdate: '',
          wid: '',
          harvestdate: '',
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
      })
    }
  },
  methods: {
    shangchuan(row) {
      this.dialogFormVisible3 = true
      this.batchform.originid = row.fid
      this.batchform.gname = row.cname
      this.batchform.amount = row.camount
      this.batchform.gid = row.cid
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
              let url2 = this.$store.state.settings.baseurl + '/farm'
              axios.put(url2, {
                "fid": this.batchform.originid,
                "camount": 0
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
    guangai(row) {
      this.dialogFormVisible = true
      this.dialogForm.farmid = row.fid
    },
    guangaiUpdate() {
      this.dialogFormVisible = false
      let url = this.$store.state.settings.baseurl + '/fertile'
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
              "farmid": this.dialogForm.farmid,
              "optype": 0,
              "optime": this.dialogForm.optime
            },
            {
              headers: {
                'Authorization': this.token
              }
            })
            .then(() => {
              this.$message({
                type: 'success',
                message: '灌溉成功!'
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
    shifei(row) {
      this.dialogFormVisible = true
      this.dialogForm.farmid = row.fid
    },
    shifeiUpdate() {
      this.dialogFormVisible = false
      let url = this.$store.state.settings.baseurl + '/fertile'
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
              "farmid": this.dialogForm.farmid,
              "optype": 1,
              "optime": this.dialogForm.optime
            },
            {
              headers: {
                'Authorization': this.token
              }
            })
            .then(() => {
              this.$message({
                type: 'success',
                message: '施肥成功!'
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
      let url = this.$store.state.settings.baseurl + '/farm'
      axios.get(url, {
          headers: {
          'Authorization': this.token
          },
          params:{
            fid: LandID,
            fname: LandName,
            cname: CropName,
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
      let url = this.$store.state.settings.baseurl + '/farm'
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
