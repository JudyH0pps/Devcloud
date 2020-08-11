<template>
    <div class="wrapper">
    <div class="search_box">
        <input type="text" v-model="keyword"  @keypress.enter="searchItem" placeholder="what are you looking for?" onfocus="this.placeholder=''" onblur="this.placeholder='what are you looking for?'">
        <i class="fas fa-search"></i>
    </div>
    </div>
</template>

<script>
// import {mapActions} from 'vuex'

export default {
  name: 'SearchBar',
  data() {
    return {
      keyword : '',
    }
  },
  computed:{
  },  
  methods: {
    searchItem() {
      if(this.keyword != "")
      {
        this.$router.push({
          name:'Search',
          params:{
              search_keyword : this.keyword
          }
        }) 
        this.keyword = '';         
      }
    }
  }
}

</script>

<style scoped>
*{
  margin: 0;
  padding: 0;
}

.wrapper{
  width: 100%;
  height: 100vh;
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
}

.search_box{
  width: 50%;
  position: relative;
  -moz-box-shadow:    3px 3px 3px 3px #ccc;
  -webkit-box-shadow: 3px 3px 3px 3px #ccc;
  box-shadow:         3px 3px 2px 1px #ccc;
  border: 2px solid #fff;
  border-radius: 40px;
}

.search_box input[type="text"]{
  width: 100%;
  padding: 15px;
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