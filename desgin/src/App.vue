<template>
  <div id="app">
    <!-- <section class="banner"> -->
    <HeadBar/>
    <!-- </section> -->
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div> -->
    <router-view class="botmargin400px"/>
    <Footer/>
  </div>
</template>

<script>
import HeadBar from '@/components/HeadBar.vue'
import Footer from '@/components/Footer.vue'

export default{
  name: 'App',
  components: {
    HeadBar,
    Footer
  },
  methods: {
    getParameters(paramName) { 
      // 리턴값을 위한 변수 선언 
      var returnValue; 
      // 현재 URL 가져오기 
      var url = location.href; 
      // get 파라미터 값을 가져올 수 있는 ? 를 기점으로 slice 한 후 split 으로 나눔 
      var parameters = (url.slice(url.indexOf('?') + 1, url.length)).split('&'); 
      // 나누어진 값의 비교를 통해 paramName 으로 요청된 데이터의 값만 
      for (var i = 0; i < parameters.length; i++) { 
        var varName = parameters[i].split('=')[0]; 
        if (varName.toUpperCase() == paramName.toUpperCase()) {
            returnValue = parameters[i].split('=')[1];
            return decodeURIComponent(returnValue); 
        } 
      } 
    },
  },
  created() {
    // let token = this.getParameters('token')
    // // 토큰이 있으면
    // if(token !== undefined){
    //     // localStorage.setItem('localToken', token);
    //     this.$cookie.set('logintoken',token, '1h');
    //     //this.loginTokened = true;
    //     this.fetchUserProfile(this.$cookie.get('logintoken'));
    //     // this.$cookie.set('user_id',this.user.id,'1h');
    //     this.$router.push({'name':'Home'});
    // }
  },
}

</script>

<style>
#app {
  font-family: 'Poppins', sans-serif;
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  min-width: 1024px;
}

#nav {
  padding: 30px;
}

body::-webkit-scrollbar{
    width: 0.25rem;
}

body::-webkit-scrollbar-track{
    background: #ffffff;
}

body::-webkit-scrollbar-thumb{
    background: #77b2d4;
}

a {
  text-decoration: none;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  background: white;
}

/* .banner {
  position: relative;
  width: 100%;
  height: 100%;
} */

/* .botmargin400px {
  margin-bottom: 400px !important;
} */
</style>
