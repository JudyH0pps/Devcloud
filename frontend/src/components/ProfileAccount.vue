<template>
    <div class="container mt-2">
        <form>
            <!-- <div style="width:70%; border:1px solid black; text-align:left; margin:auto; padding:20px"> -->
                <div>
                    <h5 style="float:left">Account</h5>
                    <div v-if="validated">
                        <b-button style="float:right" variant="success" @click="edit">변경하기</b-button>
                    </div>
                    <div v-else>
                        <b-button style="float:right" variant="primary" @click="edit">저장하기</b-button>
                    </div>
                </div>
                <br><hr>
                <!-- 닉네임 -->
                <div>
                    <label>Nick Name</label>
                    <b-form-input type="text" v-model="user.name" :readonly="validated"></b-form-input>
                    <!-- user.email.split('@')[0] -->
                    <p style="margin-top:10px">공식적으로 보여지는 이름입니다.</p>
                </div>
                <hr>
                <!-- ID 또는 Email -->
                <div>
                    <label>User ID</label>
                    <b-form-input v-model="user.email" readonly></b-form-input>
                    <p style="margin-top:10px">ID는 변경하실 수 없습니다.</p>
                </div>
                <hr>
                <!-- 국적 -->
                <div>
                    <label>Region</label>
                    <!-- <b-form-input v-model="user.region" :readonly="validated" ></b-form-input> -->
                    <b-form-input v-model="user.region" :readonly="validated" ></b-form-input>
                    
                    <p style="margin-top:10px">현재 활동하고 있는 지역을 나타냅니다.</p>
                </div>
                <hr>
                <a href="#"> 더보기...</a>
            <!-- </div> -->
        </form>
    </div>

</template>
<script>
import {mapState} from 'vuex';

export default {
    name: 'ProfileAccount',
    data: function() {
        return {
            validated : true,
        }
    },
    computed:{
        ...mapState({
        user : state => state.user.user
        })
    },
    methods:{
        edit:function(){
            this.validated = !this.validated
        }
    },
}
</script>