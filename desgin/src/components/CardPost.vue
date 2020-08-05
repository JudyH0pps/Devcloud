<template>
    <div class="blog-post">
        <div class="blog-post__img">
            <img src="../assets/cloud.png" alt="">
        </div>
        <div class="blog-post__info">
            <div class="blog-post__date">
                <span>목요일</span>
                <span>2020년 7월 30일</span>
            </div>
            <h1 class="blog-post__title">구름</h1>
            <p class="blog-post__text">
                
            </p>
            <a href="#" class="blog-post__cta">Read more</a>
        </div>
    </div>

</template>

<script>
export default{
    name: 'CardPost',
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
    height: 200px;
    padding: 50px;
    margin: 15px 10px;
    background-color: #fff;
    box-shadow: 0px 3px 4px rgba(0,0,0,.2);
    display: flex;
    align-items: center;
    border-radius: 0px;
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
}
.blog-post__title{
    font-size: 25px;
    margin: 1rem 0 1rem;
    text-transform: uppercase;
    color: #4facfe;
}
.blog-post__text{
    margin-bottom: 2rem;
    font-size: 1.4rem;
    color: rgba(0,0,0,.7);
}

</style>