<template>
  <div>
    <el-container class="home">
      <el-header>
        <el-menu :default-active="active" class="el-menu-demo" mode="horizontal" @select="select">
          <el-submenu index="1">
            <template slot="title">Lotteries</template>
            <el-menu-item index="1-1">Available</el-menu-item>
            <el-menu-item index="1-2">Participating</el-menu-item>
            <el-menu-item index="1-3">Won</el-menu-item>
          </el-submenu>
          <el-menu-item index="3"><a href="https://github.com/kaio-silva-playkids/playkids-platforms-onboard" target="_blank">Github</a></el-menu-item>
          <el-submenu index="4" class="profile" v-if="user">
            <template slot="title">Profile: {{user.username}} | credit: {{user.credit}}</template>
            <el-menu-item index="4-1" v-on:click="logout()">Logout</el-menu-item>
          </el-submenu>
        </el-menu>
        <div class="line"></div>
      </el-header>
      <el-main>

        <h2>{{title}}</h2>

        <div v-if="!lotteries || lotteries.length === 0">
          No results.
        </div>

        <el-row v-loading="loading">
            <el-col :span="6" v-for="lottery in lotteries">
            <el-card :body-style="{ padding: '0px' }">
              <div class="card-image">
                <div class="overlay price">Price: {{lottery.price}}</div>
                <div class="overlay award">Award: {{lottery.award}}</div>
                <div v-if="active == '1-3'" class="overlay won">
                  <span>{{lottery.award}}</span>
                  <span>CREDITS</span>
                </div>
                <img src="../assets/card.png" class="image">
              </div>
              <h3>Lottery #{{lottery.id}}</h3>
              <div style="padding: 12px;">
                <div class="bottom">
                  <time class="time">{{ lottery.draw | formatDate }}</time>
                  <div v-if="active == '1-1'">
                    <el-button v-if="enableBuy" type="text" class="button" @click="dialog(true, lottery)">Buy Ticket</el-button>
                  </div>
                  <div v-else-if="active == '1-2'">
                    <a class="card-label">Participating!</a>
                  </div>
                  <div v-else-if="active == '1-3'">
                    <a class="card-label">You won {{lottery.award}} credits!</a>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

      </el-main>
    </el-container>

    <buy-dialog v-if="selectedLottery" v-bind:lottery="selectedLottery"
                v-bind:control="control" v-bind:confirm="buyTicket" v-bind:close="dialog"></buy-dialog>
  </div>
</template>

<script>
  import moment from 'moment';

  import BuyDialog from '../components/dialogs/BuyDialog.vue';

  const titles = ['Available', 'Participating', 'Won'];

  export default {
    name: 'Home',
    components: { BuyDialog },
    data() {
      return {
        active: '1-1',
        title: titles[0],
        loading: false,
        enableBuy: true,
        control: false,
        selectedLottery: null,
        lotteries: null,
        user: null,
        currentDate: new Date(),
      };
    },
    methods: {
      select(key) {
        this.search().then(() => {
          this.active = key;

          switch (key) {
            case '1-3':
              this.title = titles[2];
              this.won();
              this.enableBuy = false;
              break;
            case '1-2' :
              this.title = titles[1];
              this.participating();
              this.enableBuy = false;
              break;
            case '1-1' :
            default:
              this.title = titles[0];
              this.available();
              this.enableBuy = null;
              break;
          }
        });
      },
      dialog(flag, lottery) {
        this.control = flag;
        this.selectedLottery = lottery;
      },
      profile() {
        return this.$userService.profile().then((response) => {
          this.user = response;
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
      available() {
        this.lotteries = this.$lodash.differenceBy(this.lotteries, this.user.tickets
          .map(ticket => ticket.lottery), 'id')
          .filter((lottery) => (lottery.winner === null && moment(lottery.draw).isAfter(moment())));
      },
      participating() {
        this.lotteries = this.user.tickets.map(ticket => ticket.lottery)
          .filter((lottery) => moment(lottery.draw).isAfter(moment()));
      },
      won() {
        this.lotteries = this.lotteries.filter((lottery) =>
          (lottery.winner !== null && lottery.winner.id === this.user.id));
      },
      buyTicket(lottery) {
        this.control = false;
        if (this.user.credit >= lottery.price) {
          this.loading = true;
          return this.$ticketService.buy(lottery).then((response) => {
            this.user = response;
            this.select(this.active);
          }).catch((error) => {
            this.loading = false;

            this.$message.error(`Failed to get lotteries: ${error.description}`);
          });
        }
        return null;
      },
      logout() {
        this.$authService.logout();
        this.$router.push('login');
      },
    },
    created() {
      this.debouncedProfile =
        this.$lodash.debounce(this.profile, 500, { leading: true, trailing: true });

      this.debouncedSearch =
        this.$lodash.debounce(this.search, 500, { leading: true, trailing: true });

      this.debouncedProfile().then(() => {
        this.debouncedSearch().then(() => {
          this.available();
        });
      });
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

  .el-submenu {
    z-index: 100;
  }

  .el-card {
    margin: 20px;
  }

  .card-image {
    position: relative;
  }

  .overlay {
    position: absolute;
    z-index: 50;
    padding: 5px;
    color: #FFFFFF;
    font-weight: bold;
    text-align: center;
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

  .won {
    float: right;
    text-align: center;
    width: 100%;
    right: 0;
    bottom: 0;
    font-size: 4em;
  }

  .profile {
    float: right;
  }

  .time {
    font-size: 14px;
    color: #999;
    float: left;
  }

  .bottom {
    line-height: 16px;
    margin-bottom: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .card-label {
    color: #409EFF;
    font-weight: 600;
    font-size: 14px;
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

</style>
