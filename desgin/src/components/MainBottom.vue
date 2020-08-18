<template>
    <div class="backImg-jay">
        <div class="container-jay">
            <div>
                <span>{{questionAllCnt}}</span>
                <h3>전체 질문</h3>
            </div>
            <div>
                <span>{{answerAllCnt}}</span>
                <h3>전체 답변</h3>
            </div>
            <div>
                <span>{{questionCnt}}</span>
                <h3>오늘의 질문</h3>
            </div>
            <div>
                <span>{{answerCnt}}</span>
                <h3>오늘의 답변</h3>
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
  height: 300px;
  background-image: url("../assets/dark-sky-background.jpg");
  /*background: #2E2E2E;*/
}
.container-jay {
    width: 100%;
    height: 150px;
    display: flex;
    justify-content: space-around;
}
.container-jay div {
    width: 20%;
    height: 150px;
    margin-top: 75px;
    border: 1.5px solid whitesmoke;
    text-align: center;
}
span {
    font-size: 48px;
    color: whitesmoke;
    padding-top: 20px;
}
h3 {
    padding-top: 10px;
    color: whitesmoke
}
</style>