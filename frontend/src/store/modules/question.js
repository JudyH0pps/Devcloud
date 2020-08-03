import http from "@/util/http-common";
import router from '@/router'

export default {
    namespaced: true,

	state: {
        questions:[],
        question:{},
    },
	mutations: {
        setQuestions(state, payload){
            state.questions= payload;
        },
        setQuestion(state,payload){
            state.question = payload;
        },
    },
	actions: {
        getUserQuestions(context,payload){
            http
                .get('/api/questions',{
                    params:{
                        user_id : payload
                    }
                })
                .then(({data}) => {
                    // console.log(data)
                    context.commit('setQuestions',data);
                    // console.log(data['data']);
                })
                .catch((err) =>{
                    console.log(err);
                    alert('유저질문 조회중 오류 발생');
                });
        },
        getQuestion(context, qid) {
            http
                .get('/api/questions', {
                    params: {
                        "id": qid
                    }
                })
                .then(res => {
                    context.commit('setQuestion', res.data[0])
                })
                .catch(err => console.log(err.response))
        },
        postQuestion(context, questionData) {
            if (questionData.title==='' || questionData.content==='' || questionData.user_id==='') {
                alert('제목과 내용을 입력해주세요!')
            }   else {
                http
                    .post('/api/questions', questionData)
                    .then(() => router.push({name: 'board'}))
                    .catch(err => console.log(err.response))
            }
        },
        editQuestion(context, questionData) {
            if (questionData.title==='' || questionData.content==='' || questionData.user_id==='') {
                alert('제목과 내용을 입력해주세요!')
            }   else {
                http
                    .put('/api/questions', questionData)
                    .then(() => {
                        router.push({name: 'Detail'})
                    })
                    .catch(err => console.log(err.response))
            }
        },
        goEditQuestion() {
            router.push({name: 'Edit'})
        },
        fetchQuestionByQid(context, qid){
            http
                .get('/api/questions',{
                    params:{
                        qid : qid
                    }
                })
                .then(({data})=>{
                    context.commit('setQuestion',data)
                })
                .catch((err) => {
                    console.log(err);
                });
        },
        fetchQuestionsByKeyword(context,keyword){
            http
                .get('/api/questions',{
                    params:{
                        keyword : keyword
                    } 
                })
                .then(({data}) =>{
                    context.commit('setQuestions',data)
                })
                .catch((err) =>{
                    console.log(err);
                    alert("검색중 오류발생")
                });
        },
        deleteQuestion(context, qid) {
            http
                .delete('/api/questions', {
                    params: {
                        "id": qid
                    }
                })
                .then(() => {
                    context.commit('setQuestion', {})
                    router.push({name: 'board'})
                })
                .catch(err => console.log(err.response))
        } 
    },
	getters: {},
}