import http from "@/util/http-common";

export default {
    namespaced: true,

	state: {
        techs : [],
        techsIn:[],
        isTechs : false,
        tech : {
                key : "",
                value : "",
                id : "",
                }
    },
	mutations: {
        //스테이트 태그 변경 
        setTechs(state, payload){
          state.techs = payload;
          state.isTechs = true;
        },
        setTechsIn(state,payload){
            state.techsIn = payload
        },
        setTech(state,payload){
            state.tech.key = payload.name;
            state.tech.value = payload.name;
            state.tech.id = payload.id;
        }
    },
	actions: {
        // 태그리스트 조회
        fetchTechs(context){
            http
                .get('/api/tech')
                .then(({data}) => {
                    //console.log(data)
                    //console.log(data.length)
                    context.commit('setTechs',data)
                })
                .catch(() =>{
                    alert("태그목록조회중 에러");
                });
        },
    },
	getters: {},
}