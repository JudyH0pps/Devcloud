 <template>
  <div class="d-flex flex-column align-items-center">
    <!-- <form class="d-flex flex-column align-items-center"> -->
      <div class="form-group text-left col-12 col-md-7 px-0 my-4">
        <label for="title" class="mb-0 d-block"><h3 class="font-weight-bolder">Title</h3></label>
        <input v-model="questionData.title" type="text" class="form-control" id="title" placeholder="질문의 제목을 입력해주세요" />
      </div>
      <div class="form-group text-left col-12 col-md-7 px-0 my-4">
        <label for="content" class="mb-0 d-block"><h3 class="font-weight-bolder">Body</h3></label>
        <textarea  v-model="questionData.content" type="text" class="form-control" id="content" placeholder="내용을 입력해주세요" />
      </div>
      <div class="form-group text-left col-12 col-md-7 px-0 my-4">
        <label for="tag" class="mb-0 d-block"><h4 class="font-weight-bolder">Tags</h4></label>
        <input type="text" class="form-control" id="tag" placeholder="#태그1, #태그2" />
      </div>
      <button @click="writeQuestion(questionData)" class="btn btn-success col-12 col-md-7">작성</button>
    <!-- </form> -->
  </div>
</template>

<script>
import {mapActions} from 'vuex'
export default {
  name: 'Write',
  data() {
    return {
      questionData: {
        "title": '',
        "content": '',
        "user_id": this.$cookie.get('user_id')
      },
      isEditPage: null
    }
  },
  methods: {
    ...mapActions('question',['getQuestion','postQuestion', 'editQuestion']),
    writeQuestion(questionData) {
      if (this.isEditPage) {
        questionData.id = this.questionId
        this.editQuestion(questionData)
      }
      else {
        this.postQuestion(questionData)
      }
    }
  },
  computed: {
    questionId() {
      return parseInt(this.$route.params.question_id)
    }
  },
  created() {
    if (window.location.href===`http://localhost:3000/question/${this.questionId}/edit`) {
      // 수정 페이지로 활용한다면, 기존 질문의 내용을 가져와야 합니다.
      this.getQuestion(this.questionId)
      // 받아온 질문 정보 중 필요한 정보를 현재 컴포넌트의 data에 할당합니다.
      const originQuestion = this.$store.state.question.question
      this.questionData.title = originQuestion.title
      this.questionData.content = originQuestion.content
      // 수정 페이지임을 확인하기 위한 변수를 조정합니다.
      this.isEditPage = true
    }
    else {
      this.isEditPage = false
    }
  },
}
</script>

<style>

</style>