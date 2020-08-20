import http from "@/util/http-common";
import router from '@/router'

export default {
    namespaced: true,

	state: {
        questions: [],
        question: null,
    },
	mutations: {
        setQuestions(state, payload){
            state.questions= payload;
        },
        resetQuestions(state){
            state.questions = [];
        },
        setQuestion(state,payload){
            state.question = payload;
        },
        
    },
	actions: {
        fetchUserQuestions(context,payload){
            http
                .get('/api/question',{
                    params:{
                        user_id : payload
                    }
                })
                .then(({data}) => {
                    // console.log(data)
                    context.commit('setQuestions',data.content);
                    // console.log(data['data']);
                })
                .catch((err) =>{
                    console.log(err);
                    alert('유저질문 조회중 오류 발생');
                });
        },
        async fetchQuestion(context, qid) {
            const resp = await http
                .get('/api/question', {
                    params: {
                        "question_id": qid
                    }
                })
                context.commit('setQuestion',resp.data)
                // .then(({data}) => {
                //     context.commit('setQuestion', data)
                // })
                // .catch(err => console.log(err.response))
        },
        postQuestion(context, questionData) {
            if (questionData.title==='' || questionData.content==='' || questionData.user_id==='') {
                alert('제목과 내용을 입력해주세요!')
            }   else {
                http
                    .post('/api/question', questionData)
                    .then(() => router.push({name: 'Search'}))
                    .catch(err => console.log(err.response))
            }
        },
        editQuestion(context, questionData) {
            if (questionData.title==='' || questionData.content==='' || questionData.question_id==='') {
                alert('제목과 내용을 입력해주세요!')
            }   else {
                http
                    .put('/api/question', questionData)
                    .then(() => {
                        router.go(-1)
                    })
                    .catch(err => console.log(err.response))
            }
        },
        goEditQuestion() {
            router.push({name: 'Edit'})
        },
        fetchQuestionByQid(context, qid){
            http
                .get('/api/question',{
                    params:{
                        question_id : qid
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
            if(keyword != undefined){
                keyword = encodeURIComponent(keyword)
            }
            http
                .get('/api/question',{
                    params:{
                        keyword : keyword
                    } 
                })
                .then(({data}) =>{
                    context.commit('setQuestions',data.content)
                })
                .catch((err) =>{
                    console.log(err);
                    alert("검색중 오류발생")
                });
        },
        fetchQuestionsByTag(context,tag_id){
            http.get('/api/question/tag',{
                params:{
                    tag_id : tag_id
                }
            })
            .then(({data}) => {
                context.commit('setQuestions',data.content)
            })
            .catch((err)=>{
                console.log(err);
                alert("태그검색중 오류발생")
            });
        },
        deleteQuestion(context, qid) {
            http
                .delete('/api/question', {
                    params: {
                        "question_id": qid
                    }
                })
                .then(() => {
                    context.commit('setQuestion', {})
                    // router.push({name: 'Board'})
                    router.go(-1)
                })
                .catch(err => console.log(err.response))
        } 
    },
	getters: {},
}