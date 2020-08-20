import http from "@/util/http-common";

export default{
    // 모듈안에 있는 각 모듈을 인식하게 도와줌
    namespaced: true,
    // 데이터 관리
    state: {
        notifications: [],
    },
    mutations: {
        PUSH_NOTIFICATION(state, notifications) {
            state.notifications.push({
                ...notifications,
                id: (Math.random().toString(36) + Date.now().toString(36)).substr(2)
            })
        },
        setNotifications(state, payload){
            state.notifications = payload;
        }
    },
    actions: {
        addNotification({commit}, notifications) {
            commit('PUSH_NOTIFICATION', notifications)
        },
        async fetchUserNotifications(context, user_id){
            const resp = await http.get('api/notification/'+ user_id);
            //console.log(resp);
            context.commit('setNotifications',resp.data)
        }   
    },
    getters: {

    }
}