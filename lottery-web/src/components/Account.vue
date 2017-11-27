<template>
  <div>
    <h1>New Account</h1>
    <el-row>
      <el-col :span="12" :offset="6"><div class="grid-content bg-purple-light">
        <el-form ref="form" :model="form" :rules="rules">
          <el-form-item label="Username" prop="username">
            <el-input v-model="form.username" auto-complete="off" placeholder="Enter your username"></el-input>
          </el-form-item>

          <el-form-item label="Email" prop="email">
            <el-input v-model="form.email" type="email" auto-complete="off" placeholder="Enter your email">
            </el-input>
          </el-form-item>

          <el-form-item label="Password" prop="password">
            <el-input v-model="form.password" @keyup.enter.native="createAccount('form')" type="password" auto-complete="off"
                      placeholder="Enter your password">
            </el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="default" v-on:click="createAccount('form')">Create new Account</el-button>
          </el-form-item>
        </el-form>
      </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  function createAccount() {
    this.$accountService.create(this.form).then((response) => {
      if (response) {
        this.$router.push('home');
        this.$emit('created', true);
      } else {
        this.$message.error('Failed to create account.');
      }
    });
  }
  export default {
    name: 'account',
    data() {
      return {
        loading: false,
        form: {
          username: '',
          email: '',
          password: '',
        },
        rules: {
          username: [
            { required: true, message: 'Username is mandatory', trigger: 'blur' },
          ],
          email: [
            { required: true, message: 'Email is mandatory', trigger: 'blur' },
          ],
          password: [
            { required: true, message: 'Password is mandatory', trigger: 'blur' },
          ],
        },
      };
    },
    methods: {
      createAccount(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            createAccount.call(this);
          } else {
            this.$message.error('Invalid field username, email or password');
          }
        });
      },
    },
  };
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
