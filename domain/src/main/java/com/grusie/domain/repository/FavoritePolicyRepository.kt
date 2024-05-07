package com.grusie.domain.repository

import com.grusie.domain.model.PolicySimple
import kotlinx.coroutines.flow.Flow

interface FavoritePolicyRepository {
    suspend fun getFavoritePolicyIdList(uid:String) : Flow<List<String>>
    suspend fun setFavoritePolicy(isFavorite: Boolean, uid: String, policyId: String)
}