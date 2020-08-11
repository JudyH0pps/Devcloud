<template>
    <section class="write">
        <DetailQuestion/>
        <Write/>
    </section>
</template>

<script>
import DetailQuestion from '@/components/DetailQuestion.vue'
import Write from '@/views/Write.vue'

export default {
    name: 'WriteAnswer',
    components: {
        DetailQuestion,
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