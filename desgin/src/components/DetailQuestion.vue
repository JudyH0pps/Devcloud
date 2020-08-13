<template>
    <div class="detailquestion" v-if="this.$store.state.question.question">
        <h1 class="q-title">Q. {{ question.title }}</h1>
        <div class="leftline">
            <div class="tags">
                <span class="tag" v-for="tag in question.questionTags" :key="tag">{{ tag }}</span>
            </div>
            <div class="q-info">
                <div class='profile'>
                    <img :src="question.user.imageUrl" class="profile_img">
                    <p>{{ question.user.name }}</p>
                </div>
                <p>{{ question.updatedAt }}</p>
            </div>
        </div>
        <!-- <p class="q-content">{{ question.content }}</p> -->
        <div v-html="question.content">
        </div>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
    name: 'DetailQuestion',
    data() {
        return {
            // question: {},
        }
    },
    computed: {
        ...mapState({
			question: state => state.question.question
		})
    },
	methods: {
        ...mapActions('question', ['fetchQuestion', 'goEditQuestion','deleteQuestion']),
	},
	created() {
        // alert(this.$route.params.question_id)
        this.fetchQuestion(this.$route.params.question_id)
	},
    // mounted() {
    //     console.log(this.$store.state.question.question)
    // }
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