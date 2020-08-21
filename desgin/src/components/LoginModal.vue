<template>
    <div class="modal-bg" v-if="loginModalOn">
        <div id="login-box" class="modal" >
            <div style="width:100%; height:20%; background:skyblue; border-radius:4px 4px 0 0;display:flex; align-items:center; justify-content: center;">
                <p style="text-align:center; color:white; font-weight:bold;">LOGIN</p>
            </div>
            <img @click="googleLogin" class="social google g-logo" src="../assets/btn_google_signin_dark_normal_web@2x.png" alt="g-logo">
            <p style="font-size:14px;">Devcloud의 서비스는 구글 계정으로 편리하게 이용하실 수 있습니다.</p>
            <div style="width:100%; height:25%; background:skyblue; border-radius: 0 0 4px 4px;display:flex; align-items:center; justify-content: center;">
                <button class="social close" @click="closeModal">Close</button>
            </div>
            
        </div>
    </div>
</template>

<script>
export default{
    name: 'LoginModal',
    data() {
        return {
        
        }
    },
    props: {
        loginModalOn: Boolean,
    },
    methods: {
        closeModal() {
            this.$emit('toggleModal');
        },
        googleLogin() {
            this.$emit('googleLogin');
        }
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
button {
    margin-left: auto;
    margin-right: auto;
}
button.social {
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
}
img.social,
.google {
    width: 180px;
}
.social{
    cursor: pointer;
}
.close {
    background: #000;
}
/* .g-logo {
    height: 100%;
} */
</style>