<template>
    <div class="detailanswer">
        <h1 class="q-title">A. {{ title }}</h1>
        <div class="leftline">
            <div class="q-info">
                <p>Written by {{ user.name }}</p>
                <p>{{ date }}</p>
            </div>
        </div>
        <p class="q-content">{{ content }}</p>
    </div>
</template>

<script>

export default {
    name: 'DetailAnswer',
    data() {
        return {
            user: {
                name: 'Kong',
            },
            content: '다운받은 코드의 model 부분에 @data라고 된부분이있는데 의미는 그것입니다',
            date: '2020년 8월 10일'
        }
    },
}
</script>

<style scoped>
.q-title {
  padding: 10px 0;
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

</style>