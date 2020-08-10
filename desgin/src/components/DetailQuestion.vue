<template>
    <div class="detailquestion">
        <h1 class="q-title">Q. {{ title }}</h1>
        <div class="leftline">
            <div class="tags">
                <span class="tag" v-for="tag in tags" :key="tag">{{ tag }}</span>
            </div>
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
    name: 'DetailQuestion',
    data() {
        return {
            user: {
                name: 'Damgom',
            },
            tags: ['Java','django','spring'],
            title: 'Spring코드에서 @data 어노테이션의미가 뭔가요?',
            content: '다운받은 코드의 model 부분에 @data라고 된부분이있는데 무슨의미인지 모르겠어요',
            date: '2020년 8월 10일'
        }
    },
}
</script>

<style scoped>
.q-title {
  padding: 10px 0;
  border-bottom: 1px solid #eeeeee;
  margin-bottom: 10px;
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
.q-content {
    padding: 10px 0;
}
.q-info p {
    font-size: 14px;
    color: gray;
}

</style>