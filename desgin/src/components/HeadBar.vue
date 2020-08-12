<template>    
    <header>
      <router-link :to="{ 'name':'Home' }">
        <p class="logo">Dev<span class="red">C</span>loud
        </p>
      </router-link>
      <HeadSearchBar v-show="this.$store.state.searchBarinHeadbar"/>
      <div>
        <ul>
          <!-- <router-link :to="{ 'name':'Detail' }">
            <li class="underline">Detail</li>
          </router-link> -->
          <!-- <router-link :to="{ 'name':'Search' }">
            <li class="underline">Search</li>
          </router-link> -->
          <!-- <router-link :to="{ 'name':'Write' }">
            <li class="underline">New Question</li>
          </router-link> -->
          <router-link :to="{ 'name':'Ranking' }">
            <li class="underline">Ranking</li>
          </router-link>
          <li class="loginbutton" v-if="!this.$store.state.user.isLoggedIn" @click="toggleModal">
            <i class="fas fa-power-off fa-2x"></i>
            <small>login</small>
          </li>
          <div class="profile" v-if="this.$store.state.user.isLoggedIn">
            <img 
            alt="profile picture"
            @click="toggleDropdown"
            class="border rounded-circle profile-picture"
            :src="userImage"
            style="width: 48px; height: 48px;">
            <i class="fas fa-caret-down"></i>
            <div class="dropdown" v-show="showDropdown">
              <!-- <router-link :to="{ 'name':'Profile' }"> -->
              <li class="dropdown-list" @click="moveProfile">MyProfile</li>
              <!-- </router-link> -->
              <li class="dropdown-list" @click="signOutBtn">Logout</li>
            </div>
          </div>
          <LoginModal @googleLogin="googleLogin" :loginModalOn="loginModalOn" @toggleModal="toggleModal"/>
        </ul>
      </div>
    </header>
</template>

<script>
import LoginModal from '@/components/LoginModal.vue'
import HeadSearchBar from '@/components/HeadSearchBar.vue'
//import {mapState,mapActions} from 'vuex'

export default {
  name: 'HeadBar',
  components: {
      LoginModal,
      HeadSearchBar
  },
  // computed:{
  //       ...mapState({
  //           //keyword : state => state.keyword,
  //           user : state => state.user.user,
  //       }),
  // },
  data() {
      return {
        loginModalOn: false,
        userImage: this.$cookie.get('user_image'),
        showDropdown: false,
      }
  },
  methods: {
    //...mapActions('user',['fetchMyProfile']),

    signOutBtn() {
        this.toggleDropdown();
        alert("로그아웃 되었습니다.");
        this.loginTokened = false;

        // 로컬 스토리지 토큰 제거
        //localStorage.removeItem('localToken');
        this.$cookie.delete("logintoken");
        this.$cookie.delete("user_id");
        this.$router.push({name:'Home'});
        this.$router.go();
    },
    googleLogin() {
        // alert('asdfsad21312f');
        // alert('asdfsadf');
        window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://localhost:3000/"'
        // alert("구글로그인 창으로 이동합니다.");
    },
    navbarSpan() {
      window.addEventListener("scroll", function(){
        let header = document.querySelector("header");
        header.classList.toggle("sticky", window.scrollY > 0);
      })
    },
    toggleModal() {
      // alert('바꾸자')
      this.loginModalOn = !this.loginModalOn;
    },
    toggleDropdown() {
      this.showDropdown = !this.showDropdown;
    },
    moveProfile(){
            this.$router.push({
                name : 'Profile',
                params : {
                    user_id : this.$cookie.get('user_id')
                }
            }).catch(()=>{
                // 프로필에서 다시 프로필로 이동시에 새로고침 해줌
                this.$router.go()
            })
            this.toggleDropdown()
        }
  },
  mounted() {
    this.navbarSpan();
    // alert(this.$store.state.isLoggedIn);
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
  font-size: 15px;
  /* text-transform: uppercase; */
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
.dropdown {
  position: absolute;
  top: 70px;
  right: -42px;
  /* margin-left: 10px; */
  padding: 0 10px 0;
  background: white;
  border: 2px solid #eeeeee;
  border-radius: 15px;
}
.dropdown-list {
  margin: 0;
  padding: 10px;
  cursor: pointer;
  margin-left: auto;
  margin-right: auto;
}
.dropdown-list:hover {
  color: #b8b8b8;
}
.fas {
  margin-left: 15px;
  text-align: center;
}
.profile {
  display: flex;
  flex-direction: column;
}
.profile-picture {
  border-radius: 50%;
  margin-left: 15px;
  cursor: pointer;
  /* box-shadow: 0px 5px 10px rgba(0,0,0,.2); */
}
header.sticky {
  padding: 10px 80px;
  background: #fff;
  box-shadow: 0px 3px 5px rgba(0,0,0,.5);
  /* box-shadow: 0px 5px 10px rgba(0,0,0,.2); */
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
  /* color: #fff; */
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
  /* background: #fff; */
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