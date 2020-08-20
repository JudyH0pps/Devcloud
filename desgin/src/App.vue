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
    <div class="wave wave1"></div>
    <div class="wave wave2"></div>
    <div class="wave wave3"></div>
    <div class="wave wave4"></div>
    <Footer/>
  </div>
</template>

<script>
import HeadBar from '@/components/HeadBar.vue'

import Footer from '@/components/Footer.vue'
import {mapState,mapActions,mapMutations} from 'vuex'

export default{
  name: 'App',
  components: {
    HeadBar,
    Footer,
    
  },
  
  methods: {
    ...mapActions('user',['fetchMyProfile']),
    ...mapActions('notification',['fetchUserNotifications']),
    ...mapMutations('user',['setisLoggedIn']),
    
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
    checkNotification(){
      // alert("timer test")
      this.$cookie.set('timerid',setTimeout(this.checkNotification,1000),'1h');
      if(this.$cookie.get('user_id'))
        this.fetchUserNotifications(this.$cookie.get('user_id'))
    },
    stopCheckNotification(){
      if(this.$cookie.get('timerid') != null)
        clearTimeout(this.$cookie.get('timerid'));
    }
  },
  created() {
    let token = this.getParameters('token')
    // // 토큰이 있으면
    if(token !== undefined){
        let temp = token.split('#')[0];
        // localStorage.setItem('localToken', token);
        this.$cookie.set('logintoken',temp, '1h');
        
        //this.loginTokened = true;
        this.fetchMyProfile(this.$cookie.get('logintoken'));
        
        //this.$cookie.set('user_id',this.user.id,'1h');
        this.$router.push({'name':'Home'});
    }
    if(this.$cookie.get('logintoken')){
      this.checkNotification()
      this.setisLoggedIn(true)
      // alert(this.isLoggedIn)
    }else{
      this.stopCheckNotification()
    }
  },
  computed: {
    ...mapState({
      isLoggedIn : state => state.user.isLoggedIn,
      //isNotification : state => state.notification.notifications
    })
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
  background: rgb(202, 237, 248);
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

.tag {
    font-size: 14px;
    margin: 1px;
    padding: 3px 5px;
    border-radius: 12px;
    border: 1px solid #e1ecf4;
    background: #e1ecf4;
    color:#60829b;
    font-weight: 400;
    text-transform: uppercase;
    margin: 10px 6px 0px 0;
}
.tag:hover {
    background: #d0eafd;
    cursor: pointer;
}
.wave {
  position: fixed;
  bottom: 60px;
  left: 0;
  width: 100%;
  height: 100px;
  background: url('./assets/wave.png');
  background-size: 1000px 100px;
}
.wave.wave1 {
  animation: animate 10s linear infinite;
  /* z-index: 100; */
  opacity: 1;
  animation-delay: 0s;
  bottom: 0;
}
.wave.wave2 {
  animation: animate2 15s linear infinite;
  z-index: 99;
  opacity: 0.5;
  animation-delay: -5s;
  bottom: 10px;
}
.wave.wave3 {
  animation: animate 15s linear infinite;
  z-index: 98;
  opacity: 0.2;
  animation-delay: -2s;
  bottom: 15px;
}
.wave.wave4 {
  animation: animate2 10s linear infinite;
  z-index: 97;
  opacity: 0.7;
  animation-delay: 0s;
  bottom: 20px;
}
@keyframes animate {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: 1000px;
  }
}
@keyframes animate2 {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: -1000px;
  }
}
</style>
