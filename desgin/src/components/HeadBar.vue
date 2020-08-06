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
          <!-- <router-link :to="{ 'name':'Search' }">
            <li class="underline">Search</li>
          </router-link> -->
          <router-link :to="{ 'name':'Board' }">
            <li class="underline">Board</li>
          </router-link>
          <router-link :to="{ 'name':'Ranking' }">
            <li class="underline">Ranking</li>
          </router-link>
          <router-link :to="{ 'name':'Profile' }">
            <li class="underline">Profile</li>
          </router-link>
          <li class="loginbutton" v-if="!this.$store.state.isLoggedIn" @click="toggleModal">
            <i class="fas fa-power-off fa-2x"></i>
            <small>login</small>
          </li>
          <!-- <img 
            alt="profile picture"
            class="border rounded-circle profile-picture"
            :src="userImage"
            style="width: 48px; height: 48px;"> -->
          <LoginModal @googleLogin="googleLogin" :loginModalOn="loginModalOn" @toggleModal="toggleModal"/>
        </ul>
      </div>
    </header>
</template>

<script>
import LoginModal from '@/components/LoginModal.vue'
import HeadSearchBar from '@/components/HeadSearchBar.vue'
import {mapActions} from 'vuex'

export default{
  name: 'HeadBar',
  components: {
      LoginModal,
      HeadSearchBar
  },
  data() {
      return {
        loginModalOn: false,
      }
  },
  methods: {
    ...mapActions('user',['googleLogin']),
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