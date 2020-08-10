<template>
    <section class="write">
        <DetailAnswer/>
        <Write/>
    </section>
</template>

<script>
import DetailAnswer from '@/components/DetailAnswer.vue'
import Write from '@/views/Write.vue'

export default {
    name: 'WriteAnswer',
    components: {
        DetailAnswer,
        Write,
    }
}
</script>

<style scoped>
.write {
    font-family: "Poppins", sans-serif;
    margin: 80px auto 0;
    padding: 10px;
    max-width: 900px;
    min-height: 100vh;
    display: flex;
    /* justify-content: center; */
    flex-direction: column;
}
</style>