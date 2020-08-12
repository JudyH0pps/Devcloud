<template>
    <div class="detailanswer">
        <h1 class="q-title">A.</h1>
        <div class="leftline">
            <div class="q-info">
                <div class="profile">
                    <img :src="answer.user.imageUrl" class="profile_img">
                    <p>{{ answer.user.name }}</p>
                </div>
                <p>{{ answer.updated_at }}</p>
            </div>
        </div>
        <p class="q-content">{{ answer.content }}</p>
        <span style="float: right; margin: 0 2px; cursor: pointer" v-if="parseInt($cookie.get('user_id')) === answer.user.id" @click="deleteAnswer({ answer_id: answer.id, question_id: $route.params.question_id })">삭제</span>
        <span @click="$router.push({ name : 'EditAnswer', params : { question_id : $route.params.question_id, answer_id: answer.id } })" style="float: right; margin: 0 2px; cursor: pointer;" v-if="parseInt($cookie.get('user_id')) === answer.user.id">수정</span>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
    name: 'DetailAnswer',
    data() {
        return {
        }
    },
    props: {
        answer: Object,
    },
    methods: {
        ...mapActions('answer', ['deleteAnswer'])
    }
    // created() {
    //     console.log(this.answer);
    // }
}
</script>

<style scoped>
.q-title {
  padding: 10px 0;
  border-bottom: 1px solid #eeeeee;
}
.tags {
    text-align: right;
}
.leftline{
    border-left: 4px solid #eeeeee;
    padding-left: 15px;
}
.q-info {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px 0;
}
.q-info p {
    font-size: 14px;
    color: gray;
}
.q-content {
    padding: 10px 0;
}
.profile {
    display: flex;
    flex-direction: row;
    align-items: center;
}
.profile_img {
    border-radius: 50%;
    height: 40px;
    margin-right: 10px;
}
</style>