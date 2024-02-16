package com.grusie.domain.model

data class PolicySimple(
    val policyId: String,           //정책 아이디
    val policyName: String,      //정책 이름
    val policyTitle: String,     //정책 소개 제목
    val policyPeriod: String,    //정책 기간
    val ageInfo: String,         //연령 정보
    val policyKind: String,      //특화 분야
    val organization: String    //운영 기관 명
)