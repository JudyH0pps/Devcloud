<template>    
    <header>
      <router-link :to="{ 'name':'Home' }">
        <p class="logo">Dev<span class="red">C</span>loud
        </p>
      </router-link>
      <HeadSearchBar v-show="this.$store.state.searchBarinHeadbar"/>
      <div>
        <ul>
            <li class="underline" @click="moveQuestions">Questions</li>
          <!-- </router-link> -->
          <router-link :to="{ 'name':'Ranking' }">
            <li class="underline">Ranking</li>
          </router-link>
          <li class="loginbutton" v-if="!this.$store.state.user.isLoggedIn" @click="toggleModal">
            <i class="fas fa-power-off fa-2x"></i>
            <small>login</small>
          </li>
          <li v-if="this.$store.state.user.isLoggedIn">
            <div class="profile-option" @click="toggleAlarmDropdown">
              <div class="notification">
                <i class="fa fa-bell"></i>
                <span class="alert-message" v-if="notifications.length > 0">{{notifications.length}}</span>
              </div>
            </div>
          </li>
         
         <!-- 알림 목록 표시 -->
          <div class="alarmdropdown" v-show="showAlarmDropdown">
            <div class="alarm-list" v-if="!this.notifications.length">
              <p> 아직 알림이 없습니다. </p>
            </div>
            <div class="alarm-list" v-for="(notification, index) in this.notifications.slice(0,5)" :key="notification.notification_id">
              <p @click="moveToQuestion(notification.question_id,notification.notification_id,index)">{{notification.content}}</p>
              <i class="fas fa-times" @click="closeNotification(notification.notification_id,index)"></i>
            </div>
          </div>

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
import {mapState} from 'vuex'
import http from "@/util/http-common"
//import {mapState,mapActions} from 'vuex'

export default {
  name: 'HeadBar',
  components: {
      LoginModal,
      HeadSearchBar
  },
  computed:{
        ...mapState({
            notifications : state => state.notification.notifications
            // user : state => state.user.user,
        }),
  },
  data() {
      return {
        loginModalOn: false,
        userImage: this.$cookie.get('user_image'),
        showDropdown: false,
        showAlarmDropdown: false,
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
        // 서버용 리다이렉트 주소
        window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://i3c202.p.ssafy.io:3000/"'
        // local용 리다이렉트 주소
        //window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://localhost:3000/"'
        // alert("구글로그인 창으로 이동합니다.");
    },
    navbarSpan() {
      window.addEventListener("scroll", function(){
        let header = document.querySelector("header");
        header.classList.toggle("sticky", window.scrollY > 0);
      })
    },
    toggleAlarmDropdown() {
      this.showAlarmDropdown = !this.showAlarmDropdown;
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
        },
    moveQuestions(){
      this.$router.push({
        name : 'Search',
        }).catch(()=>{
            // 프로필에서 다시 프로필로 이동시에 새로고침 해줌
            this.$router.go()
            document.documentElement.scrollTop = 0;
        })
    },
    async moveToQuestion(question_id,notification_id,index){
      await http.get('/api/notification/read/'+ notification_id)
      this.notifications.splice(index,1);
      this.$router.push({
              'name':'Detail',
              params:{ "question_id" : question_id},
      });
    },
    async closeNotification(notification_id,index){
      await http.get('/api/notification/read/'+ notification_id);
      this.notifications.splice(index,1);
      //console.log(resp.data)
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
.alarmdropdown {
  position: absolute;
  top: 70px;
  right: 0px;
  width: 400px;
  /* margin-left: 10px; */
  padding: 0 10px 0;
  background: white;
  border: 2px solid #eeeeee;
  border-radius: 15px;
}
.alarm-list {
  display: flex;
  align-items: center;
  height: 50px;
  padding: 0 20px;
  justify-content: space-between;
  border-bottom: 1px solid #eee;
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
  /* margin-left: 15px; */
  text-align: center;
}
.profile {
  display: flex;
  flex-direction: column;
  margin: 0 15px;
}
.profile-picture {
  border-radius: 50%;
  /* margin-left: 15px; */
  height: 100%;
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
  cursor: pointer;
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
.loginbutton  {
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
.profile-option {
  width: 40px;
  height: 40px;
  background: red;
  /* position: absolute; */
  /* right: 50px;
  top: 50%; */
  /* transform: translateY(-50%); */
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: all .5s ease-in-out;
  outline: none;
}
.profile-option .notification i {
  color: #fff;
  font-size: 1.2rem;
  transition: all .5s ease-in-out;
}
.profile-option:hover{
  background: #fff;
  border: 1px solid red;
}
.profile-option:hover .notification i {
  color: red;
}
.profile-option .notification .alert-message{
  position: absolute;
  top: -5px;
  right: -5px;
  background: #fff;
  color: red;
  border: 1px solid;
  padding: 5px;
  border-radius: 50%;
  height: 20px;
  width: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: .8rem;
  font-weight: bold;
}
.alarm-list{
  margin : 10px;
}
.alarm-list p:hover {
    cursor: pointer;
    text-decoration: underline;
}
.alarm-list i:hover{
    cursor: pointer;
}
</style>