<template>
    <section class="ranking">
        <div class="leaderboard">
            <div class="line leaderboardheader" style="height: 90px;">
                <i class="fas fa-medal"></i>
                <h3>leaderboard</h3>
            </div>
        </div>
        <RankLine v-for="(user, index) in userIdList" :key="user.user_id" :user="user" :index="index"/>
        <!-- Add Pagination -->
    </section>
</template>

<script>
import http from "@/util/http-common";
import RankLine from '../components/RankLine.vue';

export default {
    name: 'Ranking',
    data() {
        return {
            userIdList: [],
        }
    },
    components: {
        RankLine,
    },
    methods: {
    },
    created() {
            http
                .get('/api/rank')
                .then(({data}) => {
                    this.userIdList = data;
                    // console.log(this.userIdList);       
                })
                .catch(()=>{
                    alert('랭킹 목록 조회중 오류 발생')
                })
    },
    mounted(){
        document.documentElement.scrollTop = 0;
        // console.log(this.userIdList)
    },
}

</script>

<style scoped>
.ranking {
  font-family: "Poppins", sans-serif;
  margin: 80px 50px 60px;
  padding: 10px;
  min-height: 100vh;
}
.fa-medal {
    /* position: absolute; */
    color: white;
    font-size: 50px;
    padding: 20px 0 0 100px;;
    height: 100%;
}
/* .fa-medal::after {
    content: '';
    position: absolute;
    border: 3px solid rgba(255,255,255,.5);
    border-radius: 50%;
    width: 80px;
    height: 80px;
    transform: translate(-80%, -20%);
    filter: blur();
} */
.leaderboardheader {
    background-color: skyblue;
    color: white;
    justify-content: space-between;
    box-shadow: 0px 3px 4px rgba(0,0,0,.2);
}
.leaderboardheader h3 {
    text-transform: uppercase;
    padding-right: 100px;
}
.leaderboard {
    width: 100%;
}
.line {
    line-height: 85px;
    width: 70%;
    height: px;
    margin-left: auto;
    margin-right: auto;
    /* box-shadow: 0px 3px 4px rgba(0,0,0,.2); */
    display: flex;
    flex-direction: row;
}
.line img {
    height: 70%;
    border-radius: 50%;
    margin: 15px 15px 15px 40px;
    border: 4px solid #fff;
    box-shadow: 0px 5px 10px rgba(0,0,0,.2);
}
</style>