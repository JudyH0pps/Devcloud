<template>
    <!-- <div class="wrapper"> -->
    <div class="search_box">
        <input type="text" v-model="keyword"  @keypress.enter="searchItem" placeholder="what are you looking for?" onfocus="this.placeholder=''" onblur="this.placeholder='what are you looking for?'">
        <i class="fas fa-search"></i>
    </div>
    <!-- </div> -->
</template>

<script>
export default {
  name: 'HeadSearchBar',
  data() {
    return {
      setKeyword: '',
    }
  },
  computed:{
        keyword:{
            get(){
                if(this.$route.name == 'Search')
                    return this.$route.params.search_keyword
                else
                    return null
            },
            set(val){
                return this.setKeyword = val
            }
        },
  },
  methods: {
    searchItem() {
      if(this.setKeyword != ""){
                this.$router.push({
                    name:'Search',
                    params:{
                        search_keyword : this.setKeyword
                    }
                }).catch(()=>{
                    if(this.setKeyword != ""){
                        this.$router.go()
                    }
                });
      }else{ 
        // 검색어가 같아서 새로고침 되는 경우 this.setKeyword 가 ""인경우
          if(this.keyword == this.setKeyword)
              this.$router.go()
      }
    },
  },
  created(){
    const len = window.location.href.split('/').length
    this.setKeyword = window.location.href.split('/')[len-1]
  }
}
</script>

<style scoped>
*{
  margin: 0;
  padding: 0;
}

.search_box{
  width: 45%;
  position: relative;
  -moz-box-shadow:    3px 3px 3px 3px #ccc;
  -webkit-box-shadow: 3px 3px 3px 3px #ccc;
  box-shadow:         2px 2px 1px 1px #ccc;
  border: 1px solid #fff;
  border-radius: 40px;
}

.search_box input[type="text"]{
  width: 100%;
  padding: 12px;
  padding-right: 60px;
  box-sizing: border-box;
  background: rgba(0,0,0,0.3);
  border: 2px solid #fff;
  border-radius: 40px;
  font-size: 18px;
  color: #fff;
  outline: none;
}

.fa-search{
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: 25px;
  color: #fff;
  font-size: 25px;
}

::-webkit-input-placeholder {
  /* Chrome/Opera/Safari */
  color: #fff;
}
::-moz-placeholder {
  /* Firefox 19+ */
  color: #fff;
}
:-ms-input-placeholder {
  /* IE 10+ */
  color: #fff;
}

@media screen and (max-width: 425px){
  .search_box{
    width: 95%;
  }
}

</style>