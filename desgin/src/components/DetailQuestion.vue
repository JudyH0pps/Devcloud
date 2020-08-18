<template>
    <div class="detailquestion" v-if="this.$store.state.question.question">
        <header style="display: flex; justify-content: space-between; align-items: center">
            <h1 class="q-title">Q. {{ question.title }}</h1>
            <div v-if="question.user.id === parseInt($cookie.get('user_id')) && answers.length < 1" class="buttons">
                <button class="editBtn" @click="moveToEdit">수정</button>
                <button class="delBtn" @click="deleteQuestion(parseInt($route.params.question_id))">삭제</button>
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
                <p>{{ question.updatedAt }}</p>
            </div>
        </div>
        <p v-html="question.content" class="q-content">{{ question.content }}</p>


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
            //console.log(this.question.id);
            //console.log(parseInt(this.$cookie.get("user_id")));
            
            http.post('/api/liketoquestion', {
                "question_id": this.question.id,
                "user_id": parseInt(this.$cookie.get("user_id")) 
            })
            .catch(err => {
                console.log(err);
            })
            
            if(this.chkClicked == false){
                this.chkClicked = true;
            } else {
                this.chkClicked = false;
            }
            console.log(parseInt(this.$cookie.get("user_id")));
            
            if (this.isLoggedIn === true) {
                http.post('/api/liketoquestion', {
                    "question_id": this.question.id,
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
            console.log("요청한 질문 id : " + this.$route.params.question_id)
            http.get('/api/liketoquestion', {
                "question_id": this.$route.params.question_id,
                "user_id": parseInt(this.$cookie.get("user_id"))
            })
            .then(res => {
                // 가져온 유저 데이터 값과 현재 로그인 된 유저 값 비교

                // console.log(this.question.id)
                // console.log(parseInt(this.$cookie.get("user_id")))

                var dataset = res.data;
                
                //console.log(dataset);

                //해당 데이터에서 현재 question_id가 있는지 찾는다.
                for(var idx = 0; idx < dataset.length; idx++){
                    if(dataset[idx].question_id == this.$route.params.question_id && dataset[idx].user_id == parseInt(this.$cookie.get("user_id"))) {
                        this.chk = 200;
                        //console.log(dataset[idx]);
                    }
                }
                console.log("question_id : " + this.$route.params.question_id)
                console.log("체크 값 : " + this.chk)

                if(this.chk == 200) {
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
.like-button span {
    font-size: 1em;
    color: Tomato;
    margin-left: 4px;
    cursor: pointer;
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
    background: rgb(138, 243, 138);
}
</style>