<template>
    <div class="line">
        <h1 style="margin-left: 30px; font-size: 15px;">{{ index + 1 }}위</h1>
        <img :src="profile.imageUrl">
        <h3>{{ profile.name }}</h3>
        <h4 style="margin-left: auto;">{{ user.rank_point }}</h4>
        <span style="margin: 0 100px 0 10px;">pts</span>
    </div>
</template>

<script>
import http from "@/util/http-common";

export default {
    name: 'RankLine',
    data() {
        return{
            profile: '',
        }
    },
    props: {
        user: Object,
        index: Number,
    },
    methods: {
        rednerProfile() {

        }
    },
    created() {
        http
            .get('/api/user',{
                params : {
                    user_id : this.user.user_id
                }
            })
            .then(({data}) => {
                this.profile = data;
                // this.userIdList = data;
                console.log(this.profile);
                
            })
            .catch(()=>{
                alert('랭킹 목록 조회중 오류 발생')
            })
    }
}

</script>

<style scoped>
.line {
    line-height: 85px;
    width: 70%;
    height: 75px;
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