<template>
    <div class="blog-post">
        <!-- <div class="blog-post__img">
            <img src="../assets/cloud.png" alt="">
        </div> -->
        <div class="blog-post__info" style="width:100%;">
            <div style="display:flex; flex-direction:row; align-items:center; justify-content: space-between; width: 100%;">
                <h1 class="blog-post__title" @click="moveTodetail(item.question_id)">{{ addDot(item.question_title) }}<span style="color:black;">에 대한 답변</span></h1>
                <!-- <p class="blog-post__text">
                    {{ item.content }}
                </p> -->
                <!-- <div>
                    <i class="fas fa-heart" style="margin-left:auto;color:red;"><span style="color:black;">{{ item.likeCnt }}</span></i>
                </div> -->
            </div>
            <div class="blog-post__date">
                <span>{{ parseDateString(item.updated_at) }}</span>
            </div>
        </div>
    </div>

</template>

<script>
export default {
    name: 'CardPost',
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
            let year = parseInt(date.slice(0,4));
            let month = parseInt(date.slice(5,7));
            let day = parseInt(date.slice(8,10));
            let hour = parseInt(date.slice(11,13));
            let minute = parseInt(date.slice(14,16));
            let type = 1;
            if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 ){
                type = 2;
            }else if(month == 2){
                type = 0;
            }
            hour += 9;
            // 24시간이 넘어가면 
            if(hour >= 24){
                hour -= 24;
                // 하루를 추가하고 
                day += 1;
                // 추가하는데 31일까지면 
                if(type == 2){
                    if(day > 31){
                        month += 1;
                        if(month > 12){
                            year += 1;
                            month -= 12;
                        }
                        day -= 31;
                    }
                
                }else if(type == 0){
                    if(day > 28){
                        month +=1;
                        day -= 28;
                    }
                }else{
                    if(day > 30){
                        month +=1;
                        day -= 30;
                    }
                }
            }
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
    margin: 0;
    text-align: right;
}
.blog-post__title{
    font-size: 25px;
    margin: 1rem 0 1rem;
    text-transform: uppercase;
    color: #4facfe;
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