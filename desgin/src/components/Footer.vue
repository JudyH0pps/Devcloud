<template>
    <footer class="fixed-footer">
        <h4>SSAFY</h4>
        
        <!-- <h2>김시영</h2>
        <h2>김기영</h2>
        <h2>박준영</h2>
        <h2>송현진</h2>
        <h2>신승현</h2> -->
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
    /* position: fixed; */
    bottom: 0;
    width: 100%;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 25px;
    text-transform: uppercase;
    z-index: -1;
}
</style>