package com.grusie.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grusie.data.model.PolicyRemoteKeys

@Dao
interface PolicyRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPolicyRemoteKeys(policyRemoteKeys: List<PolicyRemoteKeys>)

    @Query("SELECT * FROM policy_remote_keys WHERE id = :policyId")
    fun getPolicyRemoteKeys(policyId: String): PolicyRemoteKeys?

    @Query("DELETE FROM policy_remote_keys")
    suspend fun deletePolicyRemoteKeys()
}