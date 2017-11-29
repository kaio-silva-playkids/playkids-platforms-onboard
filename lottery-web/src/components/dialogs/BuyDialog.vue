<template>
  <el-dialog
    title='Buy Ticket to Lottery'
    :visible.sync="control"
    width="25%">
    <span>You are buying one ticket by the price of: {{lottery.price}} on Lottery #{{lottery.id}}, do you confirm ?</span>
    <span slot="footer" class="dialog-footer">
                <el-button @click="close()">No</el-button>
                <el-button type="primary" @click="confirm(lottery)">Yes</el-button>
              </span>
  </el-dialog>
</template>

<script>

  export default {
    name: 'BuyDialog',
    components: {},
    props: ['lottery', 'control', 'confirm', 'close'],
    data() {
      return {
      };
    },
  };
</script>
