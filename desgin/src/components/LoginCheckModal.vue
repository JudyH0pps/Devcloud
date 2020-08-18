<template>
  <div class="modal-bg" v-if="loginCheck">
      <div class="modal">
        <div class="modal-header">
            <h3>로그인이 필요한 기능입니다.</h3>
            <h3>로그인하시겠습니까?</h3>
        </div>
        <div class="modal-buttons">
            <button @click="switchModal">예</button>
            <button @click="closeModal">아니오</button>
        </div>
      </div>
  </div>
</template>

<script>
export default {
    name: 'LoginCheckModal',
    props: {
        loginCheck: Boolean,
    },
    methods: {
        closeModal() {
            this.$emit('closeModal')
        },
        switchModal() {
            this.$emit('switchModal')
        },
    }
}
</script>

<style scoped>
.modal-bg{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100vh;
    background: rgba(0,0,0,0.5);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    z-index: 999;
}
.modal {
    background: white;
    width: 50%;
    height: 50%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    z-index: 1000;
}
.modal-header {
    text-align: center;
}
.modal-header > h3 {
    margin: 10px 0;
}
.modal-buttons {
    width: 50%;
    display: flex;
    justify-content: space-around;
    align-items: center;
}
</style>