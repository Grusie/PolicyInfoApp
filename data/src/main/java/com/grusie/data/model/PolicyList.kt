package com.grusie.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "youthPolicyList")
data class PolicyList(
    @PropertyElement
    val pageIndex: Int,
    @PropertyElement
    val totalCnt: Int,
    @Element
    val youthPolicy: List<PolicyItem>?
)