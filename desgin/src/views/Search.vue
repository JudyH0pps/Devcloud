<template>
    <section class="search">
        <!-- <h1>검색 결과</h1> -->
        <SearchResultCard v-for="result in searchresults" :key="result"/>
    </section>
</template>

<script>
import SearchResultCard from'../components/SearchResultCard.vue'

export default{
    name: 'Search',
    components: {
        SearchResultCard,
    },
    data() {
        return {
            searchresults: [1,2,3,4,5,6,7,8,9]
        }
    }
}

</script>

<style scoped>
.search {
  /* background: #e9e9e9; */
  /* overflow: hidden; */
  /* padding-top: 20vh; */
  font-family: "Poppins", sans-serif;
  margin: 80px auto 0;
  padding: 10px;
  max-width: 900px;
}
</style>