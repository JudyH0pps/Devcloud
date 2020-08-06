<template>
    <div id="con">
        <div class="mycontent">
            <h2>404</h2>
            <h4>Opps! Page not Found</h4>
            <router-link :to="{ 'name':'Home' }">To Home</router-link>
        </div>
    </div>
</template>


<script>

export default{
    name: 'NotFound',
    methods: {
        movingBackground() {
            let con = document.querySelector('#con');
            window.onmousemove = function(e){
                let x = - e.clientX/5,
                    y = - e.clientY/5;
                con.style.backgroundPositionX = x + 'px';
                con.style.backgroundPositionY = y + 'px';
            }            
        }
    },
    mounted(){
        this.movingBackground();
    }
}
</script>

<style scoped>
/* body{
    background: linear-gradient(45deg, #8500ff, #5acaff);
    height: 100vh;
} */
#con{
    position: absolute;
    /* top: 5%;
    left: 5%;
    right: 5%;
    bottom: 5%; */
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url('../assets/p404.png') #151729;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .5);
    z-index: 10000;
}
#con .mycontent{
    max-width: 600px;
    text-align: center;
}
#con .mycontent h2{
    font-size: 18vw;
    color: #fff;
    line-height: 1em;
}
#con .mycontent h4{
    position: relative;
    font-size: 1.5em;
    margin-bottom: 20px;
    color: #111;
    background: #fff;
    font-weight: 300;
    padding: 10px 20px;
    display: inline-block;
}
#con .mycontent p{
    color: #fff;
    font-size: 1.2em;
}
#con .mycontent a{
    position: relative;
    display: block;
    padding: 10px 25px;
    background: #ff0562;
    color: #fff;
    text-decoration: none;
    margin-top: 25px;
    border-radius: 25px;
    border-bottom: 4px solid #d00d56;
    margin-left: auto;
    margin-right: auto;
    width: 130px;
}
</style>