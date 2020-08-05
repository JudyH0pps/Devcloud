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
				<input @keyup.enter="postComment({user_id, answer_id: answer.id,content: postContent})" v-model="post_content" type="text" class="form-control" id="comment" placeholder="악의가 담긴 댓글은 누군가를 상처입힐 수 있습니다.">
				<button @click="postComment({user_id, answer_id: answer.id,content: postContent})" class="btn btn-secondary">작성</button>
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

		<div class="d-flex w-100">
			<div class="d-flex flex-column w-100">
				<header class="d-flex justify-content-between">
					<h5>홍길동</h5>
					<span>2020-08-05</span>
				</header>
				<div class="d-flex justify-content-between">
					<p v-show="!isEdit">{{originalContent}}</p>
					<input @keyup.enter="editComment({comment_id: comment.id, content: originalContent})" v-show="isEdit" type="text" v-model="value">
					<div v-show="!isEdit">
						<button @click="changeIsEdit" class="btn btn-secondary">수정</button>
						<button @click="deleteComment({answer_id: answer.id, comment_id: comment.id})" class="btn btn-secondary">삭제</button>
					</div>
					<div v-show="isEdit">
						<button @click="editComment({comment_id: comment.id, content: originalContent})" class="btn btn-primary">수정</button>
					</div>
				</div>
			</div>
		</div>
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
		...mapActions('comment',['postComment', 'fetchComments', 'editComment', 'deleteComment']),
		changeIsEdit() {
			this.isEdit = !this.isEdit
		}
	},
	computed: {
		...mapState({
			comments: state => state.comment.comments
		}),
	},
	created() {
		this.fetchComments(this.answer.id)
	},
	data() {
		return {
			postContent: "",
			originalContent: "댓글 내용입니다.",
			isEdit: false
		}
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