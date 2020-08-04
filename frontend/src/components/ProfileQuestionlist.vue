<template>
    <!-- <BoardResultlist :questions="questions" /> -->
    <BoardResultlist/>
</template>
<script>
import BoardResultlist from './BoardResultlist.vue'
import {mapState, mapActions} from 'vuex';

export default {
    name: 'ProfileQusetionlist',
    components:{
        BoardResultlist,
    },
    computed:{
        ...mapState({
            user : state => state.user.user,
            questions : state=> state.question.questions
        })
    },
    data () {
        return {
            // questions: [
            //     {
            //         title: "Building a simple REST API with NodeJS and Express.",
            //         content: "Have you been working on front-end ...",
            //         writer: "joonyoung",
            //         created: "Jun 27, 2020",
            //         imageurl: "sample02.gif",
            //     },
            //     {
            //         title: "Building a simple REST API with NodeJS and Express.",
            //         content: "Have you been working on front-end ...",
            //         writer: "joonyoung",
            //         created: "Jun 26, 2020",
            //         imageurl: "sample01.gif",
            //     },
            //     {
            //         title: "Building a simple REST API with NodeJS and Express.",
            //         content: "Have you been working on front-end ...",
            //         writer: "joonyoung",
            //         created: "Jun 25, 2020",
            //         imageurl: "sample03.gif",
            //     },
            //     {
            //         title: "Building a simple REST API with NodeJS and Express.",
            //         content: "Have you been working on front-end ...",
            //         writer: "joonyoung",
            //         created: "Jun 24, 2020",
            //         imageurl: "sample02.gif",
            //     },
            // ]
        }
    },
    methods:{
        ...mapActions('question',['fetchUserQuestions']),
        
        getImages(name) {
            return require('../assets/' + name);
        }
    },
    created(){
        this.fetchUserQuestions(this.$cookie.get('user_id'));
    // 화면이 그려지기전에 로그인된 id로 질문목록을 받아온다 
    // axios.get('http://i3c202.p.ssafy.io:8080/question',{
    //     params:{
    //         ID: user.id
    //     }
    // })
    // .then(result => this.questions = result.data)
    },
 
}
</script>
<style scoped>
.question_card:hover{
    background: #eeeeee;
    cursor: pointer;
}
</style>