<template>
  <div class="container text-left py-3">
    <!-- 질문 -->
    <DetailQuestion :qid="questionId" :isDetailPage="isDetailPage" />

    <!-- 에디터 -->
		<textarea
			class="w-100 mt-5"
			v-model="content"
			type="text"
			placeholder="여기는 답변 내용을 적을 공간입니다. 추후 에디터를 넣을 생각입니다. 감사합니다."
		></textarea>
		<button class="btn btn-outline-secondary float-right" @click="writeAnswer({content, question_id: questionId, user_id: $cookie.get('user_id')})"><span>답변 작성</span></button>
  </div>
</template>

<script>
import DetailQuestion from "@/components/DetailQuestion.vue";
import {mapState, mapActions} from 'vuex'
export default {
  name: "WriteAnswer",
  computed: {
    questionId() {
      return parseInt(this.$route.params.question_id);
    },
    answerId() {
      return parseInt(this.$route.params.answer_id);
    },
    ...mapState({
      answers: state => state.answer.answers
    })
  },
  components: {
    DetailQuestion
  },
  data() {
    return {
			content: "",
      isDetailPage: false,
      isEditPage: false
    };
  },
  methods: {
    ...mapActions('answer',['fetchAnswers','postAnswer', 'editAnswer']),
    writeAnswer(answerData) {
      if (this.isEditPage) {
        answerData.id = this.answerId
        this.editAnswer(answerData)
      }
      else {
        this.postAnswer(answerData)
      }
    }
  },
  created() {
    if (window.location.href===`http://localhost:3000/question/${this.questionId}/${this.answerId}/edit`) {
      this.fetchAnswers(this.questionId)
      const answer = this.answers.find(answerData => {
        return answerData.id === this.answerId
      })
      this.content = answer.content
      this.isEditPage = true
    }
  }
};
</script>

<style>
</style>