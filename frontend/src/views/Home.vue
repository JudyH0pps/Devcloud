<template>
  <div>
    <section class="Home">
      <!-- <div class="title-wrapper">
        <h1 contenteditable data-heading="DevCloud">DevCloud</h1>
      </div> -->
      <SearchBar/>
      <div class="wave wave1"></div>
      <div class="wave wave2"></div>
      <div class="wave wave3"></div>
      <div class="wave wave4"></div>
    </section>
    <MainBottom/>
  </div>
</template>

<script>
import SearchBar from '@/components/SearchBar.vue'
import MainBottom from '@/components/MainBottom.vue'
export default {
  name: 'Home',
  components: {
    SearchBar,
    MainBottom,
  },
  created() {
    this.$store.state.searchBarinHeadbar = false;
    document.documentElement.scrollTop = 0;
  },
  destroyed() {
    this.$store.state.searchBarinHeadbar = true;
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}
section {
  position: relative;
  width: 100%;
  height: 100vh;
  background: rgb(202, 237, 248);
  overflow: hidden;
}
section .wave {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100px;
  background: url('../assets/wave.png');
  background-size: 1000px 100px;
}
section .wave.wave1 {
  animation: animate 10s linear infinite;
  /* z-index: 100; */
  opacity: 1;
  animation-delay: 0s;
  bottom: 0;
}
section .wave.wave2 {
  animation: animate2 15s linear infinite;
  z-index: 99;
  opacity: 0.5;
  animation-delay: -5s;
  bottom: 10px;
}
section .wave.wave3 {
  animation: animate 15s linear infinite;
  z-index: 98;
  opacity: 0.2;
  animation-delay: -2s;
  bottom: 15px;
}
section .wave.wave4 {
  animation: animate2 10s linear infinite;
  z-index: 97;
  opacity: 0.7;
  animation-delay: 0s;
  bottom: 20px;
}
@keyframes animate {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: 1000px;
  }
}
@keyframes animate2 {
  0% {
    background-position-x: 0;
  }
  100% {
    background-position-x: -1000px;
  }
}
/* .title-wrapper {
	width: 100%;
	margin: 0 auto;
	height: 15%;
}

.title-wrapper h1 {
    text-transform: uppercase;
    transform: translate(-50%, -125%) skew(10deg) rotate(-10deg);
    font-size: 10vw;
    top: 50%;
    left: 50%;
    margin: 0;
    position: absolute;
    text-rendering: optimizeLegibility;
    font-weight: 900;
    color: rgba(202, 237, 248, 0.5);
    text-shadow: 1px 4px 6px #e6e2df, 0 0 0 #66303a, 1px 4px 6px #e6e2df;
    white-space: nowrap;		
}

.title-wrapper h1::before {
    content: attr(data-heading);
    position: absolute;
    left: -8px;
    top: -4.8%;
    overflow: hidden;
    width: 100%;
    height: 50%;
    color: #fbf7f4;
    transform: translate(1.6vw, 0) skew(-13deg) scale(1, 1.2);
    z-index: 2;
    text-shadow: 2px -1px 6px rgba(0,0,0,0.2);
}
		
.title-wrapper h1::after {
    content: attr(data-heading);
    position: absolute;
    left: 0;
    top: 0;
    overflow: hidden;
    width: 100%;
    height: 100%;
    z-index: 1;
    color: #d3cfcc;
    transform: translate(0vw, 0) skew(13deg) scale(1, 0.8);
    clip-path: polygon(0 50%, 100% 50%, 100% 100%, 0% 100%);
    text-shadow: 2px -1px 6px rgba(0,0,0,0.3);
} */
</style>
