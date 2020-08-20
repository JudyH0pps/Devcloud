<template>
    <div class="detailquestion" v-if="this.$store.state.question.question">
        <header style="display: flex; justify-content: space-between;">
            <h1 class="q-title">Q. {{ question.title }}</h1>
            <div @click="likeClick" class="like-button">
                <i v-if="chkClicked" style="font-size:20px;" class="fas fa-heart"></i>
                <i v-else stlye="font-size:10px;" style="font-size:20px;" class="far fa-heart"></i>
            </div>
        </header>
        <div class="leftline">
            <div class="tags">
                <span class="tag" v-for="data in question.questionTags" :key="data.id">{{ data.tag.name }}</span>
            </div>
            <div class="q-info">
                <div class='profile'>
                    <img :src="question.user.imageUrl" class="profile_img">
                    <router-link :to="{name: 'Profile', params :{ user_id : question.user.id }}">
                        <p>{{ question.user.name }}</p>
                    </router-link>
                </div>
                <p>{{ parseDateString(question.updatedAt) }}</p>
            </div>
        </div>
                <!-- current user가 like button clicked 상태면 x -->

        <p v-html="question.content" class="q-content">{{ question.content }}</p>

        <div v-if="question.user.id === parseInt($cookie.get('user_id')) && answers.length < 1" class="buttons" style="text-align:right;">
            <button class="editBtn" @click="moveToEdit">수정</button>
            <button class="delBtn" @click="deleteQuestion(parseInt($route.params.question_id))">삭제</button>
        </div>




        <div class="vld-parent">
            <loading :active.sync="isLoading" 
            :can-cancel="true" 
            :is-full-page="fullPage"
            color=#5ABEFF
            :width="128"
            :height="128"
            >
            </loading>
            

            <!--<label><input type="checkbox" v-model="fullPage">Full page?</label>-->
            <!--<button @click.prevent="doAjax">fetch Data</button>-->
        </div>
        <LoginCheckModal :loginCheck="loginCheck" @closeModal="changeModal" @switchModal="switchModal"/>
        <LoginModal @googleLogin="googleLogin" :loginModalOn="loginModalOn" @toggleModal="toggleModal"/>
    </div>
</template>

<script>
import LoginCheckModal from '@/components/LoginCheckModal.vue'
import LoginModal from '../components/LoginModal.vue'
import { mapState, mapActions } from 'vuex'
import http from "@/util/http-common"
// Import component
import Loading from 'vue-loading-overlay';
// Import stylesheet
import 'vue-loading-overlay/dist/vue-loading.css';
export default {
    name: 'DetailQuestion',
    data() {
        return {
            // question: {},
            chkClicked: false,
            isLoading: false,
            fullPage: true,

            chk: 100,

            loginModalOn: false,
            loginCheck: false,
        }
    },
     components: {
        Loading,
        LoginModal,
        LoginCheckModal,
    },
    computed: {
        ...mapState({
            question: state => state.question.question,
            answers: state => state.answer.answers,
            isLoggedIn: state => state.user.isLoggedIn
		})
    },
	methods: {
        ...mapActions('question', ['fetchQuestion', 'goEditQuestion','deleteQuestion']),
        //loading overlay
        doAjax() {
            this.isLoading = true;
            // simulate AJAX
            setTimeout(() => {
                this.isLoading = false
            },800)
        },
        moveToEdit() {
            this.$router.push({
                name: 'Edit',
                params: {
                    question_id: parseInt(this.$route.params.question_id)
                }
            })
        },
        likeClick() {
            if (this.isLoggedIn === true) {
                http
                    .post('/api/liketoquestion', {
                        
                        "question_id": this.$route.params.question_id,
                        "user_id": parseInt(this.$cookie.get("user_id")) 
                        
                    })
                    .then(res => {
                        console.log("like success")
                        console.log(res.data)
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
            else {
                this.changeModal()
            }
        },
        loadLikeState() {
            http.get('/api/liketoquestion', {
                params: { 
                    "question_id": this.$route.params.question_id,
                    "user_id": parseInt(this.$cookie.get("user_id"))
                }
            })
            .then(({data}) => {
                // 가져온 유저 데이터 값과 현재 로그인 된 유저 값 비교
                //console.log(data);
                //해당 데이터에서 현재 question_id가 있는지 찾는다.
                if(data != "Resource not bound"){
                    this.chkClicked = true;
                } else {
                    this.chkClicked = false;
                }
            })
            .catch(err => {
                console.log(err.data);
            })
        },
        googleLogin() {
            window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://localhost:3000/"'
        },
        toggleModal() {
            this.loginModalOn = !this.loginModalOn;
        },
        changeModal() {
            this.loginCheck = !this.loginCheck;
        },
        switchModal() {
            this.changeModal()
            this.toggleModal()
        },
        parseDateString(date) {
            let year = date.slice(0,4);
            let month = date.slice(5,7);
            let day = date.slice(8,10);
            let hour = date.slice(11,13);
            let minute = date.slice(14,16);
            return year + '년 ' + month + '월 ' + day + '일 ' + hour + '시 ' + minute + '분';
        }
	},
	created() {
        // alert(this.$route.params.question_id)
        this.fetchQuestion(this.$route.params.question_id),
        this.loadLikeState(),
        this.doAjax()
        
    }
}

</script>

<style scoped>
.detailquestion {
    background:white;
    border-radius: 10px;
    padding: 15px;
    margin-top: 10px;
}
.q-title {
  padding: 10px 0;
  /* border-bottom: 1px solid #eeeeee; */
  margin-bottom: 10px;
}
.tags {
    text-align: left;
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
.like-button span {
    font-size: 14px;
    color: Tomato;
    margin-left: 4px;
    cursor: pointer;
}
.like-button {
    height: 30px;
    width: 60px;
    /* margin-left: auto; */
    display: flex;
    flex-direction: row;
    color: Tomato;
    border: 1px solid #ccc;
    border-radius: 15px;
    text-align: center;
    align-items: center;
    justify-content: center;
    margin: 0 0 0 auto;
    margin-top: 0 !important;
}
.like-button:hover{
    background: #eee;
    color: #FF4500; 
}
.q-content >>> img {
    max-width: 300px;
    max-height: 300px;
}
.editBtn,
.delBtn {
    border: 1px solid #ccc;
    border-radius: 35px;
    width: 50px;
    height: 25px;
    text-align: center;
    margin: 2px; 
    background: white;
}
.delBtn:hover {
    color: #eee;
    background: rgb(243, 138, 152);
}
.editBtn:hover {
    color: #eee;
    background: #ccc;
}
</style>