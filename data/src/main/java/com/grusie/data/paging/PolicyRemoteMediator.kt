package com.grusie.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.grusie.data.BuildConfig
import com.grusie.data.db.PolicyInfoDB
import com.grusie.data.model.PolicyItem
import com.grusie.data.model.PolicyRemoteKeys
import com.grusie.data.service.PolicyService

/**
 * 로컬DB와 연계 되어있는 RemoteMediator
 **/

@OptIn(ExperimentalPagingApi::class)
class PolicyRemoteMediator(
    private val policyService: PolicyService,
    private val policyInfoDB: PolicyInfoDB
) : RemoteMediator<Int, PolicyItem>() {
    private val policyDao = policyInfoDB.policyDao()
    private val policyRemoteKeysDao = policyInfoDB.policyRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PolicyItem>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prePage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }
            Log.d("confirm page", " $page, $loadType")
            val policyList =
                policyService.getPolicyList(
                    page = page
                )
            val endOfPaginationReached = policyList.youthPolicy.isNullOrEmpty()

            policyInfoDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    policyDao.deleteAllPolicies()
                    policyRemoteKeysDao.deletePolicyRemoteKeys()
                }
                var prevPage: Int?
                var nextPage: Int?

                policyList.pageIndex.let { pageIndex ->
                    nextPage = pageIndex + 1
                    prevPage = if (pageIndex <= 1) null else pageIndex - 1
                }

                val keys = policyList.youthPolicy?.map { policyItem ->
                    PolicyRemoteKeys(
                        id = policyItem.id,
                        prePage = prevPage,
                        nextPage = nextPage
                    )
                }
                keys?.let { policyRemoteKeysDao.addPolicyRemoteKeys(policyRemoteKeys = it) }
                policyList.youthPolicy?.let { policyDao.addPolicyInfo(policyList = it) }
            }
            Log.d("confirm page success", "$endOfPaginationReached")
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.d("confirm page error", "${e.message}")
            return MediatorResult.Error(e)
        }
    }

    private fun getRemoteKeyCurrentPosition(state: PagingState<Int, PolicyItem>): PolicyRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                policyRemoteKeysDao.getPolicyRemoteKeys(id)
            }
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, PolicyItem>): PolicyRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { policy ->
                policyRemoteKeysDao.getPolicyRemoteKeys(policyId = policy.id)
            }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, PolicyItem>): PolicyRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { policy ->
            policyRemoteKeysDao.getPolicyRemoteKeys(policy.id)
        }

    }
}