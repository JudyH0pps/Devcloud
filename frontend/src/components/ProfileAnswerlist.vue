<template>
    <div class="container mt-2">
        <div class="row">
            <div class="cardcase m-0 p-1 col-6" v-for="answer in answers" :key="answer.aid" style="width: 18rem;">
            <div :to="{ name:'Home' }"  class="card" @click="move(answer.question.id)">
                <div class="card-body pl-3 pr-3 pb-0">
                <h5 class="text-left">Q.</h5>
                <p class="card-text text-left">{{answer.question_id}}</p>
                </div>
                <div class="card-body pl-3 pr-3 pt-0">
                <h5 class="text-left text-right">A.</h5>
                <p class="card-text text-truncate text-right">{{ answer.content }}</p>
                </div>
            </div>
            </div>  
        </div>
    </div>
</template>

<script>
import {mapState} from 'vuex';

export default {
    name: 'ProfileAnswerlist',
    data: () => {
        return {
    
        }
    },
    computed:{
        ...mapState({
            answers : state=> state.answer.answers
        })
    },
    methods:{
        move(qid){
            this.$router.push({
                name:'Detail',
                params:{ "question_id" : qid},
            })
        }
    }
}

</script>

<style scoped>

.cardcase:hover{
    background: #eeeeee;
    cursor: pointer;
}

</style>