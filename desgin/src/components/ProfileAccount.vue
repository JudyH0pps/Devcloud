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
                        <b-button style="float:right" variant="primary" @click="save">저장하기</b-button>
                    </div>
                </div>
                <br><hr>
                <div style="width: 50%; text-align: left; margin-left: auto; margin-right: auto">
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
                    <!-- Github Url -->
                    <div>
                        <label>Github</label>
                        <b-form-input v-model="user.githubUrl" :readonly="validated"></b-form-input>
                        <p style="margin-top:10px">프로필에 표기할 Github 주소입니다.</p>
                    </div>
                    <hr>
                    <div v-if="!validated">
                        <label>Technical Skills</label>
                        <!-- <b-form-input v-model="user.tech" :readonly="validated" ></b-form-input> -->
                        <tags-input element-id="tags"
                                    v-model="resultedTags"
                                    :existing-tags="this.techsIn"
                                    :typeahead='true'
                                    placeholder="태그를 추가하세요"
                                    :typeahead-hide-discard="true"
                                    :only-existing-tags="true"
                                    :add-tags-on-blur="true"
                                    typeahead-style="badges"
                                    wrapper-class="test"
                                    >
                                    
                        </tags-input>           
                        <p style="margin-top:10px">주로 사용하는 언어와 기술사항을 표시합니다.</p>
                    </div>
                </div>
                <hr>
                <a href="#"> 더보기...</a>
            <!-- </div> -->
        </form>
    </div>

</template>
<script>
import {mapState,mapMutations,mapActions} from 'vuex';
import Vue from 'vue'
import VoerroTagsInput from '@voerro/vue-tagsinput';
Vue.component('tags-input', VoerroTagsInput);



export default {
    name: 'ProfileAccount',
    data: function() {
        return {
            validated : true,
            selectedTags:[],
            resultedTags:[],
            profileData:{
                "githubUrl": '',
                "introduction": '',
                "techList": [],
                "user_id": ''
            }
        }
    },
    computed:{
        ...mapState({
        user : state => state.user.user,
        valid : state => state.testValid,
        techsIn : state => state.tech.techsIn,
        })
    },
    methods:{
        ...mapActions('tech',['fetchTechs']),
        ...mapActions('user',['editProfile']),
        ...mapMutations(['settestValid']),
        edit:function(){
            this.validated = !this.validated
            this.settestValid(!this.valid)
        },
        save:function(){
            this.validated = !this.validated
            this.settestValid(!this.valid)
            //console.log(this.techlist)
            console.log(this.resultedTags)
            this.outputChange(this.resultedTags)
            console.log(this.profileData)
            this.editProfile(this.profileData)
        },
        //프로필수정요청 
        outputChange(arr){
            this.profileData.githubUrl = this.user.githubUrl;
            this.profileData.introduction = this.user.introduction;
            this.profileData.user_id = this.user.id;
            for(var i = 0; i < arr.length; i++)
            {
                let singleTag = {};
                singleTag.id = arr[i].id;
                singleTag.name = arr[i].value;
                this.profileData.techList.push(singleTag)
            } 
        }
    },
}
</script>

<style>
.tags-input {
    /* display: flex;
    flex-wrap: wrap;
    align-items: center; */
    display: block;
    width: 100%;
    height: calc(1.5em + .75rem + 2px);
    padding: .375rem .75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: .25rem;
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-o
}

.tags-input input {
    flex: 1;
    background: transparent;
    border: none
}

.tags-input input:focus {
    outline: none;
}

.tags-input input[type="text"] {
    color: #495057;
}

.tags-input-wrapper-default {
    padding: .5em .25em;

    background: #fff;

    border: 1px solid transparent;
    border-radius: .25em;
    border-color: #dbdbdb;
}

.tags-input-wrapper-default.active {
    border: 1px solid #8bbafe;
    box-shadow: 0 0 0 0.2em rgba(13, 110, 253, 0.25);
    outline: 0 none;
}

/* The tag badges & the remove icon */
.tags-input span {
    margin-right: 0.3em;
}

.tags-input-remove {
    cursor: pointer;
    position: absolute;
    display: inline-block;
    right: 0.3em;
    top: 0.3em;
    padding: 0.5em;
    overflow: hidden;
}

.tags-input-remove:focus {
    outline: none;
}

.tags-input-remove:before, .tags-input-remove:after {
    content: '';
    position: absolute;
    width: 75%;
    left: 0.15em;
    background: #5dc282;
    
    height: 2px;
    margin-top: -1px;
}

.tags-input-remove:before {
    transform: rotate(45deg);
}
.tags-input-remove:after {
    transform: rotate(-45deg);
}

/* Tag badge styles */
.tags-input-badge {
    position: relative;
    display: inline-block;
    padding: 0.25em 0.4em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: 0.25em;
    overflow: hidden;
    text-overflow: ellipsis;
}

.tags-input-badge-pill {
    padding-right: 1.25em;
    padding-left: 0.6em;
    border-radius: 10em;
}

.tags-input-badge-selected-default {
    color: #212529;
    background-color: #f0f1f2;
}

/* Typeahead */
.typeahead-hide-btn {
    color: #999 !important;
    font-style: italic;
}

/* Typeahead - badges */
.typeahead-badges > span {
    cursor: pointer;
    margin-right: 0.3em;
}

/* Typeahead - dropdown */
.typeahead-dropdown {
    list-style-type: none;
    padding: 0;
    margin: 0;
    position: absolute;
    width: 100%;
    z-index: 1000;
}

.typeahead-dropdown li {
    padding: .25em 1em;
    cursor: pointer;
}

/* Typeahead elements style/theme */
.tags-input-typeahead-item-default {
    color: #fff;
    background-color: #343a40;
}

.tags-input-typeahead-item-highlighted-default {
    color: #fff;
    background-color: #007bff;
}
</style>