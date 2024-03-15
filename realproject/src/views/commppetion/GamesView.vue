<template>
  <div>
    <el-container style="height: 850px; border: 1px solid #eee">
      <el-header style="font-size:40;background-color:rgb(238, 241, 246)"
        >报名管理系统</el-header
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
          <el-submenu index="2" v-if="this.user.permission >= 2" >
            <template slot="title"
              ><i class="el-icon-menu"></i>管理员入口</template
            >
            <el-menu-item index="2-1">
              <router-link to="/administrators-addition" id="link">管理员面板</router-link>
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
              <el-form-item label="比赛报名时间">
                <el-date-picker
                  v-model="searchForm.time"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="报名开始日期"
                  end-placeholder="报名结束日期"
                >
                </el-date-picker>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
              </el-form-item>
            </el-form>

            <!-- 表格展示 -->
            <template>
              <el-table :data="tableData" style="width: 100%" border>
                <el-table-column prop="name" label="比赛名称" width="180">
                </el-table-column>
                <el-table-column prop="introduction" label="比赛简介" width="180"></el-table-column>                                                          
                <el-table-column  label="比赛类型">
                  <template slot-scope="scope">
                    {{ scope.row.type==1 ? '个人赛' :'团体赛' }}
                  </template>
                </el-table-column>
                <el-table-column prop="count" label="参赛名额">
                </el-table-column>
                <el-table-column prop="signCount" label="已报名人数">
                </el-table-column>
                <el-table-column label="报名开始时间" width="180">
                  <template slot-scope="scope">
                    {{ scope.row.startTime.replace('T', '\n ') }}
                  </template>
                </el-table-column>
                <el-table-column label="报名结束时间"  width="180">
                  <template slot-scope="scope">
                    {{ scope.row.endTime.replace('T', ' ') }}
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
                    <el-button type="primary" @click="signUp(scope.row.id)" size="mini">参加</el-button>
                    <el-button type="primary" @click="moreInfo(scope.row.id)" size="mini" >详情</el-button >
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage4"
                :page-sizes="[5,10, 15,20,,25 ,30,35, 40]"
                :page-size="5"
                layout="total, sizes, prev, pager, next, jumper"
                :total="40"
              >
              </el-pagination>
            </template>
          </el-main>
          <el-footer>
            <h1>受不了一点，我快嗝屁了!</h1>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import axios from "axios";
import Element from "element-ui";
import router from "@/router";
export default {
  data() {
    return {
      user:"",
      tableData: []
      ,
      searchForm: {
        name: "",
        type: "",
        time: "",
      },
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 4,
    };
  },
  created(){
    this.user=JSON.parse(window.localStorage.getItem("user"));
  },
  mounted() {
    // 在组件挂载时调用后端接口
    this.fetchGames();
  },
  
  methods: {
    onSubmit: function () {
      alert("查询数据");
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    async fetchGames() {
      try {
        
        // 发送GET请求到 GamesController 接口
        const response = await this.$axios.get('/api/games',{
          headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json' // 可根据需要设置其他请求头
      }
        });

        // 更新组件的游戏列表数据
        this.tableData = response.data.data;
      } catch (error) {
        console.error('Error fetching games from backend:', error);
      }
   
  },
  signUp(id) {
      // 发送删除请求
      axios
        .post(`/api/account/${id}`,this.user,{headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json'   
      },})
        .then((res) => {
          // 处理报名成功的逻辑
          const code = res.data.code
          const msg = res.data.msg
          if(code==1){
            console.log("报名成功");
          Element.Message.success("恭喜你报名成功");
          this.fetchGames();
          }else{
            Element.Message.error(msg);
          }
          
        })
        .catch((error) => {
          Element.Message.success("抱歉，报名失败,因为未知原因");
          // 处理删除失败的逻辑
          console.error("报名失败", error);
        });
    },
    moreInfo(id){
      // 在你需要进行操作的地方（比如某个方法）添加下面的代码
    if (localStorage.getItem('GameID')) {
        localStorage.removeItem('GameID'); // 删除已有的GameID键值对
      }
    localStorage.setItem('GameID', id); // 存储新的GameID
    router.push("/gameInfo");
    console.log("获取比赛ID，并展示其详细内容")

    }
  
 

  }
};  
</script>

<style>
#link {
  text-decoration: none;
  color: #1bb9ab8d; /* 设置链接颜色，可以根据需要调整 */
  font-weight: bold; /* 加粗文字 */
  transition: color 0.3s ease; /* 添加过渡效果 */
}
#link:hover {
    color: rgba(0, 252, 235, 0.834)2d; 
  } 
 
</style>