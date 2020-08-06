<template>
  <div id="app">
    <!-- <section class="banner"> -->
    <HeadBar/>
    <!-- </section> -->
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div> -->
    <router-view class="botmargin400px"/>
    <Footer/>
  </div>
</template>

<script>
import HeadBar from '@/components/HeadBar.vue'
import Footer from '@/components/Footer.vue'

export default{
  name: 'App',
  components: {
    HeadBar,
    Footer
  },
  methods: {
    navbarSpan() {
      window.addEventListener("scroll", function(){
        let header = document.querySelector("header");
        header.classList.toggle("sticky", window.scrollY > 0);
      })
    }
  },
  mounted() {
    this.navbarSpan();
  }
}

</script>

<style>
#app {
  font-family: 'Poppins', sans-serif;
  /* font-family: Avenir, Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  min-width: 1024px;
}

#nav {
  padding: 30px;
}

body::-webkit-scrollbar{
    width: 0.25rem;
}

body::-webkit-scrollbar-track{
    background: #ffffff;
}

body::-webkit-scrollbar-thumb{
    background: #77b2d4;
}

a {
  text-decoration: none;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  background: white;
}

/* .banner {
  position: relative;
  width: 100%;
  height: 100%;
} */

/* .botmargin400px {
  margin-bottom: 400px !important;
} */
</style>
