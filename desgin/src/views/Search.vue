<template>
    <section class="search">
        <!-- <h1>검색 결과</h1> -->
        <SearchResultCard v-for="(question, index) in questions.slice().reverse()" :question="question" :key="index"/>
    </section>
</template>

<script>
import SearchResultCard from'../components/SearchResultCard.vue'
import {mapActions, mapState} from 'vuex'

export default{
    name: 'Search',
    components: {
        SearchResultCard,
    },
    data() {
        return {
            // searchresults: [1,2,3,4,5,6,7,8,9],
            word: this.keyword,
        }
    },
    methods:{
        ...mapActions('question',['fetchQuestionsByKeyword']),
        highlight(){
            if(this.word){
                return this.word.replace(new RegExp(this.word, 'gi'), match => {
                    return '<span class="highlighted">' + match + '</span>';
                });
            
            }
        },
        move(qid){
            this.$router.push({
                name:'Detail',
                params:{ "question_id" : qid},
            })
        },
    },
    computed:{
        ...mapState({
            questions : state => state.question.questions
        }),
        searchKeyword(){
			return this.$route.params.search_keyword
        }
    },
    created() {
        // alert(this.keyword)
        this.fetchQuestionsByKeyword(this.searchKeyword)
    },
    watch:{
        searchKeyword : function(){
            this.fetchQuestionsByKeyword(this.searchKeyword)
            this.setKeyword(this.searchKeyword)
        }
    }
}

</script>

<style scoped>
.search {
  /* background: #e9e9e9; */
  /* overflow: hidden; */
  /* padding-top: 20vh; */
  font-family: "Poppins", sans-serif;
  margin: 80px auto 0;
  padding: 10px;
  max-width: 900px;
}
</style>