package com.ssafy.blog.model;

// google 이나 naver 등의 auth provider
// oauth provider 별로 로그인 후 전달해주는 data가 다르기 떄문에
// 로그인 시 provider를 확인해서 각각의 process를 거치게 됨
public enum AuthProvider {
    local,
    google
}