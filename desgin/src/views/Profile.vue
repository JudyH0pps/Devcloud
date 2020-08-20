<template>
  <section class="profile">
    <div class="container">
      <div class="profile-header">
        <div class="profile-img">
          <img :src="user.imageUrl" width="200" alt="profile">
        </div>
        <div class="profile-nav-info">
          <h3 class="user-name">{{user.name }}</h3>
        </div>
      </div>
      <div class="main-bd">
        <div class="left-side">
          <div class="profile-side">
            <div class="user-bio">
              <i class="fab fa-github"></i><a :href="user.githubUrl"> {{user.githubUrl}}</a><br>
              <i class="fas fa-layer-group"></i><span v-if="user.userTechs.length != 0">{{user.userTechs[0].tech.name}}</span>
              <p class="bio">{{ user.introduction }}</p>
            </div>
          </div>
        </div>
        <div class="right-side">
          <div class="nav">
            <ul>
              <li class="user-post tab" :class="{ 'active': selectedMenu===1 }" @click="selectedMenu=1">Questions({{this.questions.length}})</li>
              <li class="user-post tab" :class="{ 'active': selectedMenu===2 }" @click="selectedMenu=2">Answers({{this.answers.length}})</li>
              <li v-if="myProfile" class="user-post tab" :class="{ 'active': selectedMenu===3 }" @click="selectedMenu=3">Profile</li>
            </ul>
          </div>
          <ProfileQuestions v-show="selectedMenu == 1"/>
          <ProfileAnswers v-show="selectedMenu == 2"/>
          <ProfileAccount v-if="myProfile && selectedMenu == 3"/>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import ProfileAnswers from '@/components/ProfileAnswers.vue'
import ProfileQuestions from '@/components/ProfileQuestions.vue'
import ProfileAccount from '@/components/ProfileAccount.vue'

import { mapState, mapActions ,mapMutations} from 'vuex';

export default {
  name: 'Profile',
  data() {
    return {
      myProfile : false,
      selectedMenu: 2,
      // 선택된 기술스택들
      selectedTags:[],
    }
  },
  components: {
    ProfileAnswers,
    ProfileQuestions,
    ProfileAccount
  },
  computed: {
    ...mapState({
      techs : state => state.tech.techs,
      isTechs : state => state.tech.isTechs,
      user : state => state.user.user,
      questions : state => state.question.questions,
      answers : state => state.answer.answers,
      valid : state => state.testValid
    }),
    changeUser(){
      return this.$route.params.user_id
    },
  },
  watch :{
    changeUser: function(){
      this.$router.go()
    },
    isTechs : function(){
      if(this.isTechs){
        // alert(this.techs.length)
        this.inputChange(this.techs)
        // console.log(this.selectedTags)
        this.setTechsIn(this.selectedTags)
      }
    }
  },
  methods: {
    ...mapActions('user',['fetchMyProfile','fetchUserProfile']),
    ...mapActions('answer',['fetchAnswersById']),
    ...mapActions('question',['fetchUserQuestions']),
    ...mapActions('tech',['fetchTechs']),
    ...mapMutations('tech',['setTechsIn']),
    ...mapMutations(['settestValid']),
    checkAuth(){
      if(this.$cookie.get('user_id') == this.$route.params.user_id){
        this.myProfile = true;
        // alert(this.myProfile);
        this.fetchTechs();
        // alert(this.techs.length)
        //this.inputChange(this.techs)
        // console.log(this.selectedTags)
        //this.setTechsIn(this.selectedTags)
      }else{
        this.myProfile = false;
        // alert(this.myProfile)
      }
    },
    // 불러온 태그리스트를 자동완성 리스트 형식으로 변경
    inputChange(arr){
          for(var i = 0; i < arr.length; i++)
          {
            let singleTag = {};
            singleTag.key = arr[i].name;
            singleTag.value = arr[i].name;
            singleTag.id = arr[i].id;
            this.selectedTags.push(singleTag)
          } 
    }
  },
  mounted (){
    this.checkAuth()
    // 주석처리부분 본인일때와 타인일때 다르게처리하려면 ...
    // if(this.myProfile){ //본인
    //   this.fetchMyProfile(this.$cookie.get('logintoken'));
    //   //작성한 답변 조회 
    //   this.fetchAnswersById(this.$cookie.get('user_id'));
    //   //작성한 질문 조회
    //   this.fetchUserQuestions(this.$cookie.get('user_id'));
    //   //console.log(this.user);
    // }
    // else{ // 다른사람
    //   alert("다른사람의 프로필로 접근")
    // }
    // 프로필페이지에서 정보변경을 제외하고는 조회방식이 동일함
    document.documentElement.scrollTop = 0;
    this.fetchUserProfile(this.$route.params.user_id)
    //작성한 답변 조회 
    this.fetchAnswersById(this.$route.params.user_id);
    //작성한 질문 조회
    this.fetchUserQuestions(this.$route.params.user_id);
    this.settestValid(true);
    //
    
  },
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
  margin-left: 100px;
  flex-direction: row;
  justify-content: center;
  padding-top: 60px;
  width: 100%;
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
  margin-bottom: 100px;
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
