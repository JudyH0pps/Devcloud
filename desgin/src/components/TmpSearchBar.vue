<template>
  <div class="search-box">
      <input class="search-txt" type="text" name="" placeholder="Type to search">
      <i class="search-btn fas fa-search"></i>
  </div>

</template>
<script>

</script>
<style scoped>
.search-box {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: white;
    height: 40px;
    border-radius: 40px;
    padding: 10px; 
    display: flex;
    align-items: center;
    padding-right: 0;
}
/* .search-box::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    background: #2f3640;
    height: 40px;
    width: 550px;
    border-radius: 40px;
    padding: 10px; 
    display: flex;
    align-items: center;
    padding-right: 0;
    z-index: -1;
} */
.search-btn {
    color: #e84118;
    float: right;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #2f3640;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}
.search-txt {
    border: none;
    background: none;
    outline: none;
    float: left;
    padding: 0;
    color: black;
    font-size: 16px;
    transition: 0.4s;
    line-height: 20px;
    height: 25px;
    width: 500px;
    background: white;
}

</style>
