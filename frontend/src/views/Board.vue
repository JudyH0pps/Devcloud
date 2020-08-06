<template>
    <div id="board">
        <div class="container">
            <!-- 상단 글 작성 버튼 -->
            <!-- 로그인 후에 입력 가능 -->
            <div class="pl-4 pr-4 pt-4 pb-4 mt-4 mb-4">
                <div class="pl-3 pr-3 pt-3 pb-3">
                    <!-- 
                        default: "Top Questions"
                        Searched: "Search Results"
                    -->
                    <div class="d-inline-block float-left">
                        <h2>All Questions</h2>
                    </div>
                    <div class="d-inline float-right">
                        <button @click="$router.push({name: 'WriteQuestion'})" class="btn btn-info">
                            Ask Question
                        </button>
                    </div>
                </div>

                <div class="pl-2 pr-2 pt-3 pb-3 mt-5">
                    <div align="right">
                        <b-button pill variant="outline-primary">Newest</b-button>
                        <b-button pill variant="outline-secondary">Daily</b-button>
                        <b-button pill variant="outline-success">Weekly</b-button>
                        <b-button pill variant="outline-danger">Monthly</b-button>
                    </div>
                </div>
            </div>
            <hr>
            <div v-html="highlight()"></div>
            <BoardResultlist/>
        </div>
    </div>
</template>

<script>
import BoardResultlist from '../components/BoardResultlist.vue'
import {mapState, mapActions} from 'vuex'
export default {
    computed:{
        ...mapState({
            questions : state => state.question.questions
        }),
        searchKeyword(){
			return this.$route.params.search_keyword
		}
    },
    name: 'Board',
    components: {
        BoardResultlist,
    },
    data(){
        return {
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
        }
    },
    created(){
        this.fetchQuestionsByKeyword(this.searchKeyword)
    },
    watch:{
        searchKeyword : function(){
            alert(this.searchKeyword + "로 검색합니다.")
            this.fetchQuestionsByKeyword(this.searchKeyword)
            this.setKeyword(this.searchKeyword)
        }
    }
}
</script>

<style>
.highlighted {
    color: white;
    background-color: orangered;
}
</style>