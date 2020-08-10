<template>
  <div class="container p-0">
    <div class="p-0 row profile-header rounded-lg">
      <div class="m-0 my-3 p-0 col-5">
        <img alt="profile picture" class="border rounded-circle profile-picture" :src="user.imageUrl">
      </div>
      <div class="col-7 m-0 p-0 mt-3">
        <div class="col-12 m-0 p-0 my-3">
          <h2 class="p-0 text-left">{{user.name }}</h2>
          <!-- <p class="text-left">{{ user.introduction }}</p> -->
          <p class="text-left">
            <textarea v-if="this.valid == true" v-model="user.introduction" placeholder="자기소개를 입력해주세요" style="resize: none; border: none ; width:100%; height:100%"
            readonly></textarea>
            <textarea v-else v-model="user.introduction" placeholder="자기소개를 입력해주세요" style="resize: none; border: Solid ; width:100%; height:100%"
            ></textarea>
          <i class="fab fa-github"></i><a :href="user.githubUrl"> {{user.githubUrl}}</a><br>
          <i class="fas fa-layer-group"></i>{{user.userTechs}}
          </p>
        </div>
        <div class="col-3 m-0 p-0 my-3">
        </div>
        <ul class="text-center list-group list-group-horizontal col-12 p-0">
          <li class="list-group-item" @click="click1">
            <h4>{{ answers.length}}</h4>
            <p>answers</p>
          </li>
          <li class="list-group-item" @click="click2">
            <h4>{{ questions.length }}</h4>
            <p>questions</p>
          </li>
          <li class="list-group-item" @click="click3" v-if="myProfile">
            <h4> + </h4>
            <p>account</p>
          </li>
        </ul>
      </div>
    </div>
    <ProfileAnswerlist :myA="myA" v-show="showmenu1"/>
    <ProfileQuestionlist v-show="showmenu2"/>
    <ProfileAccount v-if="myProfile && showmenu3"/>
  </div>
</template>

<script>
import ProfileAnswerlist from '@/components/ProfileAnswerlist.vue'
import ProfileQuestionlist from '@/components/ProfileQuestionlist.vue'
import ProfileAccount from '@/components/ProfileAccount.vue'

import { mapState, mapActions } from 'vuex';

export default {
  name: 'Profile',
  components: {
    ProfileAnswerlist,
    ProfileQuestionlist,
    ProfileAccount
  },
  data: () => {
    return {
      myProfile : false,
      showmenu1: true,
      showmenu2: false,
      showmenu3: false,
      myQ: [],
      myA: [],
    }
  },
  computed:{
    ...mapState({
      user : state => state.user.user,
      questions : state => state.question.questions,
      answers : state => state.answer.answers,
      valid : state => state.testValid
    }),
    changeUser(){
      return this.$route.params.user_id
    }
  },
  watch :{
    changeUser: function(){
      this.$router.go()
    }
  },
  methods: {
    ...mapActions('user',['fetchMyProfile','fetchUserProfile']),
    ...mapActions('answer',['fetchAnswersById']),
    ...mapActions('question',['fetchUserQuestions']),
    checkAuth(){
      if(this.$cookie.get('user_id') == this.$route.params.user_id){
        this.myProfile = true,
        alert(this.myProfile)
      }else{
        this.myProfile = false,
        alert(this.myProfile)
      }
    },
    getProfile() {
      // this.user.id = 'JudyHopps'
      // this.user.introduction = '안녕하세요 자기소개'
      // this.user.profileImage = '/img/Judy.3ec0622d.png'
      this.myQ = ['이거 어떻게해요?1','이거 어떻게해요?2','이거 어떻3게해요?','이4거 어떻게해요?','이거5 어떻게해요?','이거 어6떻게해요?']
      this.myA = ['이렇게하면된다1','이렇게하면된다2','이렇게하면된다3','이렇게하면된다4','이렇게하면된다5','이렇게하6면된다','이렇게하5면된다','이렇게하면2된다','이렇게1하면된다']
    },
    click1() {
      this.showmenu1 = true;
      this.showmenu2 = false;
      this.showmenu3 = false;
    },
    click2() {
      this.showmenu1 = false;
      this.showmenu2 = true;
      this.showmenu3 = false;
    },
    click3() {
      this.showmenu1 = false;
      this.showmenu2 = false;
      this.showmenu3 = true;
    }
  },
  mounted (){
    this.checkAuth();
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
    this.fetchUserProfile(this.$route.params.user_id)
    //작성한 답변 조회 
    this.fetchAnswersById(this.$route.params.user_id);
    //작성한 질문 조회
    this.fetchUserQuestions(this.$route.params.user_id);
  },
}

</script>

<style scoped>
/* div{
  border: 1px dashed;
} */

p, h4{
  margin: 0;
}
.profile-header{
  background: white;
  border: 1px solid #eeeeee;
}

.list-group-horizontal{
  width: 90vw;
}

.profile-picture{
  height: 200px;
  background: white;
}

.list-group{
  margin-top: 80px;
}

.list-group-item{
  width: 30vw;
  padding: 3px 10px;
}

.list-group-item:hover{
  background: #eeeeee;
  cursor: pointer;
}
</style>