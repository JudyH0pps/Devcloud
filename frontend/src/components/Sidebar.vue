<template>
<!-- <nav class="sidebar"> -->
        <!-- <ul class="sidebar-nav">
            <li class="sidebar-item logo">
                <span class="logo-text">Devcloud</span>
            </li>
            <li class="sidebar-item">
                <span class="initial">H</span>
                <span class="link-text">ome</span>
            </li>
            <li class="sidebar-item">
              <span class="initial">S</span>
              <span class="link-text">Skills</span>
            </li>
            <li class="sidebar-item">
              <span class="initial">E</span>
              <span class="link-text">Experience</span>
            </li>
            <li class="sidebar-item">
              <span class="initial">C</span>
              <span class="link-text">contact</span>
            </li>
        </ul>
    </nav> -->
  <div align="left" class="m-0 p-0">
    <b-button v-b-toggle.sidebar-backdrop >></b-button>
    <b-sidebar id="sidebar-backdrop"
               title="DevCloud"
               backdrop
               shadow>
        <b-list-group>
            <b-list-group-item><router-link to="/">Home</router-link></b-list-group-item>
            <b-list-group-item><router-link :to="{ name : 'Board' }">Board</router-link></b-list-group-item>
            <!-- <b-list-group-item><router-link :to="{ name : 'Detail' }">Detail</router-link></b-list-group-item> -->
            <b-list-group-item><router-link :to="{ name : 'WriteQuestion' }">Write</router-link></b-list-group-item>
            <b-list-group-item><router-link :to="{ name : 'Ranking' }">Ranking</router-link></b-list-group-item>
        </b-list-group>
    </b-sidebar>
  </div>
</template>

<script>
export default {
    name: 'Sidebar',
}
</script>

<style scoped>

.sidebar{
    width: 5rem;
    /* width: 18rem; */
    height: 100vh;
    position: fixed;
    transition: width 200ms ease;
}

.sidebar-nav{
    list-style: none;
    padding: 0; 
    margin: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100%;
}

.sidebar-item{
    width: 100%;
    height: 8%;
}

/* .nav-item:last-child {
    margin-top: auto;
} */

.sidebar-link {
    display: flex;
    align-items: center;
    height: 5rem;
    color: var(--text-primary);
    text-decoration: none;
    filter: grayscale(100%) opacity(0.7);
    transition: 0s;
}

.sidebar-link {
    min-width: 2rem;
    margin: 0 1.5rem;
}


.logo{
    font-weight: bold;
    text-transform: uppercase;
    margin-bottom: 1rem;
    text-align: center;
    color: #2c3e50;
    background: gray;
    font-size: 1.5rem;
    letter-spacing: 0.3ch;
    width: 100%;
}

.initial,
.link-text::first-letter{
    font-weight: bold;
    text-transform: uppercase;
    margin-bottom: 1rem;
    text-align: center;
    color: var(--text-secondary);
    font-size: 1.5rem;
    margin:0;
    padding: 0;
}

.strong{
    font-weight: bold;
    text-transform: uppercase;
    text-align: center;
    margin:1.5rem 0;
    padding: 0;
}

.link-text{
    color: var(--text-secondary);
}

.logo-text {
  font-family: 'Open Sans';
  /* text-align: center; */
  margin-left: 2rem;
  /* display: none; */
}

/* 큰화면 */
@media only screen and (min-width: 600px){
    .sidebar{
        top:0;
        left:0;
        width: 5rem;
        height: 100vh;
    }

    .sidebar:hover{
        width: 18rem;
        filter: grayscale(0%) opacity(1);
        background: var(--bg-secondary);
    }

    .sidebar:hover .link-text{
        display: inline;
        transition: opacity var(--transition-speed);
    }

    .sidebar:hover .logo-text{
        display: inline;
    }

    .sidebar:hover .initial{
        display: none;
    }

    .small-size-logo{
        display: none;
    }

    .sidebar:hover .logo svg {
        transform: rotate(-180deg);
    }

    .initial,
    .link-text{
        margin-left:2rem;
    }

    .link-text{
        display: none;
    }
}

/* 작은 화면 */
@media only screen and (max-width: 600px){
    .navbar{
        bottom: 0;
        width: 100vw;
        height: 5rem;
    }

    .navbar-nav {
        flex-direction: row;
    }

    .logo{
        /* display: none; */
        margin: 0;
    }

    .nav-link {
        justify-content: center;
    }

    main{
        margin: 0;
    }

    .big-size-logo{
        display: none;
    }

    .link-text{
        display: none;
    }
    
}
</style>