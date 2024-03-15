<template>
    <div class="container">

    <form class="content" @submit.prevent="submitForm" v-if="showForm">
        <h2>修改信息</h2>
      <div class="form-group">
        <label for="account">>账号:</label>
        <span>{{this.user.account}}</span>
      </div>
      <div class="form-group">
        <label for="phone">手机号:</label>
        <input type="tel" id="phone" v-model="phone" >
      </div>
      <div class="form-group">
        <label for="email">邮箱:</label>
        <input type="email" id="email" v-model="email" >
      </div>
      <div class="form-group">
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <div class="form-group">
        <label for="newPassword">新密码:</label>
        <input type="password" id="newPassword" v-model="newPassword" required>
      </div>
      <el-button type="primary" @click="submitForm">修改信息</el-button>
          <el-button type="primary" @click="backInfo">返回上级</el-button>
    </form>
      <div class="content" v-if="!showForm">
        <h2>用户信息</h2>
        <div class="info">
          <label><hr>>账号:</label>
          <span>{{ this.user.account }}</span>
        </div>
        <div class="info">
          <label>>手机号:</label>
          <span>{{ this.user.phone }}</span>
        </div>
        <div class="info">
          <label>>邮箱:</label>
          <span>{{ this.user.email }}</span>
        </div>
        <div class="info">
          <label>>权限等级:</label>
          <span>{{ permissionText }}</span><hr>
        </div>
        <div class="button-container">
          <el-button type="primary" @click="modifyInformation">修改信息</el-button>
          <el-button type="primary" @click="back">返回上级</el-button>
        </div>
      </div>
    </div>
    
  </template>
  
  <script>
import router from '@/router';
import axios from 'axios';
import Element from "element-ui";
  export default {
    data() {
      return {
    showForm:false,
    user:"",
    account:"",
    phone:"",
    email:"",
    password:"",
    newPassword:""
      };
    },
    created(){
        this.user=JSON.parse(window.localStorage.getItem("user"))
      },
      computed: {
    permissionText() {
      return this.user.permission === 1 ? '普通用户' : '管理员';
    }
  },
    methods: {
        backInfo(){
            this.showForm=false
        },
    submitForm() {
      // 在这里执行表单提交的逻辑
      console.log('表单已提交');
      console.log('账号:', this.account);
      console.log('手机号:', this.phone);
      console.log('邮箱:', this.email);
      console.log('密码:', this.password);
      console.log('新密码:', this.newPassword);
      axios
        .put(`/api/account`,  {
            account:this.user.account,
            phone:this.phone,
            email:this.email,
            password:this.password,
            newPassword:this.newPassword
          },{
          headers: {
            'Authorization': `Bearer ${this.user.token}`,
            "Content-Type": "application/json" },
         
        })
        .then((res) => {
        const code=res.data.code;
        const msg=res.data.msg;
          // 处理删除成功的逻辑
          if(code==1){
            console.log("修改成功", res);
          Element.Message.success("恭喜你，重新修改成功");
          }else{
            Element.Message.error(msg)
          }
          
        })
        .catch((error) => {
          // 处理删除失败的逻辑
          console.error("修改失败", error);
        });
      this.showForm = false;
    },
        back(){
            router.push("/account")
        },
      modifyInformation() {
        this.showForm=true,
        // 添加修改信息的逻辑代码
        console.log('修改信息按钮被点击');
        // 这里可以添加打开修改信息表单或其他操作的代码
      }
    }
  };
  </script>
  
  <style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f2f2f2;
}

.content {
  width: 400px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

h2 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.info {
  margin-bottom: 15px;
}

.info label {
  font-weight: bold;
  color: #555;
}

.button-container {
  text-align: center;
  margin-top: 20px;
}

.el-button {
  background-color: #409eff;
  border-color: #409eff;
}
.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input[type="text"],
input[type="tel"],
input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
  </style>
  