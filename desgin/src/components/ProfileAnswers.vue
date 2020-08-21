<template>
  <section class="answers">
    <div class="cardlist">
      <ProfileAnswerCard v-for="answer in answers" :key="answer.id" :item="answer"/>
    </div>
    <!-- <button class="moreBtn">more</button> -->
  </section>
  
</template>

<script>
import ProfileAnswerCard from './ProfileAnswerCard.vue'
import {mapState} from 'vuex';

export default {
  name: 'ProfileAnswers',
  data() {
    return {
      as: [1,2,3,4,5,6,7,8]
      // as: [1]
    }
  },
  computed:{
      ...mapState({
          answers : state=> state.answer.answers
      })
  },
  components: {
    ProfileAnswerCard,
  }
}
</script>

<style scoped>
.answers{
  /* position: absolute; */
  /* grid-template-rows: repeat(2, 150px); */
  margin: 5px 10px 0;
  padding: 0;
  width: 100%;
}
.cardlist{
  /* position: absolute; */
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}
button {
  /* float: right; */
  position: relative;
  /* bottom: 0; */
  transform: translate(-50%, 0);
  right: -50%;
  width: 120px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  text-transform: uppercase;
  text-decoration: none;
  font-family: sans-serif;
  box-sizing: border-box;
  background: linear-gradient(90deg, #03a9f4,#f441a5,#ffeb3b,#03a9f4);
  background-size: 400%;
  border-radius: 30px;
  border: 0;
  outline: 0;
  z-index: 1;
  margin-top: 20px;
}
button:hover{
  animation: animate 8s linear infinite;
  cursor: pointer;
}
button:hover::before{
  filter: blur(20px);
  /* z-index: -1; */
  opacity: 1;
  animation: animate 8s linear infinite;
}
@keyframes animate {
  0% {
    background-position: 0%;
  }
  100% { 
    background-position: 400%;
  }
}
button::before {
  content: '';
  position: absolute;
  z-index: -1;
  top: -5px;
  left: -5px;
  right: -5px;
  bottom: -5px;
  background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);
  background-size: 400%;
  border-radius: 40px;
  opacity: 0;
  transition: 0.5s;
}
</style>
