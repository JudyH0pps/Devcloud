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
            
        }
    },
    methods:{
        ...mapActions('question',['fetchUserQuestions']),
        
        getImages(name) {
            return require('../assets/' + name);
        }
    },
}
</script>
<style scoped>
.question_card:hover{
    background: #eeeeee;
    cursor: pointer;
}
</style>