<template>    
    <header>
      <router-link :to="{ 'name':'Home' }">
        <p class="logo">Dev<span class="red">C</span>loud
        </p>
      </router-link>
      <HeadSearchBar v-show="this.$store.state.searchBarinHeadbar"/>
      <div>
        <ul>
          <!-- <router-link :to="{ 'name':'Home' }">
            <li class="underline">Home</li>
          </router-link> -->
          <router-link :to="{ 'name':'Search' }">
            <li class="underline">Search</li>
          </router-link>
          <router-link :to="{ 'name':'Board' }">
            <li class="underline">Board</li>
          </router-link>
          <router-link :to="{ 'name':'Home' }">
            <li class="underline">Ranking</li>
          </router-link>
          <router-link :to="{ 'name':'Profile' }">
            <li class="underline">Profile</li>
          </router-link>
          <li class="loginbutton" v-if="!loginTokened" @click="toggleModal">
            <i class="fas fa-power-off fa-2x"></i>
            <small>login</small>
          </li>
          <img 
            alt="profile picture"
            class="border rounded-circle profile-picture"
            :src="userImage"
            style="width: 48px; height: 48px;">
          <LoginModal @googleLogin="googleLogin" :loginModalOn="loginModalOn" @toggleModal="toggleModal"/>
        </ul>
      </div>
    </header>
</template>

<script>
import LoginModal from '@/components/LoginModal.vue'
import HeadSearchBar from '@/components/HeadSearchBar.vue'
import {mapState,mapMutations,mapActions} from 'vuex'

export default{
    name: 'HeadBar',
    components: {
        LoginModal,
        HeadSearchBar
    },
    data() {
        return {
          loginModalOn: false,
          localTokend: false,
          showModal: false,
          userImage : '',
          loginTokened: false,
        }
    },
    computed:{
      ...mapState({
          keyword : state => state.keyword,
          user : state => state.user.user,
      }),
      word :{
          get(){
              return this.keyword
          },
          set(val){
              this.setKeyword(val)
          }
      },
      // loginTokened(){
      //     if(this.$cookie.get('logintoken'))
      //         return true;
      //     else
      //         return false;
      // },
    },
    methods: {
      ...mapActions('user',['fetchUserProfile']),
      ...mapMutations(['setKeyword']),
      toggleModal() {
        // alert('바꾸자')
        this.loginModalOn = !this.loginModalOn;
      },
      // signInBtn() {
      //   this.showModal = true;
      // },
      googleLogin() {
        // alert('asdfsad21312f');
        // alert('asdfsadf');
        window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://localhost:3000/"'
        // alert("구글로그인 창으로 이동합니다.");
        this.loginModalOn = !this.loginModalOn;
      },
      // loginNo() {
      //     this.showModal = false;
      // },
      signOutBtn() {
          alert("로그아웃 되었습니다.");
          this.loginTokened = false;

          // 로컬 스토리지 토큰 제거
          //localStorage.removeItem('localToken');
          this.$cookie.delete("logintoken");
          this.$cookie.delete("user_id");
          this.$router.push({name:'Home'});
          this.$router.go();
      },

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
      search(){
          //alert(this.word)
          // this.setKeyword(this.word)
          //alert(this.keyword)
          this.$router.push({
              name:'Board'
          })
      }
    },
    created() {
        let token = this.getParameters('token');
        // 토큰이 있으면
        if(token !== undefined){
            // localStorage.setItem('localToken', token);
            this.$cookie.set('logintoken',token, '1h');
            this.loginTokened = true;
            // alert(this.loginTokened)
            this.fetchUserProfile(this.$cookie.get('logintoken'));
            // this.$cookie.set('user_id',this.user.id,'1h');
        }
        // if(localStorage.getItem('localToken') != null){
        //     this.loginTokened = true;
        // }
        // let temp_token = link.slice(link.indexOf('?') + 1, link.length);
        // let token = temp_token.slice(temp_token.indexOf('=') + 1, temp_token.length);
        
        // console.log("토큰"+ token);
        // var test_localToken = localStorage.getItem('localToken');
        //var test_localToken = this.$cookie.get('logintoken');
    }
}
</script>

<style scoped>
p {
  font-weight: bold;
  text-decoration: none;
  position: relative; 
  font-weight: 700;
  color: #000;
  text-decoration: none;
  font-size: 2em;
  text-transform: uppercase;
  letter-spacing: 2px;
  transition: 0.6s;
  margin: 0;
}

a:hover {
  color: white;
  text-decoration: none;
}

header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: 0.6s;
  /* padding: 30px 80px; */
  padding: 15px 80px;
  z-index: 100;
  min-width: 1024px;
}
header.sticky {
  padding: 10px 80px;
  background: #000;
  box-shadow: 0px 5px 5px rgba(0,0,0,.5);
  /* background: rgb(202, 237, 248); */
}
header .logo {
  position: relative; 
  font-weight: 700;
  color: #000;
  text-decoration: none;
  font-size: 2em;
  text-transform: uppercase;
  letter-spacing: 2px;
  transition: 0.6s;
}
header ul {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  height: 50%;
}
header ul li {
  position: relative;
  list-style: none;
}
header ul li {
  position: relative;
  margin: 0 15px;
  text-decoration: none;
  color: #000;
  letter-spacing: 2px;
  font-weight: 500px;
  transition: 0.6s;
}
header.sticky .logo,
header.sticky ul li{
  color: #fff;
  /* color: #000; */
}
.red{
  color: rgb(255, 0, 0);
}
.underline::after {
  content: '';
  position: relative;
  top: 2px;
  display: block;
  width: 0;
  height: 2px;
  background: #000;
  transition: width .3s;
}
.sticky .underline::after{
  background: #fff;
}
.underline:hover::after {
  position: relative;
  top: 2px;
  width: 100%;
  transition: width .3s;
}
.loginbutton {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  cursor: pointer;
  text-align: center;
}
.loginbutton:hover i{
  transform: rotate(360deg);
  transition: .5s;
}
</style>