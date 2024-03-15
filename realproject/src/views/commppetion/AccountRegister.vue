<template>
    <div :xl="6" :lg="7" class="bg-register">

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
        <el-col :span="8" class="register-card">
          <!--registerForm-->
          <el-form :model="registerForm" :rules="rules" ref="registerForm" label-width="21%" class="registerForm">
            <el-form-item label="账户" prop="account" style="width: 380px">
              <el-input v-model="registerForm.account" placeholder="Account"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email" style="width: 380px">
              <el-input v-model="registerForm.email" placeholder="Email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password" style="width: 380px" >
              <el-input  v-model="registerForm.password" placeholder="Password" :type="showPassword ? 'text' : 'password'" > 
                <el-button slot="append" @click="togglePasswordVisibility"></el-button>
              </el-input>
            </el-form-item>
            <el-form-item label="再次密码" prop="doublePassword" style="width: 380px">
              <el-input  v-model="registerForm.doublePassword" placeholder="confirmPassword" :type="showDoublePassword ? 'text' : 'password'" >
                <el-button slot="append" @click="toggleDoublePasswordVisibility"></el-button>
              </el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone" style="width: 380px">
              <el-input  v-model="registerForm.phone" placeholder="Phone"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="code" style="width: 380px">
              <el-input v-model="registerForm.code" class="code-input" style="width: 70%;float: left"></el-input>
              <!--验证码图片-->
              <el-image :src="codeImg" class="codeImg"></el-image>
            </el-form-item>
            <el-form-item class="btn-ground">
              <el-button type="primary" @click="submitForm('registerForm')">注册</el-button>
            </el-form-item>
            <el-form-item class="btn-ground">
              <router-link to="/login">
              <el-button type="primary">立即登录</el-button>
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
export default {
  data() {
    return {
      // 表单信息
      registerForm: {
        // 账户数据
        account: '',
        // 密码数据
        password: '',
        // 第二次密码数据
        doublePassword: '',
        //邮箱数据
        email:'',
        //手机号码
        phone:'',
        // 验证码数据
        code: '',
        // 验证码的key，因为前后端分离，这里验证码不能由后台存入session，所以交给vue状态管理
        codeToken: ''
      },
      showPassword: false,
      showDoublePassword:false,
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
        doublePassword: [
         { required: true, message: '请再次输入密码', trigger: 'blur' },
          { message: '请再次输入密码',trigger: ['blur', 'change'] }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入有效的邮箱地址XXXX@XXXXX', trigger: ['blur', 'change'] }
        ],

        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: '请输入有效的手机号码', trigger: ['blur', 'change'] }
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
    toggleDoublePasswordVisibility() {
      console.log('toggleDoublePasswordVisibility called');
      this.showDoublePassword = !this.showDoublePassword;
    },
    togglePasswordVisibility() {
      console.log('togglePasswordVisibility called');
      this.showPassword = !this.showPassword;
    },

    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('/api/register', this.registerForm).then(res => {
            // 拿到结果
            let result = res.data.code;
            let message = res.data.msg;
            // 判断结果
            if (result==1) {
              /*注册成功*/
              Element.Message.success("恭喜你，注册成功，快去登录吧");
              /*跳转页面*/
              router.push('/login')
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
   
    // 获取验证码方法
    getVerifyCodeImg() {
      /*获取验证码*/
      this.$axios.get('/getVerifyCode').then(res => {
        // 获取验证码key
        this.registerForm.codeToken = res.data.data.codeToken;
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
  
  .bg-register {
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
  
  .register-card {
    background-color: #ffffff;
    opacity: 0.9;
    box-shadow: 0 0 20px #ffffff;
    border-radius: 10px;
    padding: 40px 40px 30px 15px;
    width: auto;
  }
  </style>
  