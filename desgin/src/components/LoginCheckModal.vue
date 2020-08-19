<template>
    <div class="modal-bg" v-if="loginCheck">
        <div id="login-box" class="modal" >
            <div style="width:100%; height:20%; background:skyblue; border-radius:4px 4px 0 0;display:flex; align-items:center; justify-content: center;">
                <p style="text-align:center; color:white; font-weight:bold;">LOGIN</p>
            </div>
            <p>로그인이 필요한 서비스입니다.</p>
            <p>로그인 하시겠습니까?</p>
            <button class="yes" @click="switchModal">Yes</button>
            <div style="width:100%; height:25%; background:skyblue; border-radius: 0 0 4px 4px;display:flex; align-items:center; justify-content: center;">
                <button class="social close" @click="closeModal">Close</button>
            </div>
            
        </div>
    </div>
  <!-- <div class="modal-bg" v-if="loginCheck">
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
  </div> -->
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
    },
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
    justify-content: center;
    align-items: center;
    z-index: 999;
}
.modal {
    /* box-shadow: 0px 3px 5px rgba(0,0,0,.5); */
    background: white;
    width: 550px;
    height: 300px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: column;
    z-index: 1000;
    border-radius: 4px;
    /* border: 1px solid #ccc; */
    /* padding: 10px; */
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
    justify-content: space-between;
    align-items: center;
}
.modal-buttons > button:focus {
    outline: none;
}
button {
    /* margin-bottom: 20px; */
    width: 180px;
    height: 36px;
    border: none;
    border-radius: 2px;
    color: #fff;
    font-family: sans-serif;
    font-weight: 500;
    transition: 0.2s ease;
    cursor: pointer;
    z-index: 1000;
    background: #000;
}
.yes {
    background: rgb(71, 187, 67)
}
</style>