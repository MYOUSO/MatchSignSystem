<template>
  <div>
    <el-container style="height: 850px; border: 1px solid #eee">
      <el-header style="font-size:40 ;background-color:rgb(238, 241, 246)"
        >报名管理系统-管理员面板</el-header
      >
      <el-container>
        <el-menu :default-openeds="['1', '3']">
          <el-submenu index="1">
            <template slot="title"
              ><i class="el-icon-message"></i>用户入口</template
            >
            <el-menu-item index="1-1">
              <router-link to="/games" id="link">比赛界面</router-link>
            </el-menu-item>
            <el-menu-item index="1-2">
              <router-link to="/account" id="link">个人中心</router-link>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"
              ><i class="el-icon-menu"></i>管理员入口</template
            >
            <el-menu-item index="2-1">
              <router-link to="/administrators-addition" id="link"
                >管理员面板</router-link
              >
            </el-menu-item>
          </el-submenu>
        </el-menu>
        <el-container>
          <el-main>
            
            <!-- 表单 -->
            <el-form
              :inline="true"
              :model="searchForm"
              class="demo-form-inline"
            >
              <el-form-item label="比赛名称">
                <el-input
                  v-model="searchForm.name"
                  placeholder="比赛名称"
                ></el-input>
              </el-form-item>
              <el-form-item label="比赛类型">
                <el-select v-model="searchForm.type" placeholder="比赛类型">
                  <el-option label="个人赛" value="1"></el-option>
                  <el-option label="团体赛" value="2"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="排列方式" prop="orderType">
                      <el-select
                        v-model="searchForm.orderType"
                        placeholder="请选择排列的方式"
                      >
                        <el-option label="参赛名额" value="count"></el-option>
                        <el-option label="已报名人数" value="sign_count"></el-option>
                        <el-option label="开始时间" value="start_time"></el-option>
                        <el-option label="结束时间" value="end_time"></el-option>
                      </el-select>
                      <el-button @click="toggleSort" type="primary">{{ sortOrderText }}</el-button>
                      </el-form-item>
                <el-form-item label="报名开始时间" prop="startTime">
                    <el-date-picker
                      v-model="searchForm.startTime"
                      value-format="yyyy-MM-dd-HH-mm-ss"
                      type="datetime"
                      placeholder="选择报名开始时间"
                    >
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item label="结束报名时间" prop="endTime">
                    <el-date-picker
                      v-model="searchForm.endTime"
                      value-format="yyyy-MM-dd-HH-mm-ss"
                      type="datetime"
                      placeholder="选择报名结束时间"
                    >
                    
                    </el-date-picker>
                    <el-button type="primary" @click="handleSearch">查询</el-button>
                    <el-button type="primary" @click="resetSearch">重置</el-button>
                    <el-button type="primary" @click="exportToExcel">导出 Excel</el-button>
                <el-button type="primary" @click="showForm">添加比赛</el-button>
                  </el-form-item>
              <el-form-item>
                
                
                
                <el-dialog :visible.sync="formVisible" title="添加比赛"  @close="closeForm" >
                  <el-form :model="formData" label-width="80px">
                    <el-form-item label="名称" prop="name">
                      <el-input v-model="formData.name"></el-input>
                    </el-form-item>
                    <el-form-item label="简介" prop="introduction">
                      <el-input v-model="formData.introduction"></el-input>
                    </el-form-item>
                    <el-form-item label="类型" prop="type">
                      <el-select
                        v-model="formData.type"
                        placeholder="请选择比赛类型"
                      >
                        <el-option label="个人赛" value="1"></el-option>
                        <el-option label="团体赛" value="2"></el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="人数上限" prop="count">
                      <el-input v-model.number="formData.count"></el-input>
                    </el-form-item>
                    <el-form-item label="报名开始时间" prop="startTime">
                      <el-date-picker
                        v-model="formData.startTime"
                        type="datetime"
                        placeholder="选择报名开始时间"
                      >
                      </el-date-picker>
                    </el-form-item>
                    <el-form-item label="报名结束时间" prop="endTime">
                      <el-date-picker
                        v-model="formData.endTime"
                        type="datetime"
                        placeholder="选择报名结束时间"
                      >
                      </el-date-picker>
                    </el-form-item>
                    <el-form-item>
                      <el-button
                        type="primary"
                        @click="submitForm"
                        class="center-btn"
                        >提交</el-button
                      >
                    </el-form-item>
                  </el-form>
                </el-dialog>
              </el-form-item>
            </el-form>

            <!-- 表格展示 -->
            <template>
              <el-table :data="tableData" style="width: 100%" border>
                <el-table-column prop="name" label="比赛名称" width="180">
                </el-table-column>
                <el-table-column
                  prop="introduction"
                  label="比赛简介"
                  width="180"
                ></el-table-column>
                <el-table-column label="比赛类型">
                  <template slot-scope="scope">
                    {{ scope.row.type == 1 ? "个人赛" : "团体赛" }}
                  </template>
                </el-table-column>
                <el-table-column prop="count" label="参赛名额">
                </el-table-column>
                <el-table-column prop="signCount" label="已报名人数">
                </el-table-column>
                <el-table-column label="报名开始时间" width="180">
                  <template slot-scope="scope">
                    {{ scope.row.startTime.replace("T", "\n ") }}
                  </template>
                </el-table-column>
                <el-table-column label="报名结束时间" width="180">
                  <template slot-scope="scope">
                    {{ scope.row.endTime.replace("T", " ") }}
                  </template>
                </el-table-column>
                <el-table-column label="状态" width="60">
                  <template slot-scope="scope">
                    {{
                      scope.row.state == 1
                        ? "已满"
                        : scope.row.state == 2
                        ? "截止"
                        : scope.row.state ==3
                        ? "未开始"
                        : "未满"
                    }}
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button type="primary" @click="showDeleteDialog(scope.row.id)" size="mini">删除</el-button>
                    <el-button type="primary" @click="editGame(scope.row)" size="mini" >编辑</el-button >
                  </template>
                </el-table-column>
              </el-table>
              <!-- Form Dialog -->
           
              <el-dialog
                :visible.sync="editFormVisible"
                title="编辑比赛"
                @close="closeForm"
              >
                <el-form :model="editFormData" label-width="80px">
                  <el-form-item label="名称" prop="name">
                    <el-input v-model="editFormData.name"></el-input>
                  </el-form-item>
                  <el-form-item label="简介" prop="introduction">
                    <el-input v-model="editFormData.introduction"></el-input>
                  </el-form-item>
                  <el-form-item label="类型" prop="type">
                    <el-select v-model="editFormData.type">
                      <el-option label="个人赛" :value="1"></el-option>
                      <el-option label="团体赛" :value="2"></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="人数上限" prop="count">
                    <el-input v-model.number="editFormData.count"></el-input>
                  </el-form-item>
                  <el-form-item label="开始时间" prop="startTime">
                    <el-date-picker
                      v-model="editFormData.startTime"
                      type="datetime"
                      placeholder="选择报名开始时间">
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item label="结束时间" prop="endTime">
                    <el-date-picker
                      v-model="editFormData.endTime"
                      type="datetime"
                      placeholder="选择报名结束时间"
                    >
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item label="更新信息" prop="updateInfo">
              <span style="font-size: 12px; color: #999;">最近一次更新时间:{{editFormData.updateTime}}/最近一次更新人:{{editFormData.updatePerson}}</span>
            </el-form-item>
                  <el-form-item>
                    <el-button
                      type="primary"
                      @click="submitEditForm"
                      class="center-btn"
                      >保存</el-button
                    >
                  </el-form-item>
                </el-form>
              </el-dialog>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[10, 20, 30, 40]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totalItems"
              >
              </el-pagination>
            </template>
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import Element from "element-ui";
import * as XLSX from "xlsx";
// import axios from "axios";

export default {
  props: ["ID", "gameIndex"],
  data() {
    return {
      ascending: true,
      user:"",
      currentPage: 1,
      pageSize: 10,
      totalItems: 0,
      editGameData: null,
      selectedScope: null,
      editFormVisible: false,
      editFormData: {
        name: "",
        introduction: "",
        type: 0,
        count: 0,
        startTime: null,
        endTime: null,
        updateTime:null,
        updatePerson:""
      },
      formVisible: false,
      formData: {
        name: "",
        introduction: "",
        type: 0,
        count: 0,
        startTime: null,
        endTime: null,
        creator:"",
        updatePerson:""
      },
      tableData: [],
      searchForm: {
        name: "",
        type: "",
        startTime: null,
        endTime: null,
        orderType:"",
        order:1,
        
      },
      searchResults: [],
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
      deleteConfirmationVisible: false,
    };
  },
  mounted() {
    // 在组件挂载时调用后端接口
    this.fetchGames();
  },
  created(){
        this.user=JSON.parse(window.localStorage.getItem("user"))
          
      },
      computed: {
    sortOrderText() {
      return this.ascending ? '升序' : '降序';
    }
  },
  methods: {
    toggleSort() {
      this.ascending = !this.ascending;
      this.$emit('sort-changed', this.ascending);
    },
  

    resetSearch(){
      this.searchForm.name="",
      this.searchForm.type="",
      this.searchForm.orderType="",
      this.searchForm.startTime=null,
      this.searchForm.endTime=null
      axios.get('/api/games/page',{
          headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json' // 可根据需要设置其他请求头
      },params:{
            currentPage: this.currentPage,
            pageSize: this.pageSize}
      },).then(response=>{
        this.tableData = response.data.data.rows;
        this.totalItems = response.data.data.total;
      }).catch(error => {
        console.error('Error during reset search:', error);
      });
    },
    handleSearch() {
      if(this.searchForm.type!==""){
        console.log("通过:type不为空")
      }else{
        this.searchForm.type=0;
        console.log("通过:type赋值为0")
        console.log(this.searchForm.type)
      }
      if(this.ascending){
        this.searchForm.order=1;

      }else{
        this.searchForm.order=2;

      }
    axios.get('/api/games/page/text', {
          headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json' // 可根据需要设置其他请求头
      },params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
            name:this.searchForm.name,
            type:this.searchForm.type,
            orderType:this.searchForm.orderType,
            order:this.searchForm.order,
            startTime:this.searchForm.startTime,
            endTime:this.searchForm.endTime}
          },
          )
      .then(response => {
        this.tableData = response.data.data.rows;
        this.totalItems = response.data.data.total;
      })
      .catch(error => {
        console.error('Error during search:', error);
        this.searchForm.type="";
      });
  },
   
    exportToExcel() {
      const ws = XLSX.utils.json_to_sheet(this.tableData);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "Sheet1");
      XLSX.writeFile(wb, "table_data.xlsx");
    },

    editGame(row) {
      this.editFormData = { ...row };
      this.editFormVisible = true;
    },
    submitEditForm() {
      const id = this.editFormData.id;
      this.editFormData.updatePerson=this.user.account;
      const jsonEditData = JSON.stringify(this.editFormData);
      axios
        .put(`/api/games/${id}`, jsonEditData, {
          headers: {
            'Authorization': `Bearer ${this.user.token}`,
            "Content-Type": "application/json" },
        })
        .then((response) => {
          // 处理删除成功的逻辑
          console.log("编辑成功", response);

          this.fetchGames();

          Element.Message.success("恭喜你，编辑成功");
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("编辑失败", error);
        });
      this.editFormVisible = false;
    },
    onSubmit: function () {
      alert("查询数据");
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.fetchGames();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchGames();
    },
    async fetchGames() {
      try {
        // 发送GET请求到 GamesController 接口
        const response = await axios.get("/api/games/page", {
          headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json'   
      },
      
          params: {
            currentPage: this.currentPage,
            pageSize: this.pageSize,
          },
    },
        );
        // 更新组件的比赛列表数据
        this.tableData = response.data.data.rows;
        this.totalItems = response.data.data.total;
      } catch (error) {
        console.error("Error fetching games from backend:", error);
      }
    },
    showForm() {
      this.formVisible = true;
    },
    closeForm() {
      this.formVisible = false;
      // 清空表单数据
      this.formData = {
        name: "",
        introduction: "",
        type: "",
        count: 0,
        start_time: "",
        end_time: "",
      };
    },
    submitForm() {
      this.formData.creator=this.user.account;
      this.formData.updatePerson=this.user.account
      // 将表单数据打包成 JSON 对象
      const jsonData = JSON.stringify(this.formData);

      // 发送 POST 请求到后端接口
      this.$axios
        .post("/api/games",jsonData, {
          headers: {
            'Authorization': `Bearer ${this.user.token}`,
             "Content-Type": "application/json" },
        })
        .then((response) => {
          // 处理成功的情况
          console.log("成功", response.data);
          this.closeForm(); // 关闭表单
          Element.Message.success("增加比赛成功");
          this.fetchGames();
        })
        .catch((error) => {
          // 处理失败的情况
          console.error("失败", error);
        });
    },
    showDeleteDialog(itemID) {
      // 弹出确认删除的提示框
      this.$confirm("确认删除该项？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 用户点击确定，触发删除确认，将要删除的项的标识符传递给父组件
          this.deleteItem(itemID);
        })
        .catch(() => {
          // 用户点击取消，不执行任何操作
        });
    },
    deleteItem(id) {
      // 发送删除请求
      axios
        .delete(`/api/games/${id}`,{headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json'   
      },})
        .then((response) => {
          // 处理删除成功的逻辑
          console.log("删除成功", response);

          this.fetchGames();

          Element.Message.success("恭喜你，删除成功");
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("删除失败", error);
        });
    },
  },
};
</script>


<style scoped>
.center-btn {
  margin: 0 auto;
  display: block;
}
.game-form {
  max-width: 400px;
  margin: 20px auto;
}

/* 可以根据需要添加其他样式 */

#link {
  text-decoration: none;
  color: #1bb9ab8d; /* 设置链接颜色，可以根据需要调整 */
  font-weight: bold; /* 加粗文字 */
  transition: color 0.3s ease; /* 添加过渡效果 */
}
#link:hover {
  color: rgba(0, 252, 235, 0.834) 2d;
}
</style>