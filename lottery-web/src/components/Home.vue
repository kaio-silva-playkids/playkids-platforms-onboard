<template>
  <el-container class="home">
    <el-header>
      <el-menu :default-active="active" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-submenu index="1">
          <template slot="title">Lotteries</template>
          <el-menu-item index="1-1">Available</el-menu-item>
          <el-menu-item index="1-2">Participating</el-menu-item>
          <el-menu-item index="1-3">History</el-menu-item>
        </el-submenu>
        <el-menu-item index="3"><a href="https://github.com/kaio-silva-playkids/playkids-platforms-onboard" target="_blank">Github</a></el-menu-item>

        <li class="el-submenu profile">
          <div class="el-submenu__title"> Profile: {{user.username}} | credits: {{user.credit}}</div>
        </li>
      </el-menu>
      <div class="line"></div>
    </el-header>
    <el-main>

      <el-row v-loading="loading">
        <el-col :span="6" v-for="lottery in lotteries">
          <el-card :body-style="{ padding: '0px' }">
            <div class="card-image">
              <div class="overlay price">Price: {{lottery.price}}</div>
              <div class="overlay award">Award: {{lottery.award}}</div>
              <img src="../assets/card.png" class="image">
            </div>
            <div style="padding: 14px;">
              <h3>Lottery #{{lottery.id}}</h3>
              <div class="bottom clearfix">
                <time class="time">{{ lottery.draw | formatDate }}</time>
                <el-button type="text" class="button">Buy Ticket</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

    </el-main>
  </el-container>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      active: '1-1',
      loading: false,
      lotteries: null,
      user: null,
      currentDate: new Date(),
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    loadProfile() {
      return this.$userService.profile().then((response) => {
        this.user = response;
        console.log(this.user);
      });
    },
    search() {
      this.loading = true;

      return this.$lotteryService.search().then((response) => {
        this.loading = false;
        this.lotteries = response;
      })
      .catch((error) => {
        this.loading = false;

        this.$message.error(`Failed to get lotteries: ${error.description}`);
      });
    },
  },
  date(draw) {
    return this.$moment.utc(draw).format('DD/MM/YYYY HH:mm:ss');
  },
  created() {
    this.debouncedProfile =
      this.$lodash.debounce(this.loadProfile, 500, { leading: true, trailing: true });

    this.debouncedProfile();

    this.debouncedSearch =
      this.$lodash.debounce(this.search, 500, { leading: true, trailing: true });

    this.debouncedSearch();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

  .el-container {
    height: 100vh;
  }

  .el-main {
    height: 100%;
  }

  .el-card {
    width: 300px;
  }

  .card-image {
    position: relative;
  }

  .overlay {
    position: absolute;
    z-index: 1000;
    padding: 5px;
    color: #FFFFFF;
    font-weight: bold;
  }

  .price {
    float: right;
    right: 0;
    top: 0;
    background-color: steelblue;
  }

  .award {
    float: left;
    left: 0;
    top: 0;
    background-color: lightseagreen;
  }

  .profile {
    float: right;
  }

  .time {
    font-size: 14px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

</style>
