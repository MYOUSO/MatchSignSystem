<template>
  

  <div class="container">
    <el-container>
      <el-aside width="200px" style="background-color: #f0f2f5; padding: 20px;">
        <div style="margin-bottom: 20px;">
          <el-button type="primary" @click="openForm" style="width: 100%;"  v-if="this.user.permission >= 2">编辑比赛</el-button>
        </div>
        <div>
          <el-button type="default" @click="handleBack" style="width: 100%;">返回首页</el-button>
        </div>
      </el-aside>
      
      <el-main style="margin-left: 220px; padding: 20px;">
          <!-- Form Dialog -->
          <el-dialog :visible.sync="formVisible" title="编辑比赛" width="50%">
          <el-form ref="gameForm" :model="form" label-width="120px">
            <el-form-item label="主题" prop="topic">
              <el-input v-model="form.topic" placeholder="请输入比赛主题"></el-input>
            </el-form-item>
            <el-form-item label="开始时间" prop="beginTime">
              <el-date-picker v-model="form.beginTime" type="datetime" placeholder="请选择开始时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker v-model="form.endTime" type="datetime" placeholder="请选择结束时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="简介" prop="information">
              <el-input type="textarea" v-model="form.information" autosize placeholder="请输入比赛简介"></el-input>
            </el-form-item>
            <el-form-item label="管理人员" prop="manager">
              <el-input v-model="form.manager" placeholder="请输入管理人员"></el-input>
            </el-form-item>
            <el-form-item label="举办地点" prop="place">
              <el-input v-model="form.place" placeholder="请输入举办地点"></el-input>
            </el-form-item>
            <el-form-item label="更新信息" prop="updateInfo">
              <span style="font-size: 12px; color: #999;">最近一次更新时间:{{form.updateTime}}/最近一次更新人:{{form.updatePerson}}</span>
            </el-form-item>
          </el-form>
          
          <span slot="footer" class="dialog-footer">
            <el-button @click="formVisible = false">取消</el-button>
            <el-button type="primary" @click="submitForm">确定</el-button>
          </span>
        </el-dialog>
        <div class="custom-paragraph">
          <span class="custom-heading">比赛名称:</span><br>
          <span class="custom-text">{{game.name}}</span>
        </div>
        <div class="custom-paragraph">
          <span class="custom-heading">比赛简介:</span><br>
          <span class="custom-text">{{game.introduction}}</span>
        </div>
        <div class="custom-paragraph">
          <span class="custom-heading">比赛报名状态:</span><br>
          <span class="custom-text">
      <template v-if="game.state === 0">
        <span class="green">■未满■</span>
      </template>
      <template v-else-if="game.state === 1">
        <span class="red">■已满■</span>
      </template>
      <template v-else-if="game.state === 2">
        <span class="dark_red">■截止■</span>
      </template>
      <template v-else-if="game.state === 3">
        <span class="aqua">■未开始■</span>
      </template>
    </span>
        </div>
        <div class="custom-paragraph">
          <span class="custom-heading">比赛参加人数/上限人数:</span><br>
          <span class="custom-text">{{game.signCount}}/{{game.count}}</span>
        </div>
        <div class="custom-paragraph">
          <span class="custom-heading">比赛具体信息:</span><br>
          <span class="custom-text">>比赛举办方:{{gameInfo.manager}}</span><br>
          <span class="custom-text">>比赛主题:{{gameInfo.topic}}</span><br>
          <span class="custom-text">>比赛具体时间安排:</span><br>
          <span class="custom-text">>报名开始时间:{{game.startTime}}</span><br>
          <span class="custom-text">>报名结束时间:{{game.endTime}}</span><br>
          <span class="line">---------------我是一条分割线---------------</span><br>
          <span class="custom-text">>开始时间:{{gameInfo.beginTime}}</span><br>
          <span class="custom-text">>结束时间:{{gameInfo.endTime}}</span><br>
          <span class="line">---------------我是一条分割线---------------</span><br>
          <span class="custom-text">>比赛举办地点:{{gameInfo.place}}</span><br>
        </div>
        <div class="custom-paragraph">
          <span class="custom-heading">比赛详细介绍</span><br>
          <span class="custom-text">{{gameInfo.information}}</span>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>

import axios from 'axios';
import router from "@/router";
import Element from "element-ui";
export default {
  data() {
    return {
      formVisible: false,
      form: {
        gameID:"",
        topic: '',
        beginTime: null,
        endTime: null,
        information: '',
        manager: '',
        place: '',
        updateTime:null,
        updatePerson:null

      },
      user:"",
      id:"",
      game:"",
      gameInfo:""
    };
  },
  mounted() {
    this.id = localStorage.getItem('GameID');
    this.form.gameID=this.id,
    console.log('Stored GameID:', this.id);
    // 在组件挂载时调用后端接口
    this.fetchGames(this.id);
  },
  created(){ 
  
    this.user=JSON.parse(window.localStorage.getItem("user"));
      },
  methods: {
    openForm() {
      this.form.topic= this.gameInfo.topic,
      this.form.beginTime= this.gameInfo.beginTime,
      this.form.endTime= this.gameInfo.endTime,
      this.form.information= this.gameInfo.information,
      this.form.manager= this.gameInfo.manager,
      this.form.place= this.gameInfo.place,
      this.form.updateTime=this.gameInfo.updateTime,
      this.form.updatePerson =this.gameInfo.updatePerson
      this.formVisible = true;
    },
    submitForm() {
      // You can add your logic here to handle form submission, show confirmation dialog, etc.
      this.$confirm('确定更改比赛信息吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.form.updatePerson=this.user.account;
        const jsonEditData = JSON.stringify(this.form);
        axios
        .put(`/api/games/gameInfo`, jsonEditData, {
          headers: {
            'Authorization': `Bearer ${this.user.token}`,
            "Content-Type": "application/json" },
        })
        .then((response) => {
          // 处理删除成功的逻辑
          console.log("编辑成功", response);

          this.fetchGames(this.id);

          Element.Message.success("恭喜你，编辑成功");

        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("编辑失败", error);
        });
        // Handle form submission or update here
        this.formVisible = false;
      }).catch(() => {
        // Do nothing if Cancel is clicked
      });
    },
    handleBack() {
    localStorage.removeItem('GameID'); // 删除已有的GameID键值对
    router.push("/games");
    },
  



    
    fetchGames(id) {
      axios
        .get(`/api/games/gameInfo/${id}`,{headers: {
        // 动态设置请求头，使用用户对象中的 token
        'Authorization': `Bearer ${this.user.token}`,
      }
          })
        .then((response) => {
          // 处理报名成功的逻辑
         this.code =response.data.code
         this.game =response.data.data.game,
         this.gameInfo=response.data.data.gameInfo
         
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("获取失败", error);
        });
    },

  },
};

</script>








<style>

.red{
  color:red
}
.green{
  color:greenyellow
}
.dark_red{
  color:darkred
}
.aqua{
  color:aqua
}
.line{
  color:rgb(139, 0, 245)
}
.container {
    width: 80%;
    margin: 0 auto;
    padding: 20px;
    text-align: center;
}

.custom-paragraph {
    font-size: 16px;
    line-height: 1.6;
    margin-bottom: 10px;
}

.custom-heading {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 5px;
}

.custom-text {
    font-size: 16px;
    color: #555;
    margin-left: 20px; /* Adding left margin for alignment */
}
.el-header,
.el-main {
    background-color: #ffffff;
    color: #333;
    text-align: center;
    line-height: 60px;
}

.el-main {
    line-height: 1.6;
}

.el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
}

body > .el-container {
    margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
    line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
    line-height: 320px;
}
</style>
