<template>
            <div class="pb-4 mb-4">
                <div class="mt-3 mb-4">
                    <h2>Questions</h2>
                </div>

                <div align="left" style="height: 100px;">
                        <!-- :img-src="getImages(question.avatar)" -->
                        <!-- :img-src="question.avatar" -->
                    <b-card
                        :img-src="getImages('sample02.gif')"
                        img-left
                        v-for="(question, index) in questions.slice().reverse()"
                        :key="index"
                        img-width= "400"
                        img-height= "280"
                        @click="move(question.id)"
                    >
                        
                        <!-- image issue -->
                        <div style="width: 70%; float: left">
                            <b-card-text>
                                <h2>
                                    {{question.title}}
                                </h2>
                            </b-card-text>
                            <hr>
                            <b-card-text>
                                <h4>
                                    {{question.content}}
                                </h4>
                            </b-card-text>

                            <b-card-text>
                                author: {{question.user.name}}
                            </b-card-text>

                            <div style="margin-top: 50px">
                                <b-icon-tag>tag1</b-icon-tag>
                                <b-icon-tag>tag2</b-icon-tag>
                                <b-icon-tag>tag3</b-icon-tag>
                            </div>
                        </div>
                        <div style="width: 30%; float: right; text-align: center">
                            <img src="@/assets/sun-icon.png"/>
                            <h1 style="margin-top: 50px">0</h1>
                        </div>

                    </b-card>

                    
                </div>
            </div>
</template>

<script>
import {mapState} from 'vuex';
//import axios from "axios";

export default {
    name: 'BoardResultlist',
    components: {
    },
    data() {
        return {
            likes: 0,
        }
        
    },
    // props: {
    //         questions: Array,
    //     },
    computed:{
        ...mapState({
            questions : state => state.question.questions
        })
    },    
    created() {
        // console.log(this.questions);
    },
    methods: {
        getImages(name) {
            return require('../assets/' + name);
        },
        move(qid){
            this.$router.push({
                name:'Detail',
                params:{ "question_id" : qid},
            })
        },
    },
}
</script>


<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');


*{
    font-family: 'Jua', sans-serif;
}
</style>