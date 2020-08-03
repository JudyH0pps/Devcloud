<template>
  <div id="app">
    <Headbar/>
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link :to="{ name : 'profilepage' }">profilepage</router-link> |
      <router-link :to="{ name : 'Detail' }">Detail</router-link> |
      <router-link :to="{ name : 'Write' }">Write</router-link>
    </div> -->
    <router-view/> 
  </div>
</template>

<script>
import Headbar from '@/components/Headbar.vue'

export default {
  name: 'App',
  components: {
    Headbar,
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}

:root{
    font-size: 16px;
    font-family: 'Open Sans';
    scroll-behavior: smooth;
}

/* body{
    color: black;
    background-color: white;
    margin: 0;
    padding: 0;
} */

body::-webkit-scrollbar{
    width: 0.25rem;
}

body::-webkit-scrollbar-track{
    background: #ffffff;
}

body::-webkit-scrollbar-thumb{
    background: #77b2d4;
}
</style>
