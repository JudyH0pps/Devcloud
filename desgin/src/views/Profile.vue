<template>
  <section class="profile">
    <div class="container">
      <div class="profile-header">
        <div class="profile-img">
          <img src="../assets/damgom.png" width="200" alt="profile">
        </div>
        <div class="profile-nav-info">
          <h3 class="user-name">Nongdamgom</h3>
          <div class="address">
            <p class="state">
              Gwanju,
            </p>
            <span class="country">Republic of Korea</span>
          </div>
        </div>
        <div class="profile-option">
          <div class="notification">
            <i class="fa fa-bell"></i>
            <span class="alert-message">1</span>
          </div>
        </div>
      </div>
      <div class="main-bd">
        <div class="left-side">
          <div class="profile-side">
            <div class="user-bio">
              <p class="bio">
                안녕하세요 자기소개입니다.안녕하세요 자기소개입니다.안녕하세요 자기소개입니다
                안녕하세요 자기소개입니다.안녕하세요 자기소개입니다.안녕하세요 자기소개입니다
                안녕하세요 자기소개입니다.안녕하세요 자기소개입니다.안녕하세요 자기소개입니다
                안녕하세요 자기소개입니다.안녕하세요 자기소개입니다.안녕하세요 자기소개입니다
                안녕하세요 자기소개입니다.안녕하세요 자기소개입니다.안녕하세요 자기소개입니다
                안녕하세요 자기소개입니다.안녕하세요 자기소개입니다.안녕하세요 자기소개입니다
                안녕하세요 자기소개입니다.안녕하세요 자기소개입니다.안녕하세요 자기소개입니다
              </p>
            </div>
          </div>
        </div>
        <div class="right-side">
          <div class="nav">
            <ul>
              <li class="user-post tab" :class="{ 'active': selectedMenu===1 }" @click="selectedMenu=1">Questions</li>
              <li class="user-post tab" :class="{ 'active': selectedMenu===2 }" @click="selectedMenu=2">Answers</li>
              <li class="user-post tab" :class="{ 'active': selectedMenu===3 }" @click="selectedMenu=3">Profile</li>
            </ul>
          </div>
          <ProfileQuestions v-show="selectedMenu == 1"/>
          <ProfileAnswers v-show="selectedMenu == 2"/>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import ProfileAnswers from '@/components/ProfileAnswers.vue'
import ProfileQuestions from '@/components/ProfileQuestions.vue'

export default {
  name: 'Profile',
  data() {
    return {
      selectedMenu: 2,
    }
  },
  computed: {
  },
  components: {
    ProfileAnswers,
    ProfileQuestions
  }
}
</script>

<style scoped>

*{
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}
.profile {
  /* background: #e9e9e9; */
  /* overflow: hidden; */
  /* padding-top: 20vh; */
  font-family: "Poppins", sans-serif;
  margin: 80px 50px 0;
  padding: 10px;
}
.profile-header {
  background: #fff;
  width: 100%;
  display: flex;
  height: 190px;
  position: relative;
  box-shadow: 0px 3px 4px rgba(0,0,0,.2);
}
.profile-img {
  float: left;
  width: 340px;
  height: 200px;
}
.profile-img img {
  border-radius: 50%;
  height: 230px;
  width: 230px;
  border: 4px solid #fff;
  box-shadow: 0px 5px 10px rgba(0,0,0,.2);
  position: absolute;
  top: 20px;
  left: 40px;
  z-index: 5;
  background: #fff;
}
.profile-nav-info {
  float: left;
  flex-direction: column;
  justify-content: center;
  padding-top: 60px;
}
.profile-nav-info h3 {
  font-variant: small-caps;
  font-size: 2rem;
  font-family: sans-serif;
  font-weight: bold;
}
.profile-nav-info .address {
  display: flex;
  font-weight: bold;
  color: #777;
}
.profile-nav-info .address p {
  margin-right: 5px;
}
.profile-option {
  width: 40px;
  height: 40px;
  background: red;
  position: absolute;
  right: 50px;
  top: 50%;
  transform: translateY(-50%);
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
.main-bd {
  width: 100%;
  display: flex;
  padding-right: 15px;
}
.profile-side {
  width: 300px;
  background: #fff;
  box-shadow: 0px 3px 5px rgba(0,0,0,0.2);
  padding: 90px 30px 20px;
  margin-left: 10px;
  z-index: 99;
}
.profile-side p{
  margin-bottom: 7px;
  color: #333;
  font-size: 14px;
}
.right-side {
  width: 100%;
}
.nav {
  z-index: -1;
}
.nav ul {
  display: flex;
  justify-content: center;
  list-style-type: none;
  height: 40px;
  background: #fff;
  box-shadow: 0px 2px 5px rgba(0,0,0,.3);
}
.nav ul li {
  padding: 10px;
  width: 100%;
  cursor: pointer;
  text-align: center;
  transition: all .2s ease-in-out;
}
.nav ul li.active,
.nav ul li:hover {
  box-shadow: 0 -3px 0px rgba(288, 0, 70, .9) inset;
}

</style>
