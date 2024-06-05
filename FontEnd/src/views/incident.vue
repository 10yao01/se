<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入" v-model="searchTitle" />
          <el-input style="width: 20%;" placeholder="请输入涉及人员姓名" v-model="searchName" />
          <el-select v-model="searchClass" clearable placeholder="请选择事件分类" style="width: 20%">
            <el-option label="设施安全" value="1"></el-option>
            <el-option label="食品安全" value="2"></el-option>
            <el-option label="公共安全" value="3"></el-option>
          </el-select>
          <el-button size="media" @click="search(searchTitle, searchName, searchClass)"  icon="el-icon-search">搜索</el-button>
          <el-button type="primary" style="margin-left: 290px;" @click="dialogFormVisible = true">+ 新增事件</el-button>
          <el-dialog title="新增地区" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="dialogForm">
              <el-form-item label="事件名称" :label-width="formLabelWidth">
                <el-input v-model="dialogForm.title" autocomplete="off"></el-input>
              </el-form-item>
              <div>
                <span style="font-weight: bold">涉事人员&nbsp&nbsp</span>
                <el-select v-model="dialogForm.fid" placeholder="请选择" filterable>
                  <el-option
                    v-for="item in farmerData"
                    :key="item.fid"
                    :label="item.name"
                    :value="item.fid">
                    <span style="float: left">{{ item.name }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.fid }}</span>
                  </el-option>
                </el-select>
              </div>
              <div>
                <span style="font-weight: bold">事件分类&nbsp&nbsp</span>
                <el-select v-model="dialogForm.classes" placeholder="请选择">
                  <el-option label="设施安全" value="1"></el-option>
                  <el-option label="食品安全" value="2"></el-option>
                  <el-option label="公共安全" value="3"></el-option>
                </el-select>
              </div>
              <div>
                <span style="font-weight: bold">严重程度&nbsp&nbsp</span>
                <el-select v-model="dialogForm.degree" placeholder="请选择">
                  <el-option label="1" value="1"></el-option>
                  <el-option label="2" value="2"></el-option>
                  <el-option label="3" value="3"></el-option>
                  <el-option label="4" value="4"></el-option>
                  <el-option label="5" value="5"></el-option>
                </el-select>
              </div>
              <el-form-item label="事件描述" :label-width="formLabelWidth" style="margin-top: 20px">
                <el-input v-model="dialogForm.description" type="textarea" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="AddData()">确 定</el-button>
            </div>
           </el-dialog>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
            <el-table-column prop="iid" label="事件编号" sortable width="120px">
            </el-table-column>
            <el-table-column prop="title" label="事件名称" sortable>
            </el-table-column>
            <el-table-column prop="name" label="涉事人员" sortable>
            </el-table-column>
            <el-table-column prop="fid" label="涉事人员编号" sortable width="130px">
            </el-table-column>
            <el-table-column prop="classes" label="事件分类" sortable>
              <template slot-scope="scope">
                <el-tag :type="classType[scope.row.classes]" disable-transitions>{{ classText[scope.row.classes]
                }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="degree" label="严重程度" sortable width="120px">
            </el-table-column>
            <el-table-column prop="isdeal" label="状态" sortable>
              <template slot-scope="scope">
                <el-tag :type="statusType[scope.row.isdeal]" disable-transitions>{{ statusText[scope.row.isdeal]
                }}</el-tag>
              </template>
            </el-table-column>

            <el-table-column label="操作" width="280px">
                <template slot-scope="scope">
                    <el-button 
                    type="info" 
                    size="mini" 
                    @click="handleInfo(scope.row.description)">详情</el-button>
                    <el-dialog
                        title="事件详情"
                        :visible.sync="dialogVisible"
                        width="30%">
                        <span>{{descriptionData}}</span>
                        <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
                        </span>
                    </el-dialog>
                    <el-button
                    size="mini"
                    @click="deal(scope.$index, pagedData)">处理</el-button>
                    <el-button
                    size="mini"
                    type="danger"
                    @click="openDelete(scope.$index, scope.row, formData)">删除</el-button>
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
      farmerData: [],
      oneData: {},
      searchTitle: '',
      searchName: '',
      searchClass: '',
      descriptionData: '',
      dialogForm: {
          title: '',
          fid: '',
          description: '',
          classes: '',
          degree: ''
      },
      dialogVisible: false,
      dialogFormVisible: false,
      formLabelWidth: '70px',
      classText: ['','设施安全','食品安全','公共安全'],
      classType: ['','info','warning','danger'],
      statusText: ['未处理', '已处理'],
      statusType: ['danger', 'success'],
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      statusFileter: [0, 1],
      name: '',
      token: ''
    }
  },
  created() {
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
    this.name = window.localStorage.getItem('name')
    this.token = window.localStorage.getItem('token')
  },
  mounted () {
    if (this.$route.params.iid) {
      const url = this.$store.state.settings.baseurl + '/incident/' + this.$route.params.iid
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
        return this.statusFileter.includes(row.isdeal)
      })
    }
  },
  methods: {
    AddData() {
      if(this.name=='root'){
        this.dialogFormVisible = false;
        const url = this.$store.state.settings.baseurl + '/incident';
        axios.post(url, {
          "iid": this.formData[this.formData.length-1].iid+1,
          "title": this.dialogForm.title,
          "fid": this.dialogForm.fid,
          "name": this.farmerData[this.dialogForm.fid-1].name,
          "classes": this.dialogForm.classes,
          "degree": this.dialogForm.degree,
          "description": this.dialogForm.description
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
    search(searchTitle, searchName, searchClass) {
      if(this.name=='root'){
        let url = this.$store.state.settings.baseurl + '/incident?'
        if(searchTitle != '') {
            url = url + 'title=' + searchTitle
            if(searchName != '') {
                url = url + '&name=' + searchName
            }
            if(searchClass != '') {
                url = url + '&classes=' + searchClass
            }
        }else if(searchName != '') {
            url = url + 'name=' + searchName
            if(searchClass != '') {
                url = url + '&classes=' + searchClass
            }
        }else if(searchClass != '') {
            url = url + 'classes=' + searchClass
        }
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
        Message.error("没有此权限")
      }
    },
    deleteRow (index, row, rows) {
      let Iid = row.iid;
      const url = this.$store.state.settings.baseurl + '/incident/' + Iid;
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
      let url = this.$store.state.settings.baseurl + '/incident'
      if(this.name!='root'){
        url = url + '?name=' +this.name
      }
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          this.formData = response.data.data
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
    handleInfo (description) {
      this.dialogVisible = true;
      this.descriptionData = description;
    },
    deal (index, rows) {
        const url = this.$store.state.settings.baseurl + '/incident/' + rows[index].iid
        axios.put(url,{
            headers: {
                'Authorization': this.token
            }
        })
        .then(() => {
            this.fetchData();
            this.$message({
            type: 'success',
            message: '处理成功!'
          });
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
</style>
