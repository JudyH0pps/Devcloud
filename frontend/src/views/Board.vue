<template>
    <div id="board">
        <div class="vld-parent">
            <loading :active.sync="isLoading" 
            :can-cancel="true" 
            :on-cancel="onCancel"
            :is-full-page="fullPage"
            color=#5ABEFF
            :width="128"
            :height="128"
            >
            </loading>
            

            <!--<label><input type="checkbox" v-model="fullPage">Full page?</label>-->
            <!--<button @click.prevent="doAjax">fetch Data</button>-->
        </div>
        <div class="container-xl">
            <!-- 상단 글 작성 버튼 -->
            <!-- 로그인 후에 입력 가능 -->
            <div class="pl-4 pr-4 pt-4 pb-4 mt-4 mb-4">
                <div class="pl-3 pr-3 pt-3 pb-3">
                    <!-- 
                        default: "Top Questions"
                        Searched: "Search Results"
                    -->
                    
                    <div class="d-inline-block float-left" v-if="this.searchKeyword">
                        <h2>Search Result: <span style="text-decoration: underline; color: gray; font-weight: none">{{this.searchKeyword}}</span></h2>
                    </div>
                    <div class="d-inline-block float-left" v-else>
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
            <BoardResultlist :keyword="this.searchKeyword" />
        </div>
    </div>
</template>

<script>
import BoardResultlist from '../components/BoardResultlist.vue'
import {mapState, mapActions} from 'vuex'

// Import component
import Loading from 'vue-loading-overlay';
// Import stylesheet
import 'vue-loading-overlay/dist/vue-loading.css';
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
        Loading,
    },
    data(){
        return {
            isLoading: false,
            fullPage: true
        }
    },
    methods:{
        ...mapActions('question',['fetchQuestionsByKeyword']),

        doAjax() {
            this.isLoading = true;
            // simulate AJAX
            setTimeout(() => {
                this.isLoading = false
            },800)
        },
        onCancel() {
            console.log('User cancelled the loader.')
        },
    },
    created(){
        this.fetchQuestionsByKeyword(this.searchKeyword),
        this.doAjax()
    },
    watch:{
        searchKeyword : function(){
            this.fetchQuestionsByKeyword(this.searchKeyword)
            this.setKeyword(this.searchKeyword)
        }
    }
}
</script>

<style>

</style>