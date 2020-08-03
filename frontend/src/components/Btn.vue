<template>
    <div class="wrapper">
        <div class="mybtn">
            <button type="button" class="button">click</button>
        </div>
    </div>
</template>

<script>

</script>

<style scoped>
/* html, body {
    margin: 0;
    padding: 0;
    width: 100%;
}

body {
    height: 100vh;
    background: #151519;
} */

button {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

button {
    background: none;
    color: #ccc;
    width: 80%;
    height: 80%;
    border: 1px solid #ca503f;
    font-size: 18px;
    border-radius: 4px;
    transition: .6s;
    overflow: hidden;
    color: gray;
}

button:focus {
    outline: none;
}

button::before {
    content: '';
    display: block;
    position: absolute;
    background: rgba(255, 255, 255,.5);
    width: 60px;
    height: 100%;
    left: 0;
    top: 0;
    opacity: .5s;
    filter: blur(30px);
    transform: translateX(-130px) skewX(-15deg);
}

button::after {
    content: '';
    display: block;
    position: absolute;
    background: rgba(255, 255, 255, .2);
    width: 30px;
    height: 100%;
    left: 30px;
    top: 0;
    opacity: 0;
    filter: blur(30px);
    transform: translateX(-100px) skewX(-15deg);
}

button:hover {
    background: #ca503f;
    cursor: pointer;
    color: white;
}

button:hover:before {
    transform: translateX(1000px) skewX(-15deg);
    opacity: .6;
    transition: .7s;
}
</style>