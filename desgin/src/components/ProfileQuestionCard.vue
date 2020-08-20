<template>
    <div class="blog-post">
        <!-- <div class="blog-post__img">
            <img src="../assets/cloud.png" alt="">
        </div> -->
        <div class="blog-post__info" style="width:100%;">
            <div style="display:flex; flex-direction:row; align-items:center; justify-content: space-between; width: 100%;">
                <h1 class="blog-post__title" @click="moveTodetail(item.id)">{{ addDot(item.title) }}</h1>
                <!-- <p class="blog-post__text">
                    {{ item.content }}
                </p> -->
                <div>
                    <i class="fas fa-heart" style="margin-left:auto;color:red;"><span style="color:black;">{{ item.likeCnt }}</span></i>
                </div>
            </div>
            <div class="blog-post__date">
                <span>{{ parseDateString(item.updatedAt) }}</span>
            </div>
        </div>
    </div>

</template>

<script>
export default {
    name: 'ProfileQuestionCard',
    props: {
        item: Object,
    },
    methods: {
        moveTodetail(question_id) {
            this.$router.push({
                'name':'Detail',
                params:{ "question_id" : question_id},
            });
        },
        parseDateString(date) {
            let year = date.slice(0,4);
            let month = date.slice(5,7);
            let day = date.slice(8,10);
            let hour = date.slice(11,13);
            let minute = date.slice(14,16);
            return year + '년 ' + month + '월 ' + day + '일 ' + hour + '시 ' + minute + '분';
        },
        addDot(title) {
            if (title.length >= 14) {
                return title.slice(0, 14) + '...';
            }
            else {
                return title;
            }
        }
    },
    // created() {
    //     console.log(this.item)
    // }
}
</script>

<style scoped>
*,
*::before,
*::after {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
html {
    font-size: 10px;
}
img {
    width: 100%;
}
.blog-post {
    width: 93%;
    height: 100px;
    padding: 50px;
    margin: 5px 10px;
    background-color: #fff;
    box-shadow: 0px 3px 4px rgba(0,0,0,.2);
    display: flex;
    align-items: center;
    border-radius: 10px;
 }
.blog-post__img{
    width: 150px;
    transform: translateX(-40%);
    
    position: relative;
}
.blog-post__img img {
    object-fit: cover;
    display: block;
    border-radius: 10px;
}
.blog-post__img::before{
    content: '';
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    background: linear-gradient(to right, rgba(79, 172, 254, .5),
                                            rgba(0, 242, 254, .6));
    box-shadow: 0px 3px 4px rgba(0,0,0,.2);
    border-radius: 10px;
}
.blog-post__date span{
    display: block;
    color: rgba(0,0,0,.5);
    font-size: 12px;
    font-weight: 600;
    margin-left: auto;
    margin-bottom: 10px !important;
    text-align: right;

}
.blog-post__title{
    font-size: 25px;
    margin: 1rem 0 1rem;
    text-transform: uppercase;
    color: #4facfe;
    width: 100%;
}
.blog-post__title:hover {
    cursor: pointer;
    text-decoration: underline;
}
.blog-post__text{
    margin-bottom: 2rem;
    font-size: 1.4rem;
    color: rgba(0,0,0,.7);
}

</style>