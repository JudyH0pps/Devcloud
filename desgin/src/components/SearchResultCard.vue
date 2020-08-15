<template>
    <div class="card">
        <!-- <div class="image-data"> -->
            <!-- <div class="background-image"></div>
            <div class="publication-details">
                <p class="author">{{ question.user.name }}</p>
                <p class="date">{{ question.date }}</p>
            </div> -->
        <!-- </div> -->
        
        <div class="post-data">
            <div class="title" @click="moveTodetail(question.id)" v-html="$options.filters.highlights1(question.title, keyword)"></div>
            <div style="text-align: right;">
                <span class="tag" v-for="tag in question.questionTags" :key="tag.tag.name">{{ tag.tag.name }}</span>
            </div>          
            <div class="description">
                <p v-html="$options.filters.highlights2(question.content, keyword)"></p>
            </div>
            <div style="display: flex; flex-direction: row; margin-left: auto; align-items: center;">
                <div style="font-size: 25px; color: Tomato; margin-left: auto;">
                    <i class="fas fa-heart"></i>
                </div>
                <p style="font-size: 15px;">{{question.likeCnt}}</p>
            </div>
            <div style="display: flex; flex-direction: row; align-items: center; width: 100%">                   
                <img 
                    alt="profile picture"
                    class="profile-rounded"
                    :src="question.user.imageUrl"
                    style="margin-left: auto;width: 30px; height: 30px; display: inline; padding: 0px;"
                    align: right>
                <p style="font-size: 15px; color: black; font-weight: bold">
                    {{question.user.name}}
                </p>
            </div>
        </div>
        
        <!-- <h2 class="card_title">Q. {{ title }}</h2>
        <p>{{ content }}</p>
        <div>
            <span class="tag" v-for="tag in tags" :key="tag">{{ tag }}</span>
        </div>
        <div class="s">
            <p class="card_date">{{ date }}</p>
            <div class="icon heart">
                <svg viewBox="-5 -28 521.00002 512" xmlns="http://www.w3.org/2000/svg">
                    <path d="m471.382812 44.578125c-26.503906-28.746094-62.871093-44.578125-102.410156-44.578125-29.554687 0-56.621094 9.34375-80.449218 27.769531-12.023438 9.300781-22.917969 20.679688-32.523438 33.960938-9.601562-13.277344-20.5-24.660157-32.527344-33.960938-23.824218-18.425781-50.890625-27.769531-80.445312-27.769531-39.539063 0-75.910156 15.832031-102.414063 44.578125-26.1875 28.410156-40.613281 67.222656-40.613281 109.292969 0 43.300781 16.136719 82.9375 50.78125 124.742187 30.992188 37.394531 75.535156 75.355469 127.117188 119.3125 17.613281 15.011719 37.578124 32.027344 58.308593 50.152344 5.476563 4.796875 12.503907 7.4375 19.792969 7.4375 7.285156 0 14.316406-2.640625 19.785156-7.429687 20.730469-18.128907 40.707032-35.152344 58.328125-50.171876 51.574219-43.949218 96.117188-81.90625 127.109375-119.304687 34.644532-41.800781 50.777344-81.4375 50.777344-124.742187 0-42.066407-14.425781-80.878907-40.617188-109.289063zm0 0"/>
                </svg>
            </div>
            <p class="card_like">{{ like }}</p>
            <img class="profile_img" src="../assets/damgom.png" alt="">
            <p class="card_user">{{ user }}</p>
        </div> -->
        <!-- <hr/> -->
    </div>
  
</template>

<script>
import Vue from 'vue';
export default{
    name: 'SearchResultCard',
    data() {
        return {
            // title: '이거 어떻게해요?',
            // content: '이거 어떻게하는지 궁금합니다. 알려주세요. 이거 어떻게하는지 궁금합니다. 알려주세요. 이거 어떻게하는지 궁금합니다. 알려주세요',
            // tags: ['Java','Python','Django'],
            // date: '2020년 07월 20일',
            // like: 104,
            // user: 'Nongdamgom',

            question_create: this.question.createdAt,
        }
    },
    props: {
        question: Object,
        keyword: String,
    },
    methods: {
        getImages(name) {
            return require('../assets/' + name);
        },
        moveTodetail(question_id) {
            this.$router.push({
                'name':'Detail',
                params:{ "question_id" : question_id},
            });
        },
        questionCreate(){
            var createDate = this.$moment(this.question.createdAt, 'MM-DD-YYYY');
            var now = this.$moment('MM-DD-YYYY');

            console.log(this.question.createdAt);

            var diffDate = now.diff(createDate, 'days');
            if(diffDate <= 3){
                this.question_create = diffDate + "day ago";
            } else {
               this.question_create = createDate;
            }
        }
    },
    created() {
        this.questionCreate();
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
    return 'Q. ' + item.toString().replace(iQuery, matchedTxt => {
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
    /* background-color: #fff; */
    /* box-shadow: 0 3px 2px rgba(0, 0, 0, .5); */
    margin: 10px auto 10px auto;
    /* border-radius: 15px; */
    overflow: hidden;
    flex-direction: row;
    /* height: 200px; */
}
.card .title {
    color: rgb(26, 13, 171);;
}
.card .title:hover {
    cursor: pointer;
    text-decoration: underline;
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
    font-size: 20px;
    font-weight: bold;
    color: #fff;
    top: 0;
    left: 0;
    padding: 5px;
}
.post-data {
    background-color: #fff;
    padding: 10px 20px;
    position: relative;
    width: 100%;
}
.title {
    font-size:20px;
    line-height: 1;
    font-weight: bold;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
    margin-bottom: 10px;
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
.description >>> iframe, 
.description >>> ul
.descriptiON >>> ol{
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

</style>