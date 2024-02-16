package com.grusie.domain.model

data class PolicyDetail(
    val id: String,                      //정책 아이디
    val agencyType: String,              //기관 및 지자체 구분
    val name: String,                    //정책명
    val title: String,                   //정책소개
    val info: String,                    //지원 내용
    val scale: String,                   //지원 규모
    val period: String,                  //사업 운영 기간 내용
    val appPeriodCode: String,           //사업신청기간반복구분코드
    val appPeriod: String,               //사업신청기간 내용
    val ageInfo: String,                 //연령 정보
    val majorInfo: String,               //전공요건내용
    val empInfo: String,                 //취업상태 내용
    val kindInfo: String,                //특화 분야 내용
    val eduInfo: String,                 //학력요건 내용
    val locIncomeInfo: String,           //거주지 및 소득조건 내용
    val addInfo: String,                 //추가단서사항 내용
    val limitPeople: String,             //참여제한대상내용
    val submitInfo: String,              //제출서류내용
    val presentationInfo: String,        //심사발표 내용
    val applicationUrl: String,          //신청 사이트 주소
    val referUrl1: String,               //참고 사이트 주소1
    val referUrl2: String,               //참고 사이트 주소2
    val hostDepartment: String,          //주관부처명
    val mangerName: String,              //주관부처 담당자 이름
    val managerNumber: String,           //주관부처 담당자 연락처
    val organization: String,            //운영기관명
    val orgManagerName: String,          //운영기관 담당자 이름
    val etc: String,                     //기타사항
    val policyKindCode: String          //정책분야코드
)