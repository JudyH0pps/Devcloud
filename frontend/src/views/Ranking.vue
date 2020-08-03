<template>
    <div class="rankingbody container">
        <h1>Ranking</h1>
        <div class="topclass w-100 gold">

        </div>
        <div class="topclass w-100 silver">

        </div>
        <div class="topclass w-100 bronze">

        </div>
        <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">순위</th>
                <th scope="col">이름</th>
                <th scope="col">레이팅</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(user, index) in users" :key="user.name">
                <th scope="row">{{ index + 4 }}</th>
                <td>{{ user.name }}</td>
                <td>{{ user.rating }}</td>
            </tr>
        </tbody>
        </table>
    </div>
</template>

<script>
export default{
    name: 'Ranking',
    data() {
        return {
            users: [
                {
                    name: "김시영",
                    rating: "1500",
                },
                {
                    name: "김시영",
                    rating: "1500",
                },                {
                    name: "김시영",
                    rating: "1500",
                },                {
                    name: "김시영",
                    rating: "1500",
                },                {
                    name: "김시영",
                    rating: "1500",
                },
            ]
        }
    },
}
</script>

<style scoped>
.topclass{
    position: relative;
    height: 120px;
    margin-bottom: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    background: white;
}
.topclass::before{
    content: '';
    position: absolute;
    top: -1px;
    left: -1px;
    right: -1px;
    bottom: -1px;
    z-index: -1;
}
.topclass::after{
    content: '';
    position: absolute;
    top: -1px;
    left: -1px;
    right: -1px;
    bottom: -1px;
    z-index: -2;
    filter: blur(4px);
}
.topclass::after,
.topclass::before{
     background: linear-gradient(235deg,#ddd793,#ffffff,#e0bc5a);
}
/* .gold, */
.gold::after{
    background: #ffee90;
}
/* .silver, */
.silver::after{
    background: #eeeeee;
}
/* .bronze, */
.bronze::after{
    background: #ffd25b
}

</style>