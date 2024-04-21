<template>
  <div style="text-align: center">
    <h1>Hello World!</h1>
    <el-button type="primary" @click="quit">退出</el-button>
  </div>
</template>

<script>

import {clearToken, clearUser, getUser} from "@/util/TokenUtil";

export default {
  data() {
    return {
      userKey: ''
    }
  },
  mounted() {
    this.userKey = getUser()
    this.$message.success(this.userKey)
  },
  methods: {
    quit() {
      clearToken()
      clearUser()
      this.$router.push("/")
    }
  }
}
</script>

<style scoped>
</style>