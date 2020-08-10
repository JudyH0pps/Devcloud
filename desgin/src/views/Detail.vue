<template>
  <section class="detail">
    <DetailQuestion/>
    <DetailAnswer/>
  </section>
</template>

<script>
import DetailQuestion from '@/components/DetailQuestion.vue'
import DetailAnswer from '@/components/DetailAnswer.vue'

export default {
  name: 'Detail',
  components: {
    DetailQuestion,
    DetailAnswer
  },
  data() {
    return {

    }
  }
}
</script>

<style scoped>
.detail{
  font-family: "Poppins", sans-serif;
  margin: 80px auto 0;
  padding: 10px;
  max-width: 900px;
}
</style>
