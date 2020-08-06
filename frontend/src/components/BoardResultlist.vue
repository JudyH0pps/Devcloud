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
                            <b-card-text v-html="$options.filters.highlights1(question.title, keyword)" style="font-size: 30px;">
                                {{question.title}}

                            </b-card-text>
                            <hr>
                            <b-card-text v-html="$options.filters.highlights2(question.content, keyword)" style="font-size: 18px;">
                                {{question.content}}
                            </b-card-text>

                            <div style="margin-top: 30px; width: 100%; height: 60px;">
                                <div style="width: 50%; margin: 0;">                   
                                    <img 
                                        alt="profile picture"
                                        class="border rounded-circle profile-picture"
                                        :src="question.user.imageUrl"
                                        style="width: 35px; height: 35px; display: inline; padding: 0px;"
                                        align: left>
                                    <span>
                                        {{question.user.name}}
                                    </span>
                                </div>
                            </div>

                            <div style="margin-top: 10px">
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
import Vue from 'vue';
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
    props: {
        keyword: String,
    },
    computed:{
        ...mapState({
            questions : state => state.question.questions,
        }),
        // 하이라이팅
    },    
    created() {
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

//title
Vue.filter("highlights1", function(item, keyword){
    if(keyword === "") return item;

    // 정규표현식
    var iQuery = new RegExp(keyword, "ig");
    // 해당 키워드 하이라이트
    return item.toString().replace(iQuery, matchedTxt => {
        return "<span class='highlight'>" + matchedTxt + "</span>";
    });
});
//content
Vue.filter("highlights2", function(item, keyword){
    if(keyword === "") return item;

    // 정규표현식
    var iQuery = new RegExp(keyword, "ig");
    // 해당 키워드 하이라이트
    return item.toString().replace(iQuery, matchedTxt => {
        return "<span class='highlight'>" + matchedTxt + "</span>";
    });
});
</script>





<style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');


*{
    font-family: 'Jua', sans-serif;
}
.highlight {
    background-color: yellow;
}
</style>