import http from "@/util/http-common";

export default {
    namespaced: true,

	state: {
        // 받아오는 태그 
        tagss : [],
        // 받아와서 형식 바꿀 태그 
        tagsIn:[],
        // 리스트 받아왔는지 체크
        isTags : false,
        tags : {
                key : "",
                value : "",
                id : "",
                }
    },
	mutations: {
        //스테이트 태그 변경 
        setTags(state, payload){
          state.tagss = payload;
          state.isTags = true;
        },
        setTechsIn(state,payload){
            state.tagsIn = payload
        },
    },
	actions: {
        // 태그리스트 조회
        async fetchTags(context){
            // http
            //     .get('/api/tag')
            //     .then(({data}) => {
            //         //console.log(data)
            //         //console.log(data.length)
            //         context.commit('setTags',data)
            //     })
            //     .catch(() =>{
            //         alert("질문태그목록조회중 에러");
            //     });
            const resp = await http.get('api/tag');
            console.log(resp);
            context.commit('setTags',resp.data)
        },
    },
	getters: {
        tagss(state){
            let selectedTags = [];
            for(var i = 0; i < state.tagss.length; i++)
            {
                let singleTag = {};
                singleTag.key = state.tagss[i].name;
                singleTag.value = state.tagss[i].name;
                singleTag.id = state.tagss[i].id;
                selectedTags.push(singleTag)
            }
            return selectedTags
        }
    },
}