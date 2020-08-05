//import http from "@/util/http-common";

export default{
    // 모듈안에 있는 각 모듈을 인식하게 도와줌
    namespaced: true,
    // 데이터 관리
    state: {
        // 알림 리스트
        notifications: [],
        // 알림 수를 나타내는 state
        noticeCnt: 0
    },
    mutaions: {
        PUSH_NOTIFICATION(state, notifications) {
            state.notifications.push({
                ...notifications,
                id: (Math.random().toString(36) + Date.now().toString(36)).substr(2)
            })
        }
    },
    actions: {
        addNotification({commit}, notifications) {
            commit('PUSH_NOTIFICATION', notifications)
        }
    },
    getters: {

    }
}