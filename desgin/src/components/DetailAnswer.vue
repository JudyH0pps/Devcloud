<template>
    <div class="detailanswer">
        <h1 class="q-title">A.</h1>
        <div class="leftline">
            <div class="q-info">
                <div class="profile">
                    <img :src="answer.user.imageUrl" class="profile_img">
                    <p>{{ answer.user.name }}</p>
                </div>
                <p>{{ answer.updated_at }}</p>
            </div>
        </div>
        <p class="q-content">{{ answer.content }}</p>
    </div>
</template>

<script>

export default {
    name: 'DetailAnswer',
    data() {
        return {
        }
    },
    props: {
        answer: Object,
    },
    // created() {
    //     console.log(this.answer);
    // }
}
</script>

<style scoped>
.q-title {
  padding: 10px 0;
  border-bottom: 1px solid #eeeeee;
}
.tags {
    text-align: right;
}
.leftline{
    border-left: 4px solid #eeeeee;
    padding-left: 15px;
}
.q-info {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: 10px 0;
}
.q-info p {
    font-size: 14px;
    color: gray;
}
.q-content {
    padding: 10px 0;
}
.profile {
    display: flex;
    flex-direction: row;
    align-items: center;
}
.profile_img {
    border-radius: 50%;
    height: 40px;
    margin-right: 10px;
}
</style>