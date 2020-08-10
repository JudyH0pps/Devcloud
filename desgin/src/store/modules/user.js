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
        setisLoggedIn(state,payload){
            state.isLoggedIn = payload
        },
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
        // loginNo() {
        //     this.showModal = false;
        // },
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