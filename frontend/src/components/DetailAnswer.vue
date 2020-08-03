<template>
  <div v-show="answer">
			<hr />

			<!-- 답변 본론 -->
			<div class="my-5">
				<ul class="float-right d-flex">
					<li><i class="fas fa-sign-language"></i></li>
					<li v-if="answer.user.id===parseInt($cookie.get('user_id'))"><i @click="goEditAnswer" class="fas fa-edit"></i></li>
					<li v-if="answer.user.id===parseInt($cookie.get('user_id'))"><i @click="deleteAnswer({answer_id: answer.id, question_id: $route.params.question_id})" class="far fa-trash-alt"></i></li>
				</ul>
				<p>{{ answer.content }}</p>
				<div class="d-flex justify-content-end">
					<span>
						answered by
						<a href class="text-decoration-none text-reset">
							<big>{{ answer.user.name }}</big>
						</a>
					</span>
				</div>
			</div>

  </div>
</template>

<script>
import {mapActions} from 'vuex'
export default {
	name: "DetailAnswer",
	props: {
		answer: Object,
	},
	methods: {
		goEditAnswer() {
			this.$router.push({
				name: 'EditAnswer', 
				params: {
					question_id: this.$route.params.question_id,
					answer_id: this.answer.id
				}
			})
		},
		...mapActions('answer',['deleteAnswer'])
	}
}
</script>

<style scoped>
ul {
	list-style-type: none;
}

li {
	margin: 0 5px;
}

li i:hover {
	cursor: pointer;
	border-style: inset;
}
</style>