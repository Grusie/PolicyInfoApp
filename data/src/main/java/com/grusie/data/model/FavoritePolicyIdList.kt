package com.grusie.data.model

data class FavoritePolicyIdList(
    val idList: String = ""
)
fun FavoritePolicyIdList.toMutableList(): MutableList<String> {
    return idList.split(",").toMutableList()
}