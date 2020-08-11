<template>
    <section class="search">
        <!-- <h1>검색 결과</h1> -->
        <SearchResultCard v-for="(question, index) in questions.slice().reverse()" :question="question" :key="index"/>
        <button @click="moveToWrite">새 질문 작성</button>
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
        moveToWrite() {
            this.$router.push({ 'name' : 'Write' });
        }
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
  min-height: 100vh;
}
button {
  /* float: right; */
  position: fixed;
  right: 150px;
  bottom: 80px;
  /* transform: translate(-50%, 0); */
  /* right: -50%; */
  width: 120px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  text-transform: uppercase;
  text-decoration: none;
  font-family: sans-serif;
  box-sizing: border-box;
  background: linear-gradient(90deg, #03a9f4,#f441a5,#ffeb3b,#03a9f4);
  background-size: 400%;
  border-radius: 30px;
  border: 0;
  outline: 0;
}
button:hover{
  animation: animate 8s linear infinite;
  cursor: pointer;
}
button:hover::before{
  filter: blur(20px);
  /* z-index: -1; */
  opacity: 1;
  animation: animate 8s linear infinite;
}
button::before {
  content: '';
  position: absolute;
  z-index: -1;
  top: -5px;
  left: -5px;
  right: -5px;
  bottom: -5px;
  background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);
  background-size: 400%;
  border-radius: 40px;
  opacity: 0;
  transition: 0.5s;
}
</style>