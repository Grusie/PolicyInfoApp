package com.grusie.domain.usecase.policyusecases

import androidx.paging.PagingData
import com.grusie.domain.model.PolicySimple
import com.grusie.domain.repository.FavoritePolicyRepository
import com.grusie.domain.repository.LocalAuthRepository
import com.grusie.domain.repository.PolicyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class GetFavoritePolicyUseCase(
    private val localAuthRepository: LocalAuthRepository,
    private val policyRepository: PolicyRepository,
    private val favoritePolicyRepository: FavoritePolicyRepository
) {
    suspend operator fun invoke(): Flow<PagingData<PolicySimple>> {
        var policyList: Flow<PagingData<PolicySimple>> = flow {  }

        localAuthRepository.getLocalAuth().collect{localAuth ->
            favoritePolicyRepository.getFavoritePolicyIdList(localAuth.uid).collectLatest { idList ->
                policyList = policyRepository.getFavoritePolicyList(idList)
            }
        }


        return policyList
    }
}