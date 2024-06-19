<template>
  <el-container>
    <el-header>
      <h3 class="title">智慧农场管理平台</h3>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="22" :offset="1">
          <el-input style="width: 20%;" placeholder="请输入批次编号" v-model="searchBid" />
          <el-input style="width: 20%;" placeholder="请输入商品名称" v-model="searchGname" />
          <el-button size="media" @click="search(searchBid, searchGname)" icon="el-icon-search">搜索</el-button>
         
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="22" :offset="1">
          <el-table :data="pagedData" border height="550" style="width: 100%">
          <el-table-column prop="bid" label="批次编号" sortable>
          </el-table-column>
          <el-table-column prop="bdate" label="进货日期" sortable>
            </el-table-column>
          <el-table-column prop="originid" label="农田（养殖场）编号" sortable>
            </el-table-column>
          <el-table-column prop="originname" label="农田（养殖场）名称" sortable>
            </el-table-column>
          <el-table-column prop="gid" label="商品编号" sortable>
            </el-table-column>
          <el-table-column prop="gname" label="商品名称" sortable>
            </el-table-column>
          <el-table-column prop="amount" label="数量" sortable>
          </el-table-column>
          <el-table-column prop="isdealed" label="状态" sortable>
            <template slot-scope="scope">
                <el-tag :type="stateType[scope.row.isdealed]" disable-transitions>{{ state[scope.row.isdealed] }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" type="info" @click="Pass(scope.row)">
                通过
              </el-button>
              <el-button size="mini" type="danger" @click="NoPass(scope.row)">
                不通过
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
      searchBid: '',
      searchGname: '',
      descriptionData: '',
      formLabelWidth: '70px',
      pageSize: 10,
      firstRecord: 1,
      lastRecord: 999,
      stateType: ['danger','warning','success'],
      state: ['审批不通过', '未审批', '审批通过'],
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
    Pass(row) {
      if(this.type == 1){
        Message.error("抱歉，您没有此权限！")
        return 
      }
      let url = this.$store.state.settings.baseurl + '/batch'
      if(row.isdealed != 1){
        Message.error("请勿重复审核！")
      }else{
        axios.put(url,{
        "bid": row.bid,
        "isdealed": 1
      },{
        headers: {
          'Authorization': this.token
        }
      })
      .then(() => {
        this.fetchData()
        this.$message({
            type: 'success',
            message: '审核成功!'
        })
      })
      }
    },
    NoPass(row) {
      if(this.type == 1){
        Message.error("抱歉，您没有此权限！")
        return 
      }
      let url = this.$store.state.settings.baseurl + '/batch'
      if(row.isdealed != 1){
        Message.error("请勿重复审核！")
      }else{
        axios.put(url,{
        "bid": row.bid,
        "isdealed": -1
      },{
        headers: {
          'Authorization': this.token
        }
      })
      .then(() => {
        this.fetchData()
        this.$message({
            type: 'success',
            message: '审核成功!'
        })
      })
      }
    },
    search(searchBid, searchGName) {     
      let url = this.$store.state.settings.baseurl + '/batch'
      axios.get(url, {
          headers: {
          'Authorization': this.token
          },
          params:{
              bid: searchBid,
              gname: searchGName
            }
      })
      .then(response => {
        let Ddata = response.data.data
        for(let i = 0;i<Ddata.length;i++){
            Ddata[i].isdealed = Ddata[i].isdealed+1
        }    
        Ddata = Ddata.map(obj => {
              obj.originname = '1';
              return obj;
          });
        for(let i = 0;i<Ddata.length;i++){
          let url2 = this.$store.state.settings.baseurl
          if(Ddata[i].originid[0] == 'P'){
            url2 += '/pasture?pid=' + Ddata[i].originid
            axios.get(url2,{
              headers: {
                'Authorization': this.token
              }
            })
            .then(response => {
              let Ndata = response.data.data
              Ddata[i].originname = Ndata[0].pname
            })
            .catch(error => {
              console.log(error)
            })
          }else{
            url2 += '/farm?fid=' + Ddata[i].originid
            axios.get(url2,{
              headers: {
                'Authorization': this.token
              }
            })
            .then(response => {
              let Ndata = response.data.data
              Ddata[i].originname = Ndata[0].fname
            })
            .catch(error => {
              console.log(error)
            })
          }
        }
        this.formData = Ddata
      })
      .catch(error => {
        console.log(error)
      })
    },
    fetchData () {
      let url = this.$store.state.settings.baseurl + '/batch'
      axios.get(url,{
        headers: {
          'Authorization': this.token
        }
      })
        .then(response => {
          let Ddata = response.data.data
          for(let i = 0;i<Ddata.length;i++){
            Ddata[i].isdealed = Ddata[i].isdealed+1
          }        
          Ddata = Ddata.map(obj => {
              obj.originname = '1';
              return obj;
          });
          for(let i = 0;i<Ddata.length;i++){
            let url2 = this.$store.state.settings.baseurl
            if(Ddata[i].originid[0] == 'P'){
              url2 += '/pasture?pid=' + Ddata[i].originid
              axios.get(url2,{
                headers: {
                  'Authorization': this.token
                }
              })
              .then(response => {
                let Ndata = response.data.data
                Ddata[i].originname = Ndata[0].pname
              })
              .catch(error => {
                console.log(error)
              })
            }else{
              url2 += '/farm?fid=' + Ddata[i].originid
              axios.get(url2,{
                headers: {
                  'Authorization': this.token
                }
              })
              .then(response => {
                let Ndata = response.data.data
                Ddata[i].originname = Ndata[0].fname
              })
              .catch(error => {
                console.log(error)
              })
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
