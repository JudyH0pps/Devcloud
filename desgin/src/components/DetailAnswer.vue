<template>
    <div class="detailanswer">
        <h1 class="q-title">A.</h1>
        <div class="leftline">
            <div class="q-info">
                <div class="profile">
                    <img :src="answer.user.imageUrl" class="profile_img">
                    <p>{{ answer.user.name }}</p>
                </div>
                <p>{{ answer.updated_at }}</p>
            </div>
        </div>
        <!-- <p class="q-content">{{ answer.content }}</p> -->
        <div v-html="answer.content" class="q-content"></div>
        <div class="q-footer">
            <span style="cursor: pointer" @click="changeCommentInput">댓글 달기</span>
            <div>
                <span style="float: right; margin: 0 2px; cursor: pointer" v-if="parseInt($cookie.get('user_id')) === answer.user.id" @click="deleteAnswer({ answer_id: answer.id, question_id: $route.params.question_id })">삭제</span>
                <span @click="$router.push({ name : 'EditAnswer', params : { question_id : $route.params.question_id, answer_id: answer.id } })" style="float: right; margin: 0 2px; cursor: pointer;" v-if="parseInt($cookie.get('user_id')) === answer.user.id">수정</span>
            </div>
        </div>

        <!-- 댓글 작성란 -->
        <div v-show="commentInput">
            <input @keyup.enter="postComment({user_id: parseInt($cookie.get('user_id')), answer_id: answer.id,content: postContent})" v-model="postContent" type="text" placeholder="악의가 담긴 댓글은 누군가를 상처입힐 수 있습니다.">
            <button @click="postComment({user_id: parseInt($cookie.get('user_id')), answer_id: answer.id,content: postContent})">작성</button>
		</div>

        <!-- 댓글 목록 -->
        <ul v-if="comments">
			<!-- <li v-for="comment in comments" :key="comment.id">
				<img src="" alt="user_profile">
				<div>
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
			</li> -->
		</ul> 
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
    name: 'DetailAnswer',
    data() {
        return {
            commentInput: false,
            postContent: ''
        }
    },
    props: {
        answer: Object,
    },
    computed: {
		...mapState({
			comments: state => state.comment.comments
		}),
	},
    methods: {
        ...mapActions('answer', ['deleteAnswer']),
        ...mapActions('comment',['postComment', 'fetchComments', 'editComment', 'deleteComment']),
        changeCommentInput() {
			this.commentInput = !this.commentInput
		}
    },
    created() {
		this.fetchComments(this.answer.id)
    }
}
</script>

<style scoped>
.q-title {
  padding: 10px 0;
  border-bottom: 1px solid #eeeeee;
}
.tags {
    text-align: right;
}
.leftline{
    border-left: 4px solid #eeeeee;
    padding-left: 15px;
}
.q-info {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px 0;
}
.q-info p {
    font-size: 14px;
    color: gray;
}
.q-content {
    padding: 10px 0;
}
.profile {
    display: flex;
    flex-direction: row;
    align-items: center;
}
.profile_img {
    border-radius: 50%;
    height: 40px;
    margin-right: 10px;
}
.content-reader >>> .ql-syntax {
    background-color: black;
    color: white;
    padding: 3px 5px;
    border-radius: 3px;
}
.q-footer {
    display: flex; 
    justify-content: space-between;
}
</style>