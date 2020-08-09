import http from "@/util/http-common";
import cookie from "vue-cookie"

export default {
    namespaced: true,

	state: {
        user: {
            id:"",
            email:"",
            tech : "Vue.js",
            introduction:"자기소개를 입력하세요",
            name:"",
            avatar:"",
            github:"https://github.com/",
        },
    },
	mutations: {
        setUser(state, payload){
            // state.user= payload;
            state.user.email = payload.email;
            state.user.name = payload.name;
            state.user.avatar =payload.imageUrl;
        },
        setProfile(state, payload){
            state.user = payload
        }
    },
	actions: {
        // 자기 프로필을 조회 (인증필요)
        fetchMyProfile(context,payload){
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
        //다른사람의 프로필 페이지를 들어갈때
        fetchUserProfile(context,user_id){
            http
                .get('/user/info',{
                    params : {
                        user_id : user_id
                    }
                })
                .then(({data}) => {
                    context.commit('setProfile',data)
                })
                .catch(()=>{
                    alert('다른 유저정보 조회중 오류 발생')
                })
        }
    },
	getters: {},
}