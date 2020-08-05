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

	<!-- 댓글작성란 -->
	<div class="text-left">
		<label for="comment">댓글 작성</label>
		<div class="input-group">
			<input @keyup.enter="postComment" type="text" class="form-control" id="comment" placeholder="악의가 담긴 댓글은 누군가를 상처입힐 수 있습니다.">
			<button @click="postComment" class="btn btn-secondary">작성</button>
		</div>
	</div>

	<!-- 댓글 목록 -->
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
			answers: state => state.answer.answers,
		}),
		questionId() {
			return parseInt(this.$route.params.question_id)
		}
	},
	methods: {
		...mapActions('answer',['fetchAnswers']),
		...mapActions('comment',['postComment'])
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