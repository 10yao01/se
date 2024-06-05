<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 30%;" placeholder="输入关键字搜索" v-model="searchText" />
          <el-button-group style="margin-left: 15px;">
            <el-button size="media" @click="search(searchText)"  icon="el-icon-search">搜索</el-button>
          </el-button-group>
          <el-button type="primary" style="margin-left: 690px" @click="dialogFormVisible = true">+ 新增地区</el-button>

          <el-dialog title="新增地区" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="dialogForm">
              <el-form-item label="地区名称" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="地区面积" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.extent" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="AddData()">确 定</el-button>
            </div>
          </el-dialog>
        </el-col>
      </el-row>


      <el-row style="margin-top: 20px">
        <el-col :span="22" :offset="1">
          <el-table :data="formData" border height="568" stripe style="width: 100%">
            <el-table-column prop="id" label="地区编号" sortable>
            </el-table-column>
            <el-table-column prop="name" label="地区名" sortable>
            </el-table-column>
            <el-table-column prop="extent" label="地区面积" sortable>
            </el-table-column>
            <el-table-column prop="population" label="地区人口" sortable>
            </el-table-column>
            <el-table-column prop="areaScore" label="地区信用均分" sortable>
              <template slot-scope="scope">
                <el-tag :type="statusType[scope.row.status]" disable-transitions>{{ statusText[scope.row.status]
                }}：{{scope.row.areaScore}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template slot-scope="scope">
                <el-button
                  @click.native.prevent="openDelete(scope.$index, scope.row, formData)"
                  type="text"
                  size="small">
                  移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>

    </el-main>

  </el-container>
</template>

<script>
import {Message} from 'element-ui'
import axios from 'axios'

export default {

  data () {
    return {
      dialogFormVisible: false,
      dialogForm: {
          name: '',
          extent: ''
      },
      statusType: ['success','warning','danger'],
      statusText: ['优','良','差'],
      formLabelWidth: '70px',
      formData: [],
      farmerData: [],
      oneData: {},
      searchText: '',
      name: '',
      token: ''
    }
  },
  created(){
    axios.get(this.$store.state.settings.baseurl + '/farmer' ,{
        headers: {
          'Authorization': this.token
        }
    })
        .then(response => {
          this.farmerData = response.data.data
        })
        .catch(error => {
          console.log(error)
        })
    //页面加载时就从本地通过localstorage获取存储的token值
    this.token =  window.localStorage.getItem('token')
    this.name = window.localStorage.getItem('name')
  },
  mounted () {
    if (this.$route.params.id) {
      const url = this.$store.state.settings.baseurl + '/area/' + this.$route.params.id
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.oneData = res.data.data
        }
      })
    } else {
      this.fetchData()
    }
  },
  methods: {
    openDelete(index, row, rows) {
      if(this.name=='root') {       
        this.$confirm('此操作将永久删除该地区, 是否继续?', '提示', {
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
    AddData() {
      if(this.name=='root'){     
        this.dialogFormVisible = false;
        const url = this.$store.state.settings.baseurl + '/area';
        axios.post(url, {
          "id":this.formData[this.formData.length-1].id+1,
          "name":this.dialogForm.name,
          "extent":this.dialogForm.extent
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
    fetchData () {
      axios.get(this.$store.state.settings.baseurl + '/area', {
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          this.formData = response.data.data
          for(let i=0;i<this.formData.length;i++){
            let cnt=0;
            let sum=0;
            this.formData[i].areaScore = ''
            this.formData[i].status = ''
            for(let j=0;j<this.farmerData.length;j++){
              if(this.formData[i].name == this.farmerData[j].areaname){
                cnt = cnt+1;
                sum = sum+this.farmerData[j].score;
              }
            }
            this.formData[i].areaScore = sum/cnt;
            if(this.formData[i].areaScore >= 90){
              this.formData[i].status = 0
            }else if(this.formData[i].areaScore >= 70){
              this.formData[i].status = 1
            }else{
              this.formData[i].status = 2
            }
          }
          console.log(this.formData)
        })
        .catch(error => {
          console.log(error)
        })
    },
    deleteRow (index, row, rows) {
      let Aid = row.id;
      const url = this.$store.state.settings.baseurl + '/area/' + Aid;
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
    search (Aname) {
      if(Aname == ''){
        this.fetchData()
      }else{
      const url = this.$store.state.settings.baseurl + '/area/' + Aname
      axios.get(url, {
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          this.formData = response.data.data

        })
        .catch(error => {
          console.log(error)
        })
      }
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
</style>
