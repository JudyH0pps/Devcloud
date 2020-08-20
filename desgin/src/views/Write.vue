<template>
  <section class="writeform">
    <div class="form" style="margin-top: 40px; border: 1px solid #ccc; border-radius: 4px 4px 0 0" v-if="!isAnswer">
      <input type="text" name="title" autocomplete="off" required v-model="title"/>
      <label class="label-name" for="title" v-if="isEdit === false">
        <span style="margin-left:5px;" class="content-name">Title</span>
      </label>
    </div>
    <!-- <div class="form">
      <input type="text" name="content" autocomplete="off" required/>
      <label class="label-name" for="content" v-if="isEdit === false">
        <span class="content-name">Content</span>
      </label>
    </div> -->
    <div style="margin-top: 20px; margin-bottom: 15px;">
      <label class="label-name" for="tags">
        <span style="margin-left:5px;" class="tag-name">Content</span>
      </label>
    </div>
    <vue-editor id="editor"
        useCustomImageHandler
        @imageAdded="handleImageAdded"
        v-model="content" :editor-toolbar="customToolbar">
    </vue-editor>
    <div v-if="!isAnswer" style="margin-top: 20px; margin-bottom: 15px;">
      <label class="label-name" for="tags">
        <span style="margin-left:5px;" class="tag-name">Tags</span>
      </label>
    </div>
    <tags-input element-id="tags"
                v-model="resultedTags"
                :existing-tags="this.tagss"
                :typeahead='true'
                placeholder="태그를 추가하세요"
                :typeahead-hide-discard="true"
                :only-existing-tags="true"
                :add-tags-on-blur="true"
                :typeahead-max-results="tagListMax"
                :limit="posTagCnt"
                typeahead-style="dropdown"
                wrapper-class="write_tags"
                v-if="!isAnswer"
                >
    </tags-input>  
    <button @click="postData">Submit</button>
  </section>
</template>

<script>
import { mapState, mapActions,mapGetters } from 'vuex'
import { VueEditor } from 'vue2-editor'
import Vue from 'vue'
import VoerroTagsInput from '@voerro/vue-tagsinput';
import axios from 'axios'
Vue.component('tags-input', VoerroTagsInput);


export default {
  name: 'Write',
  props: {
    isAnswer: Boolean,
  },
  components: {
    VueEditor
  },
  data() {
    return {
      tagListMax : 10,
      posTagCnt : 4,
      title: '',
      content: '',
      resultedTags:[],
      tags: [],
      isEdit: false,
      customToolbar: [
          [{ header: [false, 1, 2, 3, 4, 5, 6] }],
          ["bold", "italic", "underline", "strike"],
          [{ align: "" }, { align: "center" }, { align: "right" }, { align: "justify" }],
          ["blockquote", "code-block"],
          [{ list: "ordered" }, { list: "bullet" }, { list: "check" }],
          ["link", "image", "video"],
          ["clean"]
      ]
    }
  },
  computed: {
    ...mapState({
      question: state => state.question.question,
      answers: state => state.answer.answers,
      //tagsIn : state => state.tag.tagsIn
    }),
    ...mapGetters('tag',['tagss'])
  },
  methods: {
    ...mapActions('answer', ['postAnswer', 'editAnswer']),
    ...mapActions('question', ['postQuestion', 'editQuestion']),
    ...mapActions('tag',['fetchTags']),
    /////
    handleImageAdded(file, Editor, cursorLocation) {
      var formData = new FormData();
      formData.append('file', file)

      axios({
        // Nginx 안쓸때,
        //url: 'http://i3c202.p.ssafy.io:8080/api/question/upload',
        // Nginx 용 
        url: 'https://i3c202.p.ssafy.io/api/question/upload',
        method: 'POST',
        // headers:{'Authorization': 'Bearer ' +  + token},
        data: formData
      }).then((result) => {
        let url = result.data.url;
        Editor.insertEmbed(cursorLocation, 'image', url);
        var uploader = document.getElementById("file-upload");
        uploader.value = "";
      }).catch((err) => {
        console.log(err);
      })
    },
    /////
    postData() {
      // 질문 페이지라면
      // 태그형식 변경 
      if (this.isAnswer === false) {
        const questionData = {
          title: this.title,
          content: this.content,
          tagList: this.outputChange(this.resultedTags),
        }
        // 수정
        if (this.isEdit) {
          questionData.question_id = parseInt(this.$route.params.question_id)
          questionData.tagList = this.outputChange(this.resultedTags),
          this.editQuestion(questionData)
          }
        // 작성
        else {
          questionData.user_id = parseInt(this.$cookie.get('user_id')),
          this.postQuestion(questionData)
        }
      }
      // 답변 페이지일 때
      else {
        const answerData = {
          content: this.content,
        }
          // 수정일 경우
        if (this.isEdit) {
          answerData.answer_id = parseInt(this.$route.params.answer_id),
          this.editAnswer(answerData)
          }
        // 작성일 경우
        else {
          answerData.question_id = parseInt(this.$route.params.question_id),
          answerData.user_id = this.$cookie.get('user_id')
          this.postAnswer(answerData)
        }
      }
    },
    outputChange(arr){
        let tagList = [];
        for(var i = 0; i < arr.length; i++)
        {
            let singleTag = {};
            singleTag.id = arr[i].id;
            singleTag.name = arr[i].value;
            tagList.push(singleTag)
        }
        return tagList 
    },
    inputChange(arr){
          for(var i = 0; i < arr.length; i++)
          {
            let singleTag = {};
            singleTag.key = arr[i].name;
            singleTag.value = arr[i].name;
            singleTag.id = arr[i].id;
            this.resultedTags.push(singleTag)
          } 
    },
  },
  created() {
    // URL에 있는 question_id, answer_id로 수정페이지인지 아닌지 분별
    // 답변
    if (this.isAnswer) {
      // 수정
      if (this.$route.params.answer_id) {
        this.isEdit = true
        this.content = this.answers.find(answer => answer.id === parseInt(this.$route.params.answer_id)).content
      }
    }
    // 질문
    else {
      // 수정
      if (this.$route.params.question_id) {
        this.isEdit = true
        this.title = this.question.title
        this.content = this.question.content
        this.tags = this.question.questionTags.map(data => data.tag)
        this.inputChange(this.tags)
      }
    }
    this.fetchTags();
    window.scrollTo(0, 0)
  },
  mounted() {
    // 링크 버튼
    const linkBtn = document.querySelector('.ql-link')
    // const textArea = document.querySelector('.ql-editor')
    linkBtn.addEventListener('click', function() {
      // 링크 생성
      var inputURL = prompt('URL을 입력해주세요.', 'https://')
      var hyperLink = document.createElement('a')
      hyperLink.innerText = inputURL
      hyperLink.href = inputURL
      
      var sel = window.getSelection()
      var range = sel.getRangeAt(0)
      range.collapse(false)
      range.insertNode(hyperLink)
      sel.removeAllRanges()
      sel.addRange(range)
    })
  }
}
</script>

<style scoped>
.writeform{
  font-family: "Poppins", sans-serif;
  margin: 60px auto 0;
  /* padding: 10px; */
  max-width: 900px;
  width: 100%;
  min-height: 100vh;
  display: flex;
  /* justify-content: center; */
  flex-direction: column;
  /* background: white; */
}

.form {
  width: 100%;
  position: relative;
  height: 50px;
  margin-left: auto;
  margin-right: auto;
  overflow: hidden;
  /* margin-bottom: 20px; */
}

.form input {
  width: 100%;
  height: 100%;
  color: #595f6e;
  padding-top: 20px;
  margin-bottom: 20px;
  border: none;
  outline: none;

}

.form label {
  position: absolute;
  bottom: 0px;
  left: 0%;
  width: 100%;
  height: 100%;
  pointer-events: none;
  border-bottom: 1px solid black;
}

.form label::after {
  content: '';
  position: absolute;
  left: 0px;
  bottom: -1px;
  height: 100%;
  width: 100%;
  border-bottom: 3px solid #5fa8d3;
  transform: translateX(-100%);
  transition: transform 0.3s ease;
}
.content-name {
  position: absolute;
  bottom: 5px;
  left: 0px;
  transition: all 0.3s ease;
}
.form input:focus + .label-name .content-name,
.form input:valid + .label-name .content-name {
  transform: translateY(-150%);
  font-size: 14px;
  color: #5fa8d3;
}

.form input:focus + .label-name::after,
.form input:valid + .label-name::after {
  transform: translateX(0%);
}

button {
  /* float: right; */
  position: relative;
  /* bottom: 0; */
  transform: translate(-50%, 0);
  right: -50%;
  width: 120px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  text-transform: uppercase;
  text-decoration: none;
  font-family: sans-serif;
  box-sizing: border-box;
  background: linear-gradient(90deg, #03a9f4,#f441a5,#ffeb3b,#03a9f4);
  background-size: 400%;
  border-radius: 30px;
  border: 0;
  outline: 0;
  z-index: 1;
  margin: 20px 0;
}
button:hover{
  animation: animate 8s linear infinite;
  cursor: pointer;
}
button:hover::before{
  filter: blur(20px);
  /* z-index: -1; */
  opacity: 1;
  animation: animate 8s linear infinite;
}
button::before {
  content: '';
  position: absolute;
  z-index: -1;
  top: -5px;
  left: -5px;
  right: -5px;
  bottom: -5px;
  background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);
  background-size: 400%;
  border-radius: 40px;
  opacity: 0;
  transition: 0.5s;
}
.quillWrapper{
  background: white;
}
.quillWrapper >>> .ql-clean {
  display: none !important;
}
.quillWrapper >>> .ql-editor {
  min-height: 250px;
}
.quillWrapper >>> img {
  max-width: 300px;
  max-height: 300px;
}

</style>
