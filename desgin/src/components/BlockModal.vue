<template>
    <div class="modal-bg" v-if="blockModal">
        <div id="login-box" class="modal" >
            <div style="width:100%; height:20%; background:#ff0d19; border-radius:4px 4px 0 0;display:flex; align-items:center; justify-content: center;">
                <p style="text-align:center; color:white; font-weight:bold;">경고!</p>
            </div>
            <p style="font-size:14px;">본인의 질문에는 답변할 수 없습니다.</p>
            <div style="width:100%; height:25%; background:#ff0d19; border-radius: 0 0 4px 4px;display:flex; align-items:center; justify-content: center;">
                <button class="social close" @click="closeModal">Close</button>
            </div>
            
        </div>
    </div>
</template>

<script>
export default{
    name: 'BlockModal',
    data() {
        return {
        
        }
    },
    props: {
        blockModal: Boolean,
    },
    methods: {
        closeModal() {
            this.$emit('toggleBlock');
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