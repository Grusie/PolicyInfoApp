package com.grusie.data.source

import com.grusie.data.db.policyinfo.PolicyInfoDao
import com.grusie.data.model.PolicyItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PolicyLocalDataSource {
    fun getPoliciesFromDB(policyId: String): Flow<PolicyItem?>
}

class PolicyLocalDataSourceImpl @Inject constructor(private val policyInfoDao: PolicyInfoDao) : PolicyLocalDataSource {
    override fun getPoliciesFromDB(policyId: String): Flow<PolicyItem?> = policyInfoDao.getPolicy(policyId)
}