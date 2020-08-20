<template>
    <section class="account">
        <button v-if="validated" variant="success" @click="edit"><i class="fas fa-cog"></i>프로필 수정하기</button>
        <button v-else variant="primary" @click="save"><i class="far fa-clipboard"></i>저장하기</button>

        <!-- 닉네임 -->

        <div class="formdiv">
            <label>Nick Name</label>
            <input type="text" v-model="user.name" :readonly="validated">
            <!-- user.email.split('@')[0] -->
            <p style="font-size: 12px; margin-top:10px; margin-bottom: 10px;">공식적으로 보여지는 이름입니다.</p>
        </div>
        <!-- ID 또는 Email -->
        <div class="formdiv">
            <label>User ID</label>
            <input v-model="user.email" readonly>
            <p style="font-size: 12px; margin-top:10px; margin-bottom: 10px;">ID는 변경하실 수 없습니다.</p>
        </div>
    
        <!-- Github Url -->
        <div class="formdiv">
            <label>Github</label>
            <input v-model="user.githubUrl" :readonly="validated">
            <p style="font-size: 12px; margin-top:10px; margin-bottom: 10px;">프로필에 표기할 Github 주소입니다.</p>
        </div>

        <div class="formdiv">
            <label>Introduction</label>
            <input v-model="user.introduction" :readonly="validated">
            <p style="font-size: 12px; margin-top:10px; margin-bottom: 10px;">자신을 소개할 문구입니다.</p>
        </div>
        
        <div class="formdiv" v-if="!validated">
            <div >
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
                            :typeahead-max-results="tagListMax"
                            :limit="posTagCnt"
                            typeahead-style="dropdown"
                            wrapper-class="test"
                            >
                            
                </tags-input>           
                <p style="font-size: 12px; margin-top:10px; margin-bottom: 10px;">주로 사용하는 언어와 기술사항을 표시합니다.</p>
            </div>
        </div>
        <button style="margin-left:auto;" v-if="!validated" variant="primary" @click="save"><i class="far fa-clipboard"></i>저장하기</button>
    </section>
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
            tagListMax : 10,
            posTagCnt : 4,
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
        edit(){
            this.validated = !this.validated
            this.settestValid(!this.valid)
        },
        save(){
            this.validated = !this.validated
            this.settestValid(!this.valid)
            //console.log(this.techlist)
            // console.log(this.resultedTags)
            this.outputChange(this.resultedTags)
            // console.log(this.profileData)
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
section.account {
    padding: 40px;
}
button {
    border: 1px solid #eee;
    background: white;
    margin-left: auto;
    outline: none;
    height: 25px;
    border-radius: 5px;
    margin-bottom: 5px;
    padding: 2px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .5);
}
button:hover {
    cursor: pointer;
    background: #eee;
}
label {
    width: 100%;
}
input {
    width: 100%;
    border: 0;
    border-bottom: 1px solid black;
}
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
.formdiv {
    background: white;
    border-radius: 10px;
    padding: 5px;
    margin-bottom: 5px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .5);
}
</style>