package com.grusie.data.paging

import android.util.Log
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grusie.data.db.policyinfo.PolicyInfoDao
import com.grusie.data.model.PolicyItem
import com.grusie.data.service.PolicyService

/**
 * 스크랩 리스트를 위한 페이징 소스
 **/
class FavoritePagingSource(
    private val policyService: PolicyService,
    private val policyInfoDao: PolicyInfoDao,
    private val idList : List<String>
) : PagingSource<Int, PolicyItem>() {
    override fun getRefreshKey(state: PagingState<Int, PolicyItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PolicyItem> {
        return try {
            val page = params.key ?: 1
            val pageSize = 10

            val policyList = mutableListOf<PolicyItem>()


            val startIndex = (page - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, idList.size)

            idList.subList(startIndex, endIndex).forEach { id ->
                policyService.getPolicyList(policyId = id).youthPolicy?.get(0)?.let { policyItem ->
                    policyList.add(policyItem)
                }
            }

            policyInfoDao.addPolicyInfo(policyList)
            Log.d("confirm load : ", "$page")
            val endOfPaginationReached = endIndex >= pageSize || endIndex >= idList.size - 1

            LoadResult.Page(
                data = policyList,
                prevKey = if(page <= 1) null else page - 1,
                nextKey = if(endOfPaginationReached) null else page + 1
            )
        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }

}