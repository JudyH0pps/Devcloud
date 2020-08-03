<template>
    <div class="p-0">
        <b-navbar toggleable="lg" type="light">
            <Sidebar/>
            <!-- <b-navbar-toggle target="nav-collapse"></b-navbar-toggle> -->

            <b-collapse id="nav-collapse" is-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-form>
                        <b-form-input size="md" placeholder="Search" v-model="word"></b-form-input>
                        <b-button size="md" @click="search">Search</b-button>
                    </b-nav-form>
                    
                    <div class="ml-4 mr-4">
                        <!-- before login -->
                        <div v-if="loginTokened">
                            <div class="h2 d-inline float-left mt-3 pl-3 pr-3">
                                <b-icon icon="bell" variant="warning"></b-icon>
                            </div>
                            <div class="ml-3">
                                <b-nav-item-dropdown right>
                                    <!-- Using 'button-content' slot -->
                                    <template v-slot:button-content>
                                        <b-avatar variant="info" size="3rem"></b-avatar>
                                    </template>
                                    <b-dropdown-item router-link :to="{ name : 'Profile' }"> My Profile</b-dropdown-item>
                                    <b-dropdown-item href="#" @click="signOutBtn">Sign Out</b-dropdown-item>
                                </b-nav-item-dropdown>
                            </div>
                        </div>
                        <!-- after login -->
                        <div v-else>
                            <!-- <Btn/> -->
                            <b-button class="btn btn-info" varient="outline" size="md" @click="signInBtn">Sign In</b-button>
                        </div>
                    </div>
                </b-navbar-nav>
                
            </b-collapse>
        </b-navbar>
        

        <!-- login Modal -->
        <b-modal v-model="showModal" hide-footer title="Login">
            <div class="d-block text-center">
                <div>
                    <h3>Please Login</h3>
                </div>
                <b-button class="btn btn-success mt-3" block @click="loginOk" >OK</b-button>
                <b-button class="btn btn-warning mt-2" block @click="loginNo">NO</b-button>
            </div>
        </b-modal>
    </div>
</template>

<script>
import Sidebar from './Sidebar.vue'
// import Btn from '@/components/Btn'
import {mapState,mapMutations,mapActions} from 'vuex'


export default {
    name: 'Headbar',
    components: {
        Sidebar,
        // Btn
    },
    computed:{
        ...mapState({
            keyword : state => state.keyword,
            user : state => state.user.user,
        }),
        word :{
            get(){
                return this.keyword
            },
            set(val){
                this.setKeyword(val)
            }
        },
        loginTokened(){
            if(this.$cookie.get('logintoken'))
                return true;
            else
                return false;
        }
    },
    data() {
        return {
            localTokend: false,
            showModal: false,
        }
    },
    methods: {
        ...mapActions('user',['getUserProfile']),
        ...mapMutations(['setKeyword']),
        signInBtn() {
            this.showModal = true;
        },
        loginOk() {
            window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://localhost:3000/oauth2/redirect"'
            alert("로그인 창으로 이동합니다.");
            this.showModal = false;
        },
        loginNo() {
            this.showModal = false;
        },
        signOutBtn() {
            alert("로그아웃 되었습니다.");
            //this.loginTokened = false;

            // 로컬 스토리지 토큰 제거
            //localStorage.removeItem('localToken');
            this.$cookie.delete("logintoken");
            this.$cookie.delete("user_id");
            this.$router.push({name:'Home'});
            this.$router.go();
        },

        getParameters(paramName) { 
            // 리턴값을 위한 변수 선언 
            var returnValue; 
            // 현재 URL 가져오기 
            var url = location.href; 
            // get 파라미터 값을 가져올 수 있는 ? 를 기점으로 slice 한 후 split 으로 나눔 
            var parameters = (url.slice(url.indexOf('?') + 1, url.length)).split('&'); 
            // 나누어진 값의 비교를 통해 paramName 으로 요청된 데이터의 값만 
            for (var i = 0; i < parameters.length; i++) { 
                var varName = parameters[i].split('=')[0]; 
                if (varName.toUpperCase() == paramName.toUpperCase()) {
                    returnValue = parameters[i].split('=')[1];
                    return decodeURIComponent(returnValue); 
                } 
            } 
        },
        search(){
            //alert(this.word)
            // this.setKeyword(this.word)
            //alert(this.keyword)
            this.$router.push({
                name:'board'
            })
        }
    },
    created() {
        
        let token = this.getParameters('token')

        // 토큰이 있으면
        if(token !== undefined){
            // localStorage.setItem('localToken', token);
            this.$cookie.set('logintoken',token, '1h');
            //this.loginTokened = true;
            this.getUserProfile(this.$cookie.get('logintoken'));
            // this.$cookie.set('user_id',this.user.id,'1h');
        }
        // if(localStorage.getItem('localToken') != null){
        //     this.loginTokened = true;
        // }
        // let temp_token = link.slice(link.indexOf('?') + 1, link.length);
        // let token = temp_token.slice(temp_token.indexOf('=') + 1, temp_token.length);
        
        // console.log("토큰"+ token);
        // var test_localToken = localStorage.getItem('localToken');
        //var test_localToken = this.$cookie.get('logintoken');

        
    }
}
</script>

<style scoped>
.navbar{
    height: 80px;
}

</style>