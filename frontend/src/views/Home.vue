<template>
  <div class="home">
    <div class="h-100">
      <div class="container">
        <div align="center" class="p-5 mt-5">
          <div class="p-5 mt-5">
            <div>
              <div class="h1 ml-3 mb-0 mr-2">
                <b-icon-cloud-fill></b-icon-cloud-fill>
              </div>
              <h1 class="d-inline" font-scale="5">DevCloud</h1>
            </div>
            <!-- searchInput Component -->
            <Searchbar/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Searchbar from '@/components/Searchbar.vue'
import {mapState,mapMutations} from 'vuex'

export default {
  name: 'Home',
  components: {
    Searchbar,
  },
  computed:{
    ...mapState({
      keyword : state => state.keyword
    })
  },
  methods:{
    ...mapMutations(['resetKeyword'])
  },
  created(){
    this.resetKeyword()
  }
}
</script>

