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
                    v-model="keyword"
                    v-on:keyup.enter="searchItem" 
                    placeholder="Search">
                </b-form-input>
                
            </b-input-group>
        </div>
    </div>
</template>

<script>

export default {
    name: "searchInput",
    data(){
        return {
            keyword :""
        };
    },
    methods: {
        searchItem() {
            if(this.keyword != "")
            {
                this.$router.push({
                    name:'Board',
                    params:{
                        search_keyword : this.keyword
                    }
                })          
            }
        }
    }
}
</script>