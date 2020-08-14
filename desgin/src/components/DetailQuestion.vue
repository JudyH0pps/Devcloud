<template>
    <div class="detailquestion" v-if="this.$store.state.question.question">
        <header style="display: flex; justify-content: space-between; align-items: center">
            <h1 class="q-title">Q. {{ question.title }}</h1>
            <div v-if="question.user.id === parseInt($cookie.get('user_id')) && answerLength < 1" class="buttons">
                <span @click="moveToEdit">수정</span>
                <span @click="deleteQuestion(parseInt($route.params.question_id))">삭제</span>
            </div>
        </header>
        <div class="leftline">
            <div class="tags">
                <span class="tag" v-for="data in question.questionTags" :key="data.id">{{ data.tag.name }}</span>
            </div>
            <div class="q-info">
                <div class='profile'>
                    <img :src="question.user.imageUrl" class="profile_img">
                    <p>{{ question.user.name }}</p>
                </div>
                <p>{{ question.updatedAt }}</p>
            </div>
        </div>
        <p class="q-content">{{ question.content }}</p>


        <!-- current user가 like button clicked 상태면 x -->
        <div @click="likeClick" class="like-button">
            <div v-if="chkClicked">
                <i class="fas fa-heart"></i>
                <span>좋아요 취소</span>
            </div>
            <div v-else>
                <i class="far fa-heart"></i>
                <span>좋아요</span>
            </div>
        </div>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import http from "@/util/http-common"

export default {
    name: 'DetailQuestion',
    props: {
        answerLength: Number
    },
    data() {
        return {
            // question: {},
            chkClicked: false,
        }
    },
    computed: {
        ...mapState({
			question: state => state.question.question
		})
    },
	methods: {
        ...mapActions('question', ['fetchQuestion', 'goEditQuestion','deleteQuestion']),
        moveToEdit() {
            this.$router.push({
                name: 'Edit',
                params: {
                    question_id: parseInt(this.$route.params.question_id)
                }
            })
        },
        likeClick() {
            console.log(this.question.id);
            console.log(parseInt(this.$cookie.get("user_id")));
            

            http
                .post('/api/liketoquestion', {
                    params: {
                        "question_id": this.question.id,
                        "user_id": parseInt(this.$cookie.get("user_id")),
                    }
                })
                .catch(err => {
                    console.log(err);
                })
            
            if(this.chkClicked == false){
                this.chkClicked = true;
            } else {
                this.chkClicked = false;
            }
        }
	},
	created() {
        // alert(this.$route.params.question_id)
        this.fetchQuestion(this.$route.params.question_id)

        // 좋아요 리스트를 받아와서 있는지 없는지만 확인
        http
            .get('/api/liketoquestion', {
                params: {
                    "question_id": this.question.id,
                }
            })
            .then(({data}) => {
                // 가져온 유저 데이터 값과 현재 로그인 된 유저 값 비교
                console.log(data.user_id)

                if(data.user_id == this.$cookie.get("user_id")) {
                    this.chkClicked = true;
                } else {
                    this.chkClicked = false;
                }
            })
            .catch((err) =>{
                console.log(err);
            });
	},
    // mounted() {
    //     console.log(this.$store.state.question.question)
    // }
}
</script>

<style scoped>
.q-title {
  padding: 10px 0;
  border-bottom: 1px solid #eeeeee;
  margin-bottom: 10px;
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
.q-content {
    padding: 10px 0;
}
.q-info p {
    font-size: 14px;
    color: gray;
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
.like-button {
    height: 50px;
    width: 150px;
    margin-left: auto;
    display: flex;
    flex-direction: row;
    color: Tomato;
    border: 1px solid #ccc;
    border-radius: 35px;
    text-align: center;
    align-items: center;
    justify-content: center;
}
.like-button:hover{
    background: #eee;
    color: #FF4500; 
}
</style>