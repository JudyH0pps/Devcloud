import http from "@/util/http-common";
import cookie from "vue-cookie"

export default {
    namespaced: true,

	state: {
        user: {
            id:"",
            email:"",
            region:"default region",
            introduction:"자기소개를 입력하세요",
            name:"",
            avatar:"",
            
        },
        isLoggedIn: false,
    },
	mutations: {
        setUser(state, payload){
            // state.user= payload;
            state.user.email = payload.email;
            state.user.name = payload.name;
            state.user.avatar =payload.imageUrl;
        }
    },
	actions: {
        fetchUserProfile(context,payload){
            http
                .get('/user/me',{
                    headers:{
                        Authorization: 'Bearer ' + payload
                    }
                })
                .then(({data}) => {
                    //console.log(data)
                    context.commit('setUser',data);
                    cookie.set('user_id',data.id,'1h')
                    cookie.set('user_image',data.imageUrl,'1h')
                    // console.log(data['data']);
                })
                .catch(() =>{
                    //alert('유저정보 조회중 오류 발생');
                });
        },
              // signInBtn() {
      //   this.showModal = true;
      // },
        googleLogin() {
            // alert('asdfsad21312f');
            // alert('asdfsadf');
            window.location.href = 'http://i3c202.p.ssafy.io:8080/oauth2/authorize/google?redirect_uri="http://localhost:3000/"'
            // alert("구글로그인 창으로 이동합니다.");
        },
        // loginNo() {
        //     this.showModal = false;
        // },
        signOutBtn() {
            alert("로그아웃 되었습니다.");
            this.loginTokened = false;

            // 로컬 스토리지 토큰 제거
            //localStorage.removeItem('localToken');
            this.$cookie.delete("logintoken");
            this.$cookie.delete("user_id");
            this.$router.push({name:'Home'});
            this.$router.go();
        },


        search(){
            //alert(this.word)
            // this.setKeyword(this.word)
            //alert(this.keyword)
            this.$router.push({
                name:'Board'
            })
        }
    },
	getters: {},
}