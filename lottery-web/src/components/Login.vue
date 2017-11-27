<template>
  <div>
    <h1>Lottery</h1>
    <el-row>
      <el-col :span="8" :offset="8"><div class="grid-content bg-purple-light">
        <el-form ref="form" :model="form" :rules="rules">
          <el-form-item label="Username" prop="login">
            <el-input v-model="form.login" @keyup.enter.native="signIn('form')"
                      placeholder="Enter your username/email"></el-input>
          </el-form-item>

          <el-form-item label="Password" prop="password">
            <el-input v-model="form.password" @keyup.enter.native="signIn('form')" type="password" auto-complete="off"
                      placeholder="Enter your password">
            </el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" v-on:click="signIn('form')">Sign In</el-button>
            <router-link :to="{name: 'account'}">
              <el-button type="default">Create new Account</el-button>
            </router-link>
          </el-form-item>
        </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
function authenticate() {
  this.loading = true;
  this.$authService.signIn(this.form.login, this.form.password).then((response) => {
    if (response) {
      this.$router.push('/');
      this.$emit('authenticate', true);
    } else {
      this.$message.error('Login failed.');
    }
    this.loading = false;
  });
}
export default {
  name: 'login',
  data() {
    return {
      loading: false,
      form: {
        login: '',
        password: '',
      },
      rules: {
        login: [
          { required: true, message: 'Username/email is mandatory', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Password is mandatory', trigger: 'blur' },
        ],
      },
    };
  },
  methods: {
    signIn(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          authenticate.call(this);
        } else {
          this.$message.error('Invalid field username and/or password');
        }
      });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
