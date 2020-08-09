<template>
    <div class="p-0">
        <b-navbar toggleable="lg" type="light">
            <Sidebar/>
            <!-- <b-navbar-toggle target="nav-collapse"></b-navbar-toggle> -->

            <b-collapse id="nav-collapse" is-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <div class="input-group">
                        <b-form-input size="lg" placeholder="검색어를 넣어주세요 >>" @keyup.enter="searchItem" v-model="keyword"></b-form-input>
                        <button type="button" class="btn btn-dark" style="height: 48px;" @click="searchItem">Search</button>
                    </div>
                    
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
                                        <img 
                                            alt="profile picture"
                                            class="border rounded-circle profile-picture"
                                            :src="userImage"
                                            style="width: 48px; height: 48px;">
                                    </template>
                                    <!-- <b-dropdown-item router-link :to="{ name : 'Profile' , params : { user_id : this.$cookie.get('user_id')}}"> My Profile</b-dropdown-item> -->
                                    <b-dropdown-item @click="moveProfile"> My Profile</b-dropdown-item>
                                    <b-dropdown-item href="#" @click="signOutBtn">Sign Out</b-dropdown-item>
                                </b-nav-item-dropdown>
                            </div>
                        </div>
                        <!-- after login -->
                        <div v-else>
                            <!-- <Btn/> -->
                            <button type="button" style="width: 100px; height: 48px;" class="btn btn-primary" varient="outline" size="md" @click="signInBtn">Sign In</button>
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
                <b-button class="btn btn-danger mt-3" block @click="loginOk" >google Login</b-button>
                <b-button class="btn btn-dark mt-2" block @click="loginNo">Cancel</b-button>
            </div>
        </b-modal>
    </div>
</template>

<script>
import Sidebar from './Sidebar.vue'
// import Btn from '@/components/Btn'
import {mapState,mapActions} from 'vuex'


export default {
    name: 'Headbar',
    components: {
        Sidebar,
        // Btn
    },
    computed:{
        ...mapState({
            //keyword : state => state.keyword,
            user : state => state.user.user,
        }),
        keyword:{
            get(){
                if(this.$route.name == 'Board')
                    return this.$route.params.search_keyword
                else
                    return null
            },
            set(val){
                return this.setKeyword = val
            }
        },
        loginTokened(){
            if(this.$cookie.get('logintoken'))
                return true;
            else
                return false;
        },
    },
    data() {
        return {
            localTokend: false,
            showModal: false,
            userImage : this.$cookie.get('user_image'),
            setKeyword : ""
        }
    },
    methods: {
        ...mapActions('user',['fetchMyProfile']),
       
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
        searchItem(){
            if(this.setKeyword != ""){
                this.$router.push({
                    name:'Board',
                    params:{
                        search_keyword : this.setKeyword
                    }
                }).catch(()=>{
                    if(this.setKeyword != ""){
                        this.$router.go()
                    }
                });
            }else{
                if(this.setKeyword != "")
                    this.$router.go()
            }
        },
        moveProfile(){
            // if(this.$route.name == 'Profile'){
            //     this.$route.params.user_id = this.$cookie.get('user_id')
            //     this.$router.go()
            // }

            this.$router.push({
                name : 'Profile',
                params : {
                    user_id : this.$cookie.get('user_id')
                }
            }).catch(()=>{
                // 프로필에서 다시 프로필로 이동시에 새로고침 해줌
                this.$router.go()
            })
        }
    },
    // watch : {
    //     loginTokened:function(){
    //         this.$router.go()
    //     }
    // },
    created() {
        let token = this.getParameters('token')
        // 토큰이 있으면
        if(token !== undefined){
            // localStorage.setItem('localToken', token);
            this.$cookie.set('logintoken',token, '1h');
            //this.loginTokened = true;
            this.fetchMyProfile(this.$cookie.get('logintoken'));
            // this.$cookie.set('user_id',this.user.id,'1h');
            
        }

        
    },
}
</script>

<style scoped>
.navbar{
    height: 80px;
}

</style>