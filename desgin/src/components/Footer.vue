<template>
    <footer class="fixed-footer">
        <h2>Footer</h2>
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
        font() {
            var h1 = document.querySelector("h1");
            h1.addEventListener("input", function() {
            this.setAttribute("data-heading", this.innerText);
            });
        }
    },
    mounted() {
        this.font();
    }
}
</script>
<style scoped>
footer {
    /* position: fixed; */
    bottom: 0;
    width: 100%;
    height: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 2em;
    text-transform: uppercase;
    z-index: -1;
}
</style>