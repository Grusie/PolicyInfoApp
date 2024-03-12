package com.grusie.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grusie.data.model.PolicyItem
import com.grusie.data.service.PolicyService

/**
 * 검색을 위한 페이징 소스
 **/
class PolicyPagingSource(
    private val policyService: PolicyService,
    private val query: String?,
    private val policyTypeCode: Int?,
    private val policyRegionCode: Int?,
    private val keyword: String?
) : PagingSource<Int, PolicyItem>() {
    override fun getRefreshKey(state: PagingState<Int, PolicyItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PolicyItem> {
        return try {
            val page = params.key ?: 1

            val policyList = policyService.getPolicyList(
                page = page,
                query = query,
                policyTypeCode = policyTypeCode,
                policyRegionCode = policyRegionCode,
                keyword = keyword
            )

            Log.d("confirm load : ", "$page")
            val endOfPaginationReached = policyList.youthPolicy.isNullOrEmpty()

            LoadResult.Page(
                data = policyList.youthPolicy ?: emptyList(),
                prevKey = if(page <= 1) null else page - 1,
                nextKey = if(endOfPaginationReached) null else page + 1
            )
        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }

}