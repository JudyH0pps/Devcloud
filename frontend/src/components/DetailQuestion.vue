<template>
  <div>
		<!-- 질문 타이틀 -->
		<header>
			<h1 v-if="question" class="mb-3">
				{{ question.title }}
			</h1>
		</header>
		
		<aside v-if="question.user" class="d-flex justify-content-between">
			<div>
				질문자 :
				<a class="text-decoration-none text-reset">{{ question.user.name }}</a>
			</div>
			<ul class="d-flex">
				<!-- suggestion button part -->
				<!-- 
					1. 추천 클릭 버튼을 클릭시 해당유저의 정보를 확인해 해당 포스트에 좋아요 버튼을 입력했는지
					아닌지 확인한다.

					2. 추천 버튼이 눌러져 있는 상태라면 추천 취소 아니라면 추천값을 올린다.

					[like table]
					id
					post_id
					user_id

				-->
				<li><i class="fas fa-sign-language" @click="clickLike"></i></li>
				
				<li v-if="question.user.id===parseInt($cookie.get('user_id'))"><i @click="goEditQuestion" class="fas fa-edit"></i></li>
				<li v-if="question.user.id===parseInt($cookie.get('user_id'))"><i @click="deleteQuestion(qid)" class="far fa-trash-alt"></i></li>
			</ul>
		</aside>

		<hr>

		<!-- 질문 본론 -->
		<article>				
			<!-- 질문 컨텐츠 -->
			<div>
				<p v-if="question">{{ question.content }}</p>
			</div>

		</article>
		
		<!-- 태그 -->
		<div class="mt-3">
			<!-- <button class="btn btn-primary mx-1" v-for="(tag, index) in questionData.tags" :key="index">#{{ tag }}</button> -->
			<button v-if="isDetailPage" class="btn btn-outline-secondary float-right" @click="onClickWriteAnswer">답변 작성</button>
		</div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
	name: "DetailQuestion",
	data() {
		return{
			likeData: {
				"question_id": this.question.id,
				"user_id": this.$cookie.get('user_id')
			},
		}
    },
	computed: {
		...mapState({
			question: state => state.question.question,
		})
	},
	methods: {
		onClickWriteAnswer() {
			this.$router.push({ name: 'WriteAnswer', params: { question_id: this.qid }})
		},
		...mapActions('question', ['fetchQuestion', 'goEditQuestion','deleteQuestion']),
		
		// store action 호출
		...mapActions('like', ['postQuestionLike']),

		// click event
		// 
		clickLike(likeData) {
			if(this.user_id == ''){
				alert("you need login");
			} else{
				this.postQuestionLike(likeData);
			}
		}
	},
	created() {
		this.fetchQuestion(this.qid)
	},
	props: {
		isDetailPage: Boolean,
		qid : Number,
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