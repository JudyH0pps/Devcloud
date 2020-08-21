<template>
    <footer class="fixed-footer">
        <p>Devcloud</p>
        <ul>
            <li>김시영</li>
            <li>김기영</li>
            <li>박준영</li>
            <li>송현진</li>
            <li>신승현</li>
        </ul>
    </footer>
</template>
<script>
export default{
    name: 'Footer',
    methods: {

    },
    mounted() {
        
    }
}
</script>
<style scoped>
footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 40px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 25px;
    text-transform: uppercase;
    z-index: 100000;
    color: gray;
    background: white;
}
ul {
    display: flex;
    flex-direction: row;
}
ul li {
    font-size: 12px;
    list-style: none;
    margin: 5px;
    /* font-weight: 800; */
}
p {
    font-size: 14px;
    font-weight: bold;
}
</style>