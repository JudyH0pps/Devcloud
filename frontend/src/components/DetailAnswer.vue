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
		<div class="d-flex justify-content-between">
			<a>add comment</a>
			<span>
				answered by
				<a href class="text-decoration-none text-reset">
					<big>{{ answer.user.name }}</big>
				</a>
			</span>
		</div>
		<!-- 댓글작성란 -->
		<div class="text-left">
			<label for="comment">댓글 작성</label>
			<div class="input-group">
				<input @keyup.enter="postComment" type="text" class="form-control" id="comment" placeholder="악의가 담긴 댓글은 누군가를 상처입힐 수 있습니다.">
				<button @click="postComment" class="btn btn-secondary">작성</button>
			</div>
		</div>

		<!-- 댓글 목록 -->
		<!-- <ul class="list-group">
			<li class="list-group-item d-flex" v-for="comment in comments" :key="comment.id">
				<img src="" alt="user_profile">
				<div class="d-flex flex-column">
					<header>
						<h5>{{ comment.user.name }}</h5>
						<span>{{ comment.create_at }}</span>
					</header>
					<div>
						<p>{{ comment.content }}</p>
						<div>
							<button>Edit</button>
							<button>Delete</button>
						</div>
					</div>
				</div>
			</li>
		</ul> -->
	</div>

  </div>
</template>

<script>
import {mapState, mapActions} from 'vuex'
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
		...mapActions('answer',['deleteAnswer']),
		...mapActions('comment',['postComment', 'fetchComments'])
	},
	computed: {
		...mapState({
			comments: state => state.comment.comments
		}),
		answerId() {
			return parseInt(this.$route.params.answer_id)
		}
	},
	created() {
		this.fetchComments(this.answerId)
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