<template>
  <section class="detail">
    <DetailQuestion v-if="answerLength !== null" :answerLength="answerLength" />
    <DetailAnswer v-for="answer in answers" :key="answer.id" :answer="answer"/>
    <button @click="moveToWrite">답변달기</button>
  </section>
</template>

<script>
import DetailQuestion from '@/components/DetailQuestion.vue'
import DetailAnswer from '@/components/DetailAnswer.vue'
// import { mapState, mapActions } from 'vuex'
import http from "@/util/http-common"

export default {
  name: 'Detail',
  components: {
    DetailQuestion,
    DetailAnswer
  },
  data() {
    return {
      answerLength: null,
      answers: []
    }
  },
	computed: {
		// ...mapState({
		// 	answers: state => state.answer.answers,
		// }),
	},
  methods: {
    // ...mapActions('answer',['fetchAnswers']),
    moveToWrite() {
      this.$router.push({
        name:'WriteAnswer',
        params:{ 
          "question_id" : this.$route.params.question_id
          },
        });
    },
    fetchAnswers() {
					http
						.get('/api/answer', {
							params: {
								question_id: parseInt(this.$route.params.question_id)
							}
						})
						.then(({data}) => {
              this.answers = data
              this.answerLength = this.answers.length
						})
						.catch(err => console.log(err))
		},
  },
	created() {
		// alert("조회한 글번호 :" +this.$route.params.question_id)
		// this.fetchAnswers(this.$route.params.qid);
    this.fetchAnswers()
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
