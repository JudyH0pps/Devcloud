<template>
    <section class="ranking">
        <div class="leaderboard">
            <div class="line leaderboardheader">
                <i class="fas fa-medal"></i>
                <h3>leaderboard</h3>
            </div>
            <div class="line" v-for="user in users" :key="user">
                <img src="@/assets/damgom.png">
                <h3>Nongdamgom</h3>
                <h4 style="margin-left: auto;">1500</h4>
                <span style="margin: 0 100px 0 10px;">pts</span>
            </div>
        </div>
        <!-- Add Pagination -->
    </section>
</template>

<script>
export default {
    name: 'Ranking',
    data() {
        return {
            users: [1,2,3,4,5,6,7]
        }
    },
    methods: {
    },
    mounted(){
        document.documentElement.scrollTop = 0;
    }
}

</script>

<style scoped>
.ranking {
  font-family: "Poppins", sans-serif;
  margin: 80px 50px 0;
  padding: 10px;
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
    height: 90px;
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