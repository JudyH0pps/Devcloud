<template>
    <section class="search">
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
        <hr style="border: 1px solid #D3D3D3; margin-top: 20px;">
        
        <div v-if="this.searchKeyword">
            <h2 style="margin: 30px 0 50px;">Search Result: <span style="text-decoration: underline; color: gray; font-weight: none">{{this.searchKeyword}}</span></h2>
        </div>
        <div v-else>
            <h2 style="margin: 30px 0 50px;">All Questions</h2>
        </div>

        <SearchResultCard  v-for="(question, index) in questions" :question="question" :key="index" :keyword="searchKeyword" />
        <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler">
            <!-- <template slot="no-more">더 많은 질문을 등록해주세요 !</template> -->
            <span slot="no-more">더 많은 질문을 등록해주세요 !</span>
            <span slot="no-results">더 많은 질문을 등록해주세요 !</span>
        </infinite-loading>
        <button @click="moveToWrite">새 질문 작성</button>
        <LoginCheckModal :loginCheck="loginCheck" @closeModal="changeModal" @switchModal="switchModal"/>
        <LoginModal @googleLogin="googleLogin" :loginModalOn="loginModalOn" @toggleModal="toggleModal"/>
    </section>
</template>

<script>
import SearchResultCard from'../components/SearchResultCard.vue'
import LoginCheckModal from '@/components/LoginCheckModal.vue'
import LoginModal from '../components/LoginModal.vue'
import {mapActions, mapState} from 'vuex'
import http from "@/util/http-common";
// Import component
import InfiniteLoading from 'vue-infinite-loading';
import Loading from 'vue-loading-overlay';
// Import stylesheet
import 'vue-loading-overlay/dist/vue-loading.css';
export default{
    name: 'Search',
    components: {
        InfiniteLoading,
        SearchResultCard,
        Loading,
        LoginModal,
        LoginCheckModal,
    },
    data() {
        return {
            // searchresults: [1,2,3,4,5,6,7,8,9],
            page: 2,
            isLoading: false,
            fullPage: true,
            infiniteId: +new Date(),
            loginModalOn: false,
            loginCheck: false,
        } 
    },
    methods:{
        ...mapActions('question',['fetchQuestionsByKeyword']),
        infiniteHandler($state) {
            http.get('/api/question', {
                params: {
                    keyword : this.searchKeyword,
                    page: this.page,
                },
            }).then(({ data }) => {
                if (data.content.length) {
                this.page += 1;
                this.questions.push(...data.content);
                $state.loaded();
                } else {
                $state.complete();
                }
            });
        },
        moveToWrite() {
            if (this.isLoggedIn === true) {
                this.$router.push({ 'name' : 'Write' });
            }
            else {
                this.changeModal()
            }
        },
        //loading overlay
        doAjax() {
            this.isLoading = true;
            // simulate AJAX
            setTimeout(() => {
                this.isLoading = false
            },800)
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
    computed:{
        ...mapState({
            questions : state => state.question.questions,
            isLoggedIn : state => state.user.isLoggedIn
        }),
        searchKeyword(){
			return this.$route.params.search_keyword
        }
    },
    created() {
        this.fetchQuestionsByKeyword(this.searchKeyword),
        this.doAjax()
        document.documentElement.scrollTop = 0;
        // console.log(this.questions)
    },
    watch:{
        searchKeyword : function(){
            //this.$router.go()
            this.fetchQuestionsByKeyword(this.searchKeyword)
            this.page = 2;
            this.infiniteId += 1;
            document.documentElement.scrollTop = 0;
        }
    }
}

</script>

<style scoped>
.search {
  /* background: #e9e9e9; */
  /* overflow: hidden; */
  /* padding-top: 20vh; */
  font-family: "Poppins", sans-serif;
  margin: 80px auto 0;
  padding: 10px;
  max-width: 900px;
  min-height: 100vh;
}
button {
  /* float: right; */
  position: fixed;
  right: 150px;
  bottom: 80px;
  /* transform: translate(-50%, 0); */
  /* right: -50%; */
  width: 120px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  text-transform: uppercase;
  text-decoration: none;
  font-family: sans-serif;
  box-sizing: border-box;
  background: linear-gradient(90deg, #03a9f4,#f441a5,#ffeb3b,#03a9f4);
  background-size: 400%;
  border-radius: 30px;
  border: 0;
  outline: 0;
}
button:hover{
  animation: animate 8s linear infinite;
  cursor: pointer;
}
button:hover::before{
  filter: blur(20px);
  /* z-index: -1; */
  opacity: 1;
  animation: animate 8s linear infinite;
}
button::before {
  content: '';
  position: absolute;
  z-index: -1;
  top: -5px;
  left: -5px;
  right: -5px;
  bottom: -5px;
  background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);
  background-size: 400%;
  border-radius: 40px;
  opacity: 0;
  transition: 0.5s;
}
</style>