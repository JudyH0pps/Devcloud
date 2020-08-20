<template>
  <section class="detail">
    <DetailQuestion />
    <DetailAnswer v-for="answer in answers" :key="answer.id" :answer="answer" :questionId="$route.params.question_id"/>
    <button @click="moveToWrite">답변달기</button>
    <LoginCheckModal :loginCheck="loginCheck" @closeModal="changeModal" @switchModal="switchModal"/>
    <LoginModal @googleLogin="googleLogin" :loginModalOn="loginModalOn" @toggleModal="toggleModal"/>
  </section>
</template>

<script>
import DetailQuestion from '@/components/DetailQuestion.vue'
import DetailAnswer from '@/components/DetailAnswer.vue'
import LoginCheckModal from '@/components/LoginCheckModal.vue'
import LoginModal from '@/components/LoginModal.vue'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'Detail',
  components: {
    DetailQuestion,
    DetailAnswer,
    LoginModal,
    LoginCheckModal,
  },
  data() {
    return {
      loginModalOn: false,
      loginCheck: false,
    }
  },
	computed: {
		...mapState({
      answers: state => state.answer.answers,
      isLoggedIn: state => state.user.isLoggedIn,
		}),
	},
  methods: {
    ...mapActions('answer',['fetchAnswers']),
    moveToWrite() {
      if (this.isLoggedIn === true) {
        this.$router.push({
          name:'WriteAnswer',
          params:{ 
            "question_id" : this.$route.params.question_id
            },
          });
      }
      else {
        this.changeModal()
      }
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
    this.fetchAnswers(this.$route.params.question_id);    
    document.documentElement.scrollTop = 0;
    //console.log(this.$route.params.question_id)
  },
}
</script>

<style scoped>
.detail{
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
  bottom: 150px;
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
