<template>
    <div :xl="6" :lg="7" class="bg-login">

      <!--标题-->
      <el-row type="flex" class="row-bg row-two" justify="center" align="middle">
        <el-col :span="6"></el-col>
        <el-col :span="6">
          <!--标题-->
          <h1 class="title">比赛管理系统</h1>
        </el-col>
        <el-col :span="6"></el-col>
      </el-row>
      <!--form表单-->
      <el-row type="flex" class="row-bg card" justify="center" align="bottom">
        <el-col :span="7" class="login-card">
          <!--loginForm-->
          <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
            <el-form-item label="账户" prop="account" style="width: 380px">
              <el-input v-model="loginForm.account"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" style="width: 380px">
              <el-input type="password" v-model="loginForm.password"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="code" style="width: 380px">
              <el-input v-model="loginForm.code" class="code-input" style="width: 70%;float: left"></el-input>
              <!--验证码图片-->
              <el-image :src="codeImg" class="codeImg"></el-image>
            </el-form-item>
            <el-form-item label="记住密码" prop="remember">
              <el-switch v-model="loginForm.remember"></el-switch>
            </el-form-item>
            <el-form-item class="btn-ground">
              <el-button type="primary" @click="submitForm('loginForm')">立即登陆</el-button>
              <el-button @click="resetForm('loginForm')">重置</el-button>
            </el-form-item>
            <el-form-item class="btn-ground">
              <router-link to="/register">
              <el-button type="primary">立即注册</el-button>
              </router-link>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
import Element from 'element-ui';
import router from "@/router";
import axios from 'axios';
export default {
  data() {
    return {
      // 表单信息
      loginForm: {
        account: '',
        password: '',
        code: '',
        remember: false,
        codeToken: ''
      },
      // 表单验证
      rules: {
        // 设置账户效验规则
        account: [
          {required: true, message: '请输入账户', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符的账户', trigger: 'blur'}
        ],
        // 设置密码效验规则
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 15, message: '长度在 6 到 15 个字符的密码', trigger: 'blur'}
        ],
        // 设置验证码效验规则
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {min: 5, max: 5, message: '长度为 5 个字符', trigger: 'blur'}
        ]
      },
      // 绑定验证码图片
      codeImg: null
    };
  },
  methods: {
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('/api/login', this.loginForm).then(res => {
            // 拿到结果
            let result = res.data.code;
            let message = res.data.msg;
            console.log(res)
            // 判断结果
            if (result==1) {
              
        // 将令牌保存在本地存储中
        localStorage.setItem('user', JSON.stringify(res.data.data));
        axios.defaults.headers.common['Authorization'] = `Bearer ${res.data.data.token}`;

        // 使用 Vue Router 自动跳转到 /account 页面

              /*登陆成功*/
              Element.Message.success("登录成功");
              /*跳转页面*/
              router.push('/account')
            } else {
              /*打印错误信息*/
              Element.Message.error(message);
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 获取验证码方法
    getVerifyCodeImg() {
      axios.get('/captcha', { responseType: 'arraybuffer' })
  .then(response => {
    // 将获取到的验证码图片数据转换成base64格式
    let imageBase64 = btoa(
      new Uint8Array(response.data)
        .reduce((data, byte) => data + String.fromCharCode(byte), '')
    );
    // 将base64格式的验证码图片数据显示在页面上
    document.getElementById('captchaImage').src = 'data:image/jpeg;base64,' + imageBase64;
  })
  .catch(error => {
    console.error('Failed to fetch captcha:', error);
  });

      //*获取验证码
      this.$axios.get('/getVerifyCode').then(res => {
        // 获取验证码key
        this.loginForm.codeToken = res.data.data.codeToken;
        // 获取验证码图片
        this.codeImg = res.data.data.codeImg;
      }) 
    }
  },
  created() {
    // 页面渲染完成后执行获取验证码方法
    this.getVerifyCodeImg();
  }
}
</script>

  
  <style scoped>
  .codeImg {
    /*让验证码图片飘在右边*/
    float: right;
    /*设置一些圆角边框*/
    border-radius: 3px;
    /*设置宽度*/
    width: 26%;
  }
  
  .bg-login {
    height: auto;
    background-color:#ffffff;
    background-size: 300%;
  
  }
  
  .btn-ground {
    text-align: center;
  }
  

  
  .title {
    text-shadow: -3px 3px 1px #5f565e;
    text-align: center;
    margin-top: 60%;
    color: #1a01ff;
    font-size: 40px;
  }
  
  .login-card {
    background-color: #ffffff;
    opacity: 0.9;
    box-shadow: 0 0 20px #ffffff;
    border-radius: 10px;
    padding: 40px 40px 30px 15px;
    width: auto;
  }
  </style>
  