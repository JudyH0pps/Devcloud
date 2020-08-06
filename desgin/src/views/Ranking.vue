<template>
    <section class="ranking">
        <div class="leaderboard">
            <div class="line" v-for="user in users" :key="user">

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
    }
}

</script>

<style scoped>
.ranking {
  font-family: "Poppins", sans-serif;
  margin: 80px 50px 0;
  padding: 10px;
}
.leaderboard {
    width: 100%;
}
.line {
    width: 100%;
    height: 100px;
    box-shadow: 0px 3px 4px rgba(0,0,0,.2);
}

</style>