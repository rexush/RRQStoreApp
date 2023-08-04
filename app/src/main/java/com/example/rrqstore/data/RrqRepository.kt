package com.example.rrqstore.data

import com.example.rrqstore.model.MerchData
import com.example.rrqstore.model.MerchOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RrqRepository {
    private val listOrder = mutableListOf<MerchOrder>()

    init {
        if (listOrder.isEmpty()) {
            MerchData.dummyMerch.forEach {
                listOrder.add(MerchOrder(it, 0))
            }
        }
    }

    fun getAllMerch(): Flow<List<MerchOrder>> {
        return flowOf(listOrder)
    }

    fun getMerchOrderById(merchId: Long): MerchOrder {
        return listOrder.first {
            it.merch.id == merchId
        }
    }

    fun updateMerchOrder(merchId: Long, newCountValue: Int): Flow<Boolean> {
        val index = listOrder.indexOfFirst { it.merch.id == merchId }
        val result = if (index >= 0) {
            val orderMerch = listOrder[index]
            listOrder[index] = orderMerch.copy(merch = orderMerch.merch, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedMerchOrder(): Flow<List<MerchOrder>> {
        return getAllMerch()
            .map { list ->
                list.filter { orderMerch ->
                    orderMerch.count != 0
                }
            }
    }

    fun findMerch(query: String): Flow<List<MerchOrder>> {
        return getAllMerch()
            .map { list ->
                list.filter {
                    it.merch.title.contains(query, ignoreCase = true)
                }
            }
    }

    companion object {
        @Volatile
        private var instance: RrqRepository? = null

        fun getInstance(): RrqRepository =
            instance ?: synchronized(this) {
                RrqRepository().apply {
                    instance = this
                }
            }
    }
}