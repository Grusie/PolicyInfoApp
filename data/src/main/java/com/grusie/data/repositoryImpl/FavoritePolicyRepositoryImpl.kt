package com.grusie.data.repositoryImpl

import com.grusie.data.source.FavoritePolicySource
import com.grusie.domain.repository.FavoritePolicyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritePolicyRepositoryImpl @Inject constructor(private val favoritePolicySource: FavoritePolicySource) :
    FavoritePolicyRepository {
    override suspend fun getFavoritePolicyIdList(uid: String): Flow<List<String>> {
        return favoritePolicySource.getFavoritePolicyIdList(uid)
    }

    override suspend fun setFavoritePolicy(isFavorite: Boolean, uid: String, policyId: String) {
        favoritePolicySource.setFavoritePolicy(isFavorite, uid, policyId)
    }
}