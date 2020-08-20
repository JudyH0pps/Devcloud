<template>
    <div class="detailanswer">
        <div style="display:flex; flex-direction:row; align-items:center;">
            <h1 class="q-title">A.</h1>
                    <!-- 작성자 일때 -->
                    <template v-if="writerChk">
                        <!-- 해당 포스트에 채택된 답변이 있을 때 -->
                        <template v-if="selectedAnswer">
                            <!-- 채택된 컴포넌트에는 채택완료 표시-->
                            <template v-if="isSelectedAnswer">
                                <i class="fas fa-check-circle" style="margin-left: 5px; color: green; font-size: 22px"></i>
                                <span style="font-size:14px;"> 채택완료</span>
                            </template>
                            <!-- 채택되지 않은 컴포넌트는 아무 표시도 하지 않는다.-->

                        </template>
                        <!-- 해당 포스트에 채택된 답변이 없을 때-->
                        <template v-else>
                            <button class="answer_select" style="margin-left: 30px" @click="selectBtn">채택하기</button>
                        </template>
                    </template>
                    <!-- current user is not writer-->
                    <template v-else>
                        <!-- if this answer is selected from writer -->
                        <template v-if="isSelectedAnswer">
                                <i class="fas fa-check-circle" style="margin-left: 5px; color: green; font-size: 16px"></i>
                                <span style="font-size:12px; font-weight:bold;"> 채택완료</span>
                        </template>
                    </template>
            <div @click="likeClick" class="like-button">
                <i v-if="chkClicked" style="font-size:20px;" class="fas fa-heart"></i>
                <i style="font-size:20px;" v-else class="far fa-heart"></i>
            </div>
        </div>
        <div class="leftline">
            <div class="q-info">
                <div class="profile">
                    <img :src="answer.user.imageUrl" class="profile_img">
                    <p>{{ answer.user.name }}</p>
                    <!-- current user is writer -->
                </div>
                <p>{{ parseDateString(answer.updated_at) }}</p>
                <p>{{ answer.updated_at }}</p>
            </div>
        </div>

        <!-- <p class="q-content">{{ answer.content }}</p> -->
        <div v-html="answer.content" class="q-content"></div>
        <div class="q-footer">
            <!-- <span style="cursor: pointer" @click="changeCommentInput">댓글 달기</span> -->
            <button class="editBtn" style="margin: 0 2px; cursor: pointer; margin-left:auto;" @click="$router.push({ name : 'EditAnswer', params : { question_id : $route.params.question_id, answer_id: answer.id } })" v-if="parseInt($cookie.get('user_id')) === answer.user.id">수정</button>
            <button class="delBtn" style="margin: 0 2px; cursor: pointer" v-if="parseInt($cookie.get('user_id')) === answer.user.id" @click="deleteAnswer({ answer_id: answer.id, question_id: $route.params.question_id })">삭제</button>
        </div>

        <!-- 댓글 작성란 -->
        <div class="commentInputSection" style="margin-top: 10px;">
            <input style="padding-left: 5px; height: 40px; border: 1px solid #ccc; outline: none;" @keyup.enter="postComment" v-model="postContent" type="text" placeholder="해당 답변에 대한 댓글을 작성해주세요">
            <button class="writeBtn" style="cursor: pointer; margin-left:auto;" @click="postComment">댓글작성</button>
		</div>

        <!-- 댓글 목록 -->
        <ul class="commentSection" v-if="comments">
			<li style="list-style: none; width: 100%; display: flex; flex-direction: column; margin-top: 15px;" v-for="(comment, index) in comments" :key="comment.id">
                <div style="display: flex; justify-content: space-between;">
                    <h5 style="font-size: 12px;">{{ comment.user.name }}</h5>
                    <span style="font-size: 12px;">{{ parseDateString(comment.updatedAt) }}</span>
                </div>
                    
                <div style="display: flex; justify-content: space-between;">
                    <p style="margin-left: 10px;" v-show="selectedIndex !== index">{{ comment.content }}</p>
                    <input @keyup.enter="editComment({ content: comment.content, comment_id: comment.id })" v-show="selectedIndex === index" type="text" v-model="comment.content">
                    <div v-show="selectedIndex !== index && comment.user.id === parseInt($cookie.get('user_id'))">
                        <button class="editBtn" @click="getIndex(index)">수정</button>
                        <button class="delBtn" @click="deleteComment(comment.id)">삭제</button>
                    </div>
                    <div v-show="selectedIndex === index">
                        <button @click="editComment({ content: comment.content, comment_id: comment.id })">수정</button>
                    </div>
                </div>
			</li>
		</ul>

        <LoginCheckModal :loginCheck="loginCheck" @closeModal="changeModal" @switchModal="switchModal"/>
        <LoginModal @googleLogin="googleLogin" :loginModalOn="loginModalOn" @toggleModal="toggleModal"/>
    </div>
</template>

<script>
import LoginCheckModal from '../components/LoginCheckModal.vue'
import LoginModal from '../components/LoginModal.vue'
import http from "@/util/http-common"
import { mapActions, mapState } from 'vuex'

export default {
    name: 'DetailAnswer',
    components: {
        LoginModal,
        LoginCheckModal,
    },
    data() {
        return {
            comments: [],
            postContent: '',
            commentInput: false,
            selectedIndex: -1,
            chkClicked: false,
            chk: 100,
            
            //채택 관련
            selectedAnswerId: "",
            // 채택된 답변의 유무 확인
            selectedAnswer: false,
            // 현재 컴포넌트가 채택된 답변인지 아닌지
            isSelectedAnswer: true,

            writerId: '',
            writerChk: false,
            loginModalOn: false,
            loginCheck: false,
        }
    },
    props: {
        answer: Object,
        questionId: Number,
    },
    computed: {
        ...mapState({
            question: state => state.question.question,
            isLoggedIn: state => state.user.isLoggedIn,
        }),
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
            if (this.isLoggedIn === true && this.postContent !== '') {
                var commentData = {
                    user_id: parseInt(this.$cookie.get('user_id')), 
                    answer_id: this.answer.id,
                    content: this.postContent
                }
                http
                    .post('/api/comment', commentData)
                    .then(() => {
                        this.fetchComments();
                        this.postContent = '';
                        this.changeCommentInput();
                    })
                    .catch(err => console.log(err))
            }
            else if (this.postContent === ''){
                alert('댓글 내용을 작성해주세요!')
            }
            else {
                this.changeModal()
            }
        },
        editComment(commentData) {
            http
                .put('/api/comment', commentData)
                .then(() => {
                    this.fetchComments()
                    this.selectedIndex = -1
                    this.changeCommentInput();
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
        },
        // 이 요청시 채택된 답변이 있는지까지 조회
        getUserId() {
            //console.log("현재 답변아이디는 : " + this.answer.id)
            http
                .get('/api/question', {
                    params: {
                        "question_id": this.questionId
                    }
                })
                .then(({data}) => {
                    this.writerId = data.user.id;
                    // 채택된 답변값 가져오기
                    this.selectedAnswerId = data.selectedAnswerId;
                    // 글 작성자 id를 가져온 후 유저 비교를 진행 
                    this.userCheck()
                })
                .catch(err => {
                    console.log(err);
                })
        },
        // 현재 화면을 보는 유저가 작성자인지 아닌지
        userCheck() {
            if(this.writerId == this.$cookie.get('user_id')){
                this.writerChk = true;
            }
            else {
                this.writerChk = false;
            }
        },
        likeClick() {
            if (this.isLoggedIn === true) {
                http
                    .post('/api/liketoanswer', {
                        
                        "answer_id": this.answer.id,
                        "user_id": parseInt(this.$cookie.get("user_id")),
                        
                    })
                    .then(res => {
                        console.log("like success")
                        console.log(res.data)
                    })
                    .catch(err => {
                        console.log(err);
                    })
                
                if(this.chkClicked == false){
                    this.chkClicked = true;
                } else {
                    this.chkClicked = false;
                }
            }
            else {
                this.changeModal()
            }
        },
        loadLikeState() {
            http.get('/api/liketoanswer', {
                params: {
                    "answer_id": this.answer.id,
                    "user_id": parseInt(this.$cookie.get("user_id"))
                }   
            })
            .then(({data}) => {
                // 가져온 유저 데이터 값과 현재 로그인 된 유저 값 비교
                //console.log(data);
                //해당 데이터에서 현재 question_id가 있는지 찾는다.
                if(data != "Resource not bound"){
                    this.chkClicked = true;
                } else {
                    this.chkClicked = false;
                }
            })
            .catch(err => {
                console.log(err.data);
            })
        },
        selectBtn() {
            if(confirm("현재글을 채택 하시겠습니까?") == true){
                http
                    .get('/api/answer/select', {
                        params: {
                            answer_id: this.answer.id
                        }
                    })
                    .then(({data}) => {
                        console.log(data)
                    })
                    .catch(err => {
                        console.log(err)
                    })

                this.$router.go();
            }
            // 현 재글에 selectBtn을 true로 변경하여 채택버튼을 안보이게
            
        },
        getSelectedAnswer() {
            //console.log("현재 질문아이디는 : " + this.questionId)
            http
                // [1] 먼저 해당 포스트에 채택된 답변이 있는지 조회
                .get('/api/answer/selected', {
                    params: {
                        "question_id": this.questionId
                    }
                })
                .then(({data}) => {
                    console.log("채택유무 : " + data);

                    // data is not "Resource not bound"
                    if(data != "Resource not bound"){
                        // 채택이 완료됬다면 채택완료 버튼 보여지기
                        this.selectedAnswer = true;
                    }
                   
                    // 채택된 해당 컴포넌트만 보여지기
                    if(this.answer.id != data.id){
                        //채택된 답변이 아니라면 false로 데이터 변경
                        this.isSelectedAnswer = false
                    }
                })
                .catch(err => {
                    console.log(err);
                })
        },
        googleLogin() {
            window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://localhost:3000/"'
        },
        toggleModal() {
            this.loginModalOn = !this.loginModalOn;
        },
        changeModal() {
            this.loginCheck = !this.loginCheck;
        },
        switchModal() {
            this.changeModal()
            this.toggleModal()

        },
        parseDateString(date) {
            let year = date.slice(0,4);
            let month = date.slice(5,7);
            let day = date.slice(8,10);
            let hour = date.slice(11,13);
            let minute = date.slice(14,16);
            return year + '년 ' + month + '월 ' + day + '일 ' + hour + '시 ' + minute + '분';
        }
    },
    created() {
        // alert(this.isLoggedIn)
        this.fetchComments(this.answer.id);
        // 글 작성자 id
        this.getUserId();
        // 좋아요 상태
        this.loadLikeState()
        // 채택 유무
        this.getSelectedAnswer() 
    }
}
</script>

<style scoped>
.detailanswer {
    background:white;
    border-radius: 10px;
    padding: 15px;
    margin-top: 10px;
}
.q-title {
  padding: 10px 0;
  /* border-bottom: 1px solid #eeeeee; */
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
.like-button {
    height: 30px;
    width: 60px;
    margin-left: auto;
    display: flex;
    flex-direction: row;
    color: Tomato;
    border: 1px solid #ccc;
    border-radius: 15px;
    text-align: center;
    align-items: center;
    justify-content: center;
}
.like-button:hover{
    background: #eee;
    color: #FF4500; 
}
.answer_select {
    height: 30px;
    width: 80px;
    margin-left: auto;
    display: flex;
    flex-direction: row;
    color: green;
    border: 1px solid #ccc;
    border-radius: 35px;
    text-align: center;
    align-items: center;
    justify-content: center;
    background-color: white;
}
.answer_select:hover {
    background-color: whitesmoke;
    cursor: pointer;
}
.answer_selected {
    height: 30px;
    width: 80px;
    margin-left: auto;
    display: flex;
    flex-direction: row;
    color: white;
    border: 1px solid #ccc;
    border-radius: 35px;
    text-align: center;
    align-items: center;
    justify-content: center;
    background-color: green;
}
.answer_selected:hover {
    cursor: none;
    background-color: green;
}
.editBtn,
.delBtn {
    border: 1px solid #ccc;
    border-radius: 5px;
    width: 50px;
    height: 25px;
    text-align: center;
    margin: 2px; 
    background: white;
    vertical-align: center;
    padding: 0;
}
.delBtn:hover {
    color: #eee;
    background: rgb(243, 138, 152);
}
.editBtn:hover {
    color: #eee;
    background: #ccc;
}
.commentInputSection {
    width: 98%;
    margin: 0 auto 0;
    display: flex;
    flex-direction: row;
}
.commentSection {
    width: 98%;
    margin: 0 auto 0;
    display: flex;
    flex-direction: column;
    
    /* box-shadow: 0px 3px 4px rgba(0,0,0,.2); */
    /* padding: 0 10px; */
}
.writeBtn {
    border: 1px solid #ccc;
    border-radius: 0px 2px 2px 0px;
    width: 70px;
    align-items: center;
    justify-content: center;
    height: 40px;
    border-left: 0;
}
</style>