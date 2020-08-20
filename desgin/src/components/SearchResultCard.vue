<template>
    <div class="card">
        <div class="post-data">
            <div style="display:flex; flex-direction:row; align-items: center; height: 35px;">
                <!-- <i class="fab fa-quora" style="font-size: 16px; margin-right: 5px; color: tomato;">uestion</i>  -->
                <div @click="moveTodetail(question.id)" class="title" v-html="$options.filters.highlights1(question.title, keyword)"></div>
                <div v-if="questionHasSelected" style="dipslay:flex; align-items: center;">
                    <i class="fas fa-check-circle" style="color: green; font-size: 16px"></i>
                    <span style="font-size:12px; font-weight:bold;"> 채택완료</span>
                </div>
                
                <img 
                    alt="profile picture"
                    class="profile-rounded"
                    :src="question.user.imageUrl"
                    style="margin-left: auto;width: 30px; height: 30px; display: inline; padding: 0px; margin-right: 5px;"
                    align: right>
                <p style="font-size: 14px; color: black; font-weight: bold; color: #233453;">
                    {{question.user.name}}
                </p>
            </div>
            
            <div style="display:flex; flex-direction:row; align-items:center; justify-content:center; margin: 5px 0;">
                

                <i style="font-size: 25px; margin-left: auto; margin-right: 5px; color: tomato;" class="fas fa-heart"></i>
                <p style="font-size: 16px; margin-bottom: 3px; color: #233453;">
                    {{question.likeCnt}}
                </p>
            </div>

            <div style="display:flex; flex-direction:row; align-items:center;">
                <span class="tag" v-for="tag in question.questionTags" :key="tag.tag.name" @click="searchTag(tag.tag.id)">{{ tag.tag.name }}</span>
                <div style="text-align: right; font-size: 13px; color: #233453; margin-left:auto;">
                    {{ parseDateString(question.updatedAt) }}
                </div>          
            </div>
            <!-- <div class="description" v-html="$options.filters.highlights2(question.content, keyword)">
            </div> -->
            <div style="display: flex; flex-direction: row; margin-left: auto; align-items: center;">
            </div>
            <div style="display: flex; flex-direction: row; align-items: center; width: 100%">                   
            </div>
        </div>
    </div>
  
</template>

<script>
import http from "@/util/http-common"
import Vue from 'vue';
import {mapState,mapMutations,mapActions} from 'vuex';

export default {
    name: 'SearchResultCard',
    data() {
        return {
            questionHasSelected: false,
        }
    },
    computed :{
        ...mapState({
            tagId : state => state.tag.tagId
        })
    },
    props: {
        question: Object,
        keyword: String,
    },
    methods: {
        ...mapActions('question',['fetchQuestionsByTag']),
        ...mapMutations('tag',['setTagId']),
        getImages(name) {
            return require('../assets/' + name);
        },
        moveTodetail(question_id) {
            this.$router.push({
                'name':'Detail',
                params:{ "question_id" : question_id},
            });
        },
        searchTag(tag_id){
            this.setTagId(tag_id)
            this.$emit('searchTag')
        },
        // questionCreate(){
        //     var createDate = this.$moment(this.question.createdAt, 'MM-DD-YYYY');
        //     var now = this.$moment('MM-DD-YYYY');

        //     //console.log(this.question.createdAt);

        //     var diffDate = now.diff(createDate, 'days');
        //     if(diffDate <= 3){
        //         this.question_create = diffDate + "day ago";
        //     } else {
        //        this.question_create = createDate;
        //     }
        // }
        getSelectedAnswer() {
            //console.log("현재 질문아이디는 : " + this.questionId)
            http
                // [1] 먼저 해당 포스트에 채택된 답변이 있는지 조회
                .get('/api/answer/selected', {
                    params: {
                        "question_id": this.question.id
                    }
                })
                .then(({data}) => {
                    console.log("채택유무 : " + data);

                    // data is not "Resource not bound"
                    if(data != "Resource not bound"){
                        // 채택이 완료됬다면 채택완료 버튼 보여지기
                        this.questionHasSelected = true;
                    }
                })
                .catch(err => {
                    console.log(err);
                })
        },
        parseDateString(date) {
            let year = date.slice(0,4);
            let month = date.slice(5,7);
            let day = date.slice(8,10);
            let hour = date.slice(11,13);
            let minute = date.slice(14,16);
            return year + '년 ' + month + '월 ' + day + '일 ' + hour + '시 ' + minute + '분';
        }
    },
    created() {
        //this.questionCreate();
        this.getSelectedAnswer()
    },
}


//title
Vue.filter("highlights1", function(item, keyword){
    if(keyword == undefined) {
        return item;
    }
    // 정규표현식
    var iQuery = new RegExp(keyword, "ig");
    // 해당 키워드 하이라이트
    return item.toString().replace(iQuery, matchedTxt => {
        return "<span class='highlight'>" + matchedTxt + "</span>";
    });
});
//content
Vue.filter("highlights2", function(item, keyword){
    if(keyword == undefined) return item;

    // 정규표현식
    var iQuery = new RegExp(keyword, "ig");
    // 해당 키워드 하이라이트
    return item.toString().replace(iQuery, matchedTxt => {
        return "<span class='highlight'>" + matchedTxt + "</span>";
    });
});
</script>


<style>
.highlight {
    background-color:#ffe600;
}
</style>
<style scoped>
/* *,
*::before,
*::after {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
} */
.card {
    display: flex;
    flex-direction: column;
    background-color: #fff;
    margin: 10px auto 10px auto;
    /* border-radius: 15px; */
    overflow: hidden;
    flex-direction: row;
    /* height: 200px; */
    /* padding-bottom: 25px;
    padding-top: 25px; */
    border-bottom: 1px solid #eee;
    padding: 15px;
    border-radius: 10px;
    transition: 0.5s ease;
}
.card:hover {
    box-shadow: 0 5px 1px rgba(0, 0, 0, .5);
    transform: translateY(-5%);
    /* cursor: pointer; */
}
/* .image-data {
    height: 250px;
    width: 10px;
    position: relative;
    z-index: 1;
    overflow: hidden;
} */
/* .author {
    display: inline-block;
    margin-bottom: .5rem;
    font-size: 10px;
} */
/* .author:hover {
    color: rgba(255, 255, 255, .75);
} */
/* .background-image {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    background: url('../assets/cloud.png') center no-repeat;
    background-size: cover;
    z-index: -1;
    transition: transform 5s;
} */
/* .card:hover .background-image {
    transform: scale(1.5);
} */
.publication-details {
    width: 100%;
    height: 100%;
    background: linear-gradient(to right, rgba(79, 172, 254, .5),
                                            rgba(0, 242, 254, .6));
    font-size: 14px;
    font-weight: bold;
    color: #fff;
    top: 0;
    left: 0;
    padding: 5px;
}
.post-data {
    background-color: #fff;
    margin: 0 10px;
    position: relative;
    width: 100%;
}
.title {
    font-size: 21px;
    line-height: 1;
    /* font-weight: bold; */
    margin-right: 5px;
    color: #225686;
    /* padding-bottom: 10px;
    margin-bottom: 10px; */
}
.title:hover {
    text-decoration: underline;
    cursor: pointer;
}
.subtitle {
    font-size: 12px;
    font-weight: 400;
    text-transform: uppercase;
    color: #a2a2a2;
    margin: 0;
}
.description {
    position: relative;
    border-left: 4px solid #ccc;
}
.description >>> img,
.description >>> iframe {
    display: none;
}
/* .description::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 5px;
    height: 25px;
    background: linear-gradient(to right, rgba(193, 238, 240, 0.76),
                                            rgba(0, 242, 254, .6));
    border-radius: 20px;
} */
.publication-details {
    transform: translateX(-100%);
}
.card:hover .publication-details {
    transform: translateX(0);
}
/* .tag {
    font-size: 12px;
    margin: 1px;
    padding: 3px 5px;
    border-radius: 2px;
    border: 1px solid #e1ecf4;
    background: #e1ecf4;
    color:#60829b
}
. */
.icon {
    width: 25px;
}
.icon path {
    fill: transparent;
    stroke-width: 10;
    stroke: #ff3187;
}
.icon.heart path {
    stroke-dasharray: 1560;
    stroke-dashoffset: 0;
}
.icon.heart:hover path {
    animation: animate-heart .8s linear forwards
}
@keyframes animate-heart {
    0% {
        stroke-dashoffset: 0;
    }
    40% {
        stroke-dashoffset: 1560;
    }
    80% {
        stroke-dashoffset: 3120;
        fill: transparent;
    }
    100% {
        stroke-dashoffset: 3120;
        fill: #ff3187;
    }
}
/* profile-image */
.profile-rounded {
  background: silver;
  height: 50px; width: 200px;
  border-radius: 25px;
}

.description >>> iframe {
    display: none;
}

</style>