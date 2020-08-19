<template>
    <div class="backImg-jay">
        <div class="container-jay">
            <div style="display:flex; flex-direction:column; justify-content:space-around;">
                <span style="padding:0;">{{questionAllCnt}}</span>
                <h3 style="padding:0;">전체 질문</h3>
            </div>
            <div style="display:flex; flex-direction:column; justify-content:space-around;">
                <span style="padding:0;">{{answerAllCnt}}</span>
                <h3 style="padding:0;">전체 답변</h3>
            </div>
            <div style="display:flex; flex-direction:column; justify-content:space-around;">
                <span style="padding:0;">{{questionCnt}}</span>
                <h3 style="padding:0;">오늘의 질문</h3>
            </div>
            <div style="display:flex; flex-direction:column; justify-content:space-around;">
                <span style="padding:0;">{{answerCnt}}</span>
                <h3 style="padding:0;">오늘의 답변</h3>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/util/http-common"
export default {
    name: "MainBottom",
    data() {
        return {
            questionAllCnt: 1278,
            answerAllCnt: 2456,
            questionCnt: 15,
            answerCnt: 6,
        } 
    },
    methods: {
        getQuestionAllCnt() {
            http
                .get('/api/question/all')
                .then(({data}) => {
                    this.questionAllCnt = data;
                })
                .catch(err => {
                    console.log(err);
                })
        },
        getAnswerAllCnt() {
            http
                .get('/api/answer/all')
                .then(({data}) => {
                    this.answerAllCnt = data;
                })
                .catch(err => {
                    console.log(err);
                })
        },
        getQuestionCnt() {
            http
                .get('/api/question/today')
                .then(({data}) => {
                    this.questionCnt = data;
                })
                .catch(err => {
                    console.log(err);
                })
        },
        getAnswerCnt() {
            http
                .get('/api/answer/today')
                .then(({data}) => {
                    this.answerCnt = data;
                })
                .catch(err => {
                    console.log(err);
                })
        }
    },
    created() {
        this.getQuestionAllCnt();
        this.getAnswerAllCnt();
        this.getQuestionCnt();
        this.getAnswerCnt();
    }
}
</script>

<style scoped>
.backImg-jay {
  width: 100%; 
  height: 30vh;
  /* background-image: url("../assets/dark-sky-background.jpg"); */
  background: #fff;
}
.container-jay {
    width: 100%;
    height: 150px;
    display: flex;
    justify-content: space-around;
    align-items: center;
}
.container-jay div {
    width: 20%;
    height: 150px;
    margin-top: 75px;
    border: 1.5px solid rgb(124, 121, 121);
    text-align: center;
    border-radius: 5px;
}
span {
    font-size: 48px;
    color: rgb(124, 121, 121);
    padding-top: 20px;
}
h3 {
    padding-top: 10px;
    color: rgb(124, 121, 121);
}
</style>