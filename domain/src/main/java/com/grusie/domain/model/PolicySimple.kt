package com.grusie.domain.model

data class PolicySimple(
    val id: String = "",             //정책 아이디
    val name: String = "",           //정책 이름
    val subInfo: String = "",        //정책 소개 제목
    val period: String = "",         //정책 기간
    val ageInfo: String = "",        //연령 정보
    val kindInfo: String = "",       //특화 분야
    val organization: String = ""    //운영 기관 명
)