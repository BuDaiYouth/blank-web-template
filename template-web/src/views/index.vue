<template>
  <div>
    <el-row style="margin-top: 10%">
      <h1 style="width: 100%; text-align: center;color: deepskyblue">
        <!-- Logo name -->
      </h1>
    </el-row>
    <el-row style="margin-top: 50px">
      <el-main class="login-main">
        <el-form
            ref="loginForm"
            :rules="rules"
            :model="loginForm"
            class="login-form"
        >
          <el-form-item label="账&nbsp;&nbsp号:" prop="username">
            <el-input
                v-model="loginForm.username"
                placeholder="admin"
            />
          </el-form-item>
          <el-form-item label="密&nbsp;&nbsp码:" prop="password">
            <el-input
                type="password"
                v-model="loginForm.password"
                placeholder="123456"
            />
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                style="width: 100%; margin-top: 10px"
                @click="login"
            >登录
            </el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-row>
  </div>
</template>

<script>
import {login} from "@/api/authApi";
import {setToken} from "@/util/TokenUtil";
import {Encrypt} from "@/util/AES";

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: '用户名不能为空', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loginForm.password = Encrypt(this.loginForm.password)
          login(this.loginForm).then(res => {
            const data = res.data
            if (data.data) {
              setToken(res.headers['token'])
              this.$router.push("/home")
            } else {
              this.$notify.error({
                title: '登录失败',
                message: '账号密码不正确，请重试.'
              });
            }
          })
        } else {
          // 表单验证失败
          this.$message.error('表单验证失败')
        }
      })
    }
  }
}
</script>

<style scoped>
.login-main {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%; /* 让 el-main 高度充满父容器，这样 Flex 布局才会生效 */
}

.login-form {
  padding: 40px 30px 10px 30px;
  border-radius: 20px;
  background-color: #B6DDFA;
}
</style>
