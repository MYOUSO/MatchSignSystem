<template>
   <div>
    <el-container style="height: 850px; border: 1px solid #eee">
      <el-header style="font-size:40;background-color:rgb(238, 241, 246)"
        >报名管理系统-个人中心
<!-- 圆形按钮 -->
<div>
  <div class="buttons">
    <el-button @click="checkMe">修改信息</el-button>
    <el-button @click="showDialog('logout')">退出登录</el-button>
  </div>
    <!-- 修改信息对话框 -->


    <!-- 退出登录对话框 -->
    <el-dialog :visible.sync="logoutDialogVisible" title="退出登录">
      <p>确定要退出登录吗？</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="logout">确定</el-button>
        <el-button @click="cancelLogout">取消</el-button>
      </span>
    </el-dialog>
  </div>

        </el-header >
     
      <el-container>
        <el-menu :default-openeds="['1', '3']">
          <el-submenu index="1">
            <template slot="title"
              ><i class="el-icon-message"></i>用户入口</template
            >
            <el-menu-item index="1-1">
              <router-link to="/games" id="link" >比赛界面</router-link>
            </el-menu-item>
            <el-menu-item index="1-2">
              <router-link to="/account" id="link">个人中心</router-link>
            </el-menu-item>
          </el-submenu>
          
          <el-submenu index="2" v-if="this.user.permission >= 2" >
            <template slot="title"
              ><i class="el-icon-menu"></i>管理员入口</template>
              <el-menu-item index="2-1">
              <router-link to="/administrators-addition" id="link">管理员界面</router-link>
              </el-menu-item>
          </el-submenu>
        </el-menu>
        <el-container>
          <!-- 用户中心 -->
          <el-main>
    <!-- 居中文本 -->
    <div class="center-text">
      <span style="font-size: 24px; font-weight: bold;">欢迎回来!{{user.account}}</span>
      <br>
      <br>
      <br>
    </div>
    
    <!-- 表格 -->
    <span>您参加了以下比赛:<hr></span>
    <el-row type="flex" justify="center">
      <el-col :span="20">
        <el-table :data="tableData" style="width: 120%; border: 1px solid #e0e0e0;" border>
          <el-table-column prop="name" label="比赛名称"></el-table-column>
          <el-table-column prop="introduction" label="比赛简介"></el-table-column>
          <el-table-column label="比赛类型">
            <template slot-scope="scope">
                    {{ scope.row.type == 1 ? "个人赛" : "团体赛" }}
                  </template>
          </el-table-column>
          <el-table-column prop="startTime" label="报名开始时间"></el-table-column>
          <el-table-column prop="endTime" label="报名结束时间"></el-table-column>
          <el-table-column label="比赛状态">
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
                  <el-button type="primary" size="mini" @click="leftGame(scope.row.id)">退赛</el-button> <!-- 退赛 -->
                  <el-button type="info" size="mini" @click="viewGame(scope.row)">查看</el-button>
                </template>
              </el-table-column>
        </el-table>
      </el-col>
    </el-row>
            <!-- 列出已参加的比赛 -->
          </el-main>
        </el-container>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import router from '@/router';
import axios from 'axios'
import Element from "element-ui";
export default {
  data() {
    return {
      tableData: [],
      user:"",
      modifyDialogVisible: false,
      logoutDialogVisible: false,
      editMode: false,
    };
  },
  mounted() {
    // 在组件挂载时调用后端接口
    this.fetchGames();
  },
  created(){ 
    this.user=JSON.parse(window.localStorage.getItem("user"));
      },
  methods: {
    showDialog(type) {
      if (type === 'modify') {
        this.modifyDialogVisible = true;
      } else if (type === 'logout') {
        this.logoutDialogVisible = true;
      }
    },
    logout() {
      // 在这里添加退出登录的逻辑
      this.logoutDialogVisible = false;
      router.push('/login');
      localStorage.clear();
      Element.Message.success("退出成功!")
    },
    cancelLogout() {
      this.logoutDialogVisible = false;
    },
  

    
    toggleEditMode() {
      this.editMode = !this.editMode;
    },
    saveChanges() {
      // Implement logic to save changes
      this.editMode = false; // Disable edit mode after saving changes
    },
    fetchGames() {
      axios
        .get(`/api/account/listSign`,{headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json'   
      },params: {
        id:this.user.id
            }
          })
        .then((response) => {
          // 处理报名成功的逻辑
         this.tableData =response.data.data
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("获取失败", error);
        });
    },
    checkMe(){
      router.push("/info")
    },
    leftGame(id){
      axios.delete(`/api/account/left/${id}`,{headers: 
        {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
        'Content-Type': 'application/json'   
      },
        params:{
          userID:this.user.id,
        }
    })
        .then((response) => {
          // 处理删除成功的逻辑
          console.log("删除成功", response);

          this.fetchGames();

          Element.Message.success("恭喜你，退赛成功");
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("删除失败", error);
        });
    },
  },
};

</script>

<style >
.center-text 
{margin-top: 50px; /* 调整上方文本与顶部的距离 */
  display: flex;
  flex-direction: column;
  align-items: center;
}
.buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 5px; /* 可以根据需要调整距离顶部的距离 */
}

.buttons .el-button {
  margin-left: 10px; /* 按钮之间的间距 */
}


#link {
  text-decoration: none;
  color: #1bb9ab8d; /* 设置链接颜色，可以根据需要调整 */
  font-weight: bold; /* 加粗文字 */
  transition: color 0.3s ease; /* 添加过渡效果 */
}
#link:hover {
    color: rgba(252, 193, 0, 0.834)2d; 
  } 
  .circular-button {
  position: absolute;
  margin-top: 10px;
  right: 20px; /* 距离右侧的距离 */;
  color:antiquewhite;
  background: #767575;
}

</style>