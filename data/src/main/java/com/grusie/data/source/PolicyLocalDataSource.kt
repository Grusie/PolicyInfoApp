package com.grusie.data.source

import com.grusie.data.db.PolicyInfoDao
import com.grusie.data.model.PolicyItem
import kotlinx.coroutines.flow.Flow

interface PolicyLocalDataSource {
    fun getPoliciesFromDB(policyId: String): Flow<PolicyItem>
}

class PolicyLocalDataSourceImpl(private val policyInfoDao: PolicyInfoDao) : PolicyLocalDataSource {
    override fun getPoliciesFromDB(policyId: String): Flow<PolicyItem> = policyInfoDao.getPolicy(policyId)
}