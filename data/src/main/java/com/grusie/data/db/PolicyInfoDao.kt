package com.grusie.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grusie.data.model.PolicyItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PolicyInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPolicyInfo(policyList: List<PolicyItem>)

    @Query("SELECT * FROM policyInfo")
    fun getAllPolicies(): PagingSource<Int, PolicyItem>

    @Query("SELECT * FROM policyInfo WHERE id = :policyId")
    fun getPolicy(policyId: String): Flow<PolicyItem>

    @Query("DELETE FROM policyInfo")
    suspend fun deleteAllPolicies()
}