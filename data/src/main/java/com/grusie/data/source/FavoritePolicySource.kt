package com.grusie.data.source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.grusie.data.model.FavoritePolicyIdList
import com.grusie.data.model.toMutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FavoritePolicySource {
    suspend fun getFavoritePolicyIdList(uid: String): Flow<List<String>>
    suspend fun setFavoritePolicy(isFavorite: Boolean, uid: String, policyId: String)
}

class FavoritePolicySourceImpl @Inject constructor(private val fireStore: FirebaseFirestore) :
    FavoritePolicySource {
    companion object {
        const val FAVORITE_DOCUMENT = "favorite"
    }

    override suspend fun getFavoritePolicyIdList(uid: String): Flow<MutableList<String>> {
        return flow {
            val favoriteData = fireStore.collection(FAVORITE_DOCUMENT).document(uid).get().await()
            emit(favoriteData.toObject<FavoritePolicyIdList>()?.toMutableList() ?: mutableListOf())
        }
    }

    override suspend fun setFavoritePolicy(isFavorite: Boolean, uid: String, policyId: String) {
        getFavoritePolicyIdList(uid).collectLatest {idList ->
            if (isFavorite) {
                idList.add(policyId)
            } else {
                idList.remove(policyId)
            }

            fireStore.collection(FAVORITE_DOCUMENT).document(uid).set(idList.joinToString(",")).await()
        }
    }
}