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
                <span style="float: right; margin: 0 2px; cursor: pointer;" @click="$router.push({ name : 'EditAnswer', params : { question_id : $route.params.question_id, answer_id: answer.id } })" v-if="parseInt($cookie.get('user_id')) === answer.user.id">수정</span>
            </div>
        </div>

        <!-- 댓글 작성란 -->
        <div v-show="commentInput">
            <input @keyup.enter="postComment" v-model="postContent" type="text" placeholder="악의가 담긴 댓글은 누군가를 상처입힐 수 있습니다.">
            <button style="cursor: pointer" @click="postComment">작성</button>
		</div>

        <!-- 댓글 목록 -->
        <ul v-if="comments">
			<li style="list-style: none; display: flex; margin-top: 15px;" v-for="(comment, index) in comments" :key="comment.id">
                <img src="" alt="user_profile">
                <div style="display: flex; flex-direction: column; width: 100%">
                    <header style="display: flex; justify-content: space-between;">
                        <h5>{{ comment.user.name }}</h5>
                        <span>{{ comment.updatedAt }}</span>
                    </header>
                    
                    <div style="display: flex; justify-content: space-between;">
                        <p v-show="selectedIndex !== index">{{ comment.content }}</p>
                        <input @keyup.enter="editComment({ content: comment.content, comment_id: comment.id })" v-show="selectedIndex === index" type="text" v-model="comment.content">
                        <div v-show="selectedIndex !== index">
                            <button @click="getIndex(index)">수정</button>
                            <button @click="deleteComment(comment.id)">삭제</button>
                        </div>
                        <div v-show="selectedIndex === index">
                            <button @click="editComment({ content: comment.content, comment_id: comment.id })">수정</button>
                        </div>
                    </div>
                </div>
			</li>
		</ul>
    </div>
</template>

<script>
import http from "@/util/http-common"
import { mapActions } from 'vuex'

export default {
    name: 'DetailAnswer',
    data() {
        return {
            comments: [],
            postContent: '',
            commentInput: false,
            selectedIndex: -1,
        }
    },
    props: {
        answer: Object,
    },
    computed: {
	},
    methods: {
        ...mapActions('answer', ['deleteAnswer']),
        changeCommentInput() {
			this.commentInput = !this.commentInput
        },
        getIndex(index) {
            if (index !== this.selectedIndex) {
                this.selectedIndex = index
            }
        },
        fetchComments() {
            http
                .get('/api/comment', {params: {
                    answer_id: this.answer.id,
                }})
                .then(({data}) => {
                    this.comments = data
                })
                .catch(err => console.log(err))
        },
        postComment() {
            var commentData = {
                user_id: parseInt(this.$cookie.get('user_id')), 
                answer_id: this.answer.id,
                content: this.postContent
            }
            http
                .post('/api/comment', commentData)
                .then(() => {
                    this.fetchComments()
                    this.postContent = ''
                })
                .catch(err => console.log(err))
        },
        editComment(commentData) {
            http
                .put('/api/comment', commentData)
                .then(() => {
                    this.fetchComments()
                    this.selectedIndex = -1
                })
                .catch(err => console.log(err))
        },
        deleteComment(comment_id) {
            http
                .delete('/api/comment', { params: { comment_id } })
                .then(() => {
                    this.fetchComments()
                })
                .catch(err => console.log(err))
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
div > button {
    cursor: pointer;
    background-color: #78acff;
    padding: 5px;
    border-radius: 10px;
    margin: 2px 2px
}
div > button:hover {
    background-color: #4879c7;
}
</style>