<template>
    <div id="searchInput">
        <div class="pt-4">
            <!-- main search view-->
            <b-input-group size="lg" class="w-75">
                <b-input-group-prepend>
                    <span class="input-group-text">
                        <b-icon-search font-scale="1"></b-icon-search>
                    </span>
                </b-input-group-prepend>
                <b-form-input
                    v-model="word"
                    v-on:keyup.enter="searchItem" 
                    placeholder="Search">
                </b-form-input>
                
            </b-input-group>
        </div>
    </div>
</template>

<script>
import {mapState,mapMutations} from 'vuex'

export default {
    name: "searchInput",
    data(){
        return {
            word : this.keyword
        };
    },
    computed:{
        ...mapState({
            keyword : state => state.keyword
        })
    },  
    methods: {
        ...mapMutations([
            'setKeyword'
        ]),
        searchItem() {
            this.setKeyword(this.word);
            this.$router.push({
                name:'Board'
            })
            // localStorage.setItem(this.inputValue, this.searchItem);
        }
    }
}
</script>