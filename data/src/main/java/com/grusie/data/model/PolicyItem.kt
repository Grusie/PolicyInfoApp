package com.grusie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.grusie.domain.model.PolicyDetail
import com.grusie.domain.model.PolicySimple
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "youthPolicy")
@Entity(tableName = "policyInfo")
data class PolicyItem(
    @PrimaryKey(autoGenerate = false)
    @PropertyElement(name = "bizId")           //정책 ID
    val id: String,
    @PropertyElement(name = "polyBizSecd")     //정책일련번호
    val serialNum: String,
    @PropertyElement(name = "polyBizTy")       //기관 및 지자체 구분
    val agencyType: String,
    @PropertyElement(name = "polyBizSjnm")     //정책명
    val name: String,
    @PropertyElement(name = "polyItcnCn")      //정책소개
    val subInfo: String,
    @PropertyElement(name = "sporCn")          //지원내용
    val info: String,
    @PropertyElement(name = "sporScvl")        //지원규모
    val scale: String,
    @PropertyElement(name = "bizPrdCn")        //사업운영기간내용
    val period: String,
    @PropertyElement(name = "prdRpttSecd")     //사업신청기간반복구분코드
    val appPeriodCode: String,
    @PropertyElement(name = "rqutPrdCn")       //사업신청기간내용
    val appPeriod: String,
    @PropertyElement(name = "ageInfo")         //연령 정보
    val ageInfo: String,
    @PropertyElement(name = "majrRqisCn")      //전공요건내용
    val majorInfo: String,
    @PropertyElement(name = "empmSttsCn")      //취업상태내용
    val empInfo: String,
    @PropertyElement(name = "splzRlmRqisCn")   //특화분야내용
    val kindInfo: String,
    @PropertyElement(name = "accrRqisCn")      //학력요건내용
    val eduInfo: String,
    @PropertyElement(name = "prcpCn")          //거주지및소득조건내용
    val locIncomeInfo: String,
    @PropertyElement(name = "aditRscn")        //추가단서사항내용
    val addInfo: String,
    @PropertyElement(name = "prcpLmttTrgtCn")  //참여제한대상내용
    val limitPeople: String,
    @PropertyElement(name = "rqutProcCn")      //신청절차내용
    val appProcess: String,
    @PropertyElement(name = "pstnPaprCn")      //제출서류내용
    val submitInfo: String,
    @PropertyElement(name = "jdgnPresCn")      //심사발표내용
    val presentationInfo: String,
    @PropertyElement(name = "rqutUrla")        //신청사이트주소
    val applicationUrl: String,
    @PropertyElement(name = "rfcSiteUrla1")    //참고사이트URL주소1
    val referUrl1: String,
    @PropertyElement(name = "rfcSiteUrla2")    //참고사이트URL주소2
    val referUrl2: String,
    @PropertyElement(name = "mngtMson")        //주관부처명
    val hostDepartment: String,
    @PropertyElement(name = "mngtMrofCherCn")  //주관부처담당자이름
    val managerName: String,
    @PropertyElement(name = "cherCtpcCn")      //주관부처담당자연락처
    val managerNumber: String,
    @PropertyElement(name = "cnsgNmor")        //운영기관명
    val organization: String,
    @PropertyElement(name = "tintCherCn")      //운영기관담당자이름
    val orgManagerName: String,
    @PropertyElement(name = "tintCherCtpcCn")  //운영기관담당자연락처
    val orgManagerNum: String,
    @PropertyElement(name = "etct")            //기타사항
    val etc: String,
    @PropertyElement(name = "polyRlmCd")       //정책분야코드
    val policyKindCode: String
)

fun PolicyItem.toPolicyDetail(): PolicyDetail {
    return PolicyDetail(
        id = id,
        agencyType = agencyType,
        name = name,
        subInfo = subInfo,
        info = info,
        scale = scale,
        period = period,
        appPeriodCode = appPeriodCode,
        appPeriod = appPeriod,
        ageInfo = ageInfo,
        majorInfo = majorInfo,
        empInfo = empInfo,
        kindInfo = kindInfo,
        eduInfo = eduInfo,
        locIncomeInfo = locIncomeInfo,
        addInfo = addInfo,
        limitPeople = limitPeople,
        submitInfo = submitInfo,
        presentationInfo = presentationInfo,
        applicationUrl = applicationUrl,
        referUrl1 = referUrl1,
        referUrl2 = referUrl1,
        hostDepartment = hostDepartment,
        managerName = managerName,
        managerNumber = managerNumber,
        organization = organization,
        orgManagerName = orgManagerName,
        orgManageNumber = orgManagerNum,
        etc = etc,
        policyKindCode = policyKindCode
    )
}

fun PolicyItem.toPolicySimple(): PolicySimple {
    return PolicySimple(
        id = id,
        name = name,
        subInfo = subInfo,
        period = period,
        ageInfo = ageInfo,
        kindInfo = kindInfo,
        organization = organization
    )
}