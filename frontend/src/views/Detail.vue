<template>
  <div class="text-left py-3 container">
	<!-- 헤더 -->

	<!-- 질문 -->
		<DetailQuestion :isDetailPage="isDetailPage" :qid="questionId" />

		<!-- 답변 -->
		<header class="d-flex my-5">
			<h1 class="mb-0 px-2">
				A. 여기부터 답변 영역입니다.
			</h1>
		</header>

		<!-- <div v-for="(answerData, index) in detailAnswerData" :key="index">
			<DetailAnswer :answerData="answerData" />
		</div> -->

		<DetailAnswer v-for="answer in answers" :key="answer.id" :answer="answer" />
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import DetailQuestion from '@/components/DetailQuestion.vue'
import DetailAnswer from '@/components/DetailAnswer.vue'

export default {
	name: 'Detail',
	data() {
		return {	
			isDetailPage: true,
		}
	},
	components: {
		DetailQuestion,
		DetailAnswer,
	},
	computed: {
		...mapState({
			answers: state => state.answer.answers
		}),
		questionId() {
			return parseInt(this.$route.params.question_id)
		}
	},
	methods: {
		...mapActions('answer',['fetchAnswers']),
	},
	created() {
		// alert("조회한 글번호 :" +this.$route.params.qid)
		// this.fetchAnswers(this.$route.params.qid);
		this.fetchAnswers(this.questionId)
	}
};
</script>

<style>
</style>