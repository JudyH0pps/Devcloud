<template>
  <section class="writeform">
    <div class="form">
      <input type="text" name="title" autocomplete="off" required/>
      <label class="label-name" for="title">
        <span class="content-name">Title</span>
      </label>
    </div>
    <div class="form">
      <input type="text" name="content" autocomplete="off" required/>
      <label class="label-name" for="content">
        <span class="content-name">Content</span>
      </label>
    </div>
    <button>Submit</button>
  </section>
</template>

<script>

export default {
  name: 'Write',
  components: {

  }
}
</script>

<style scoped>
.writeform{
  font-family: "Poppins", sans-serif;
  /* margin: 80px auto 0; */
  /* padding: 10px; */
  max-width: 900px;
  min-height: 100vh;
  display: flex;
  /* justify-content: center; */
  flex-direction: column;
}

.form {
  width: 100%;
  position: relative;
  height: 50px;
  margin-left: auto;
  margin-right: auto;
  overflow: hidden;
}

.form input {
  width: 100%;
  height: 100%;
  color: #595f6e;
  padding-top: 20px;
  border: none;
  outline: none;
}

.form label {
  position: absolute;
  bottom: 0px;
  left: 0%;
  width: 100%;
  height: 100%;
  pointer-events: none;
  border-bottom: 1px solid black;
}

.form label::after {
  content: '';
  position: absolute;
  left: 0px;
  bottom: -1px;
  height: 100%;
  width: 100%;
  border-bottom: 3px solid #5fa8d3;
  transform: translateX(-100%);
  transition: transform 0.3s ease;
}
.content-name {
  position: absolute;
  bottom: 5px;
  left: 0px;
  transition: all 0.3s ease;
}
.form input:focus + .label-name .content-name,
.form input:valid + .label-name .content-name {
  transform: translateY(-150%);
  font-size: 14px;
  color: #5fa8d3;
}

.form input:focus + .label-name::after,
.form input:valid + .label-name::after {
  transform: translateX(0%);
}

button {
  /* float: right; */
  position: relative;
  /* bottom: 0; */
  transform: translate(-50%, 0);
  right: -50%;
  width: 120px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  color: #fff;
  font-size: 15px;
  text-transform: uppercase;
  text-decoration: none;
  font-family: sans-serif;
  box-sizing: border-box;
  background: linear-gradient(90deg, #03a9f4,#f441a5,#ffeb3b,#03a9f4);
  background-size: 400%;
  border-radius: 30px;
  border: 0;
  outline: 0;
  z-index: 1;
}
button:hover{
  animation: animate 8s linear infinite;
  cursor: pointer;
}
button:hover::before{
  filter: blur(20px);
  /* z-index: -1; */
  opacity: 1;
  animation: animate 8s linear infinite;
}
button::before {
  content: '';
  position: absolute;
  z-index: -1;
  top: -5px;
  left: -5px;
  right: -5px;
  bottom: -5px;
  background: linear-gradient(90deg, #03a9f4, #f441a5, #ffeb3b, #03a9f4);
  background-size: 400%;
  border-radius: 40px;
  opacity: 0;
  transition: 0.5s;
}
</style>
