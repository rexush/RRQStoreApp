package com.example.rrqstore.utils

import com.example.rrqstore.data.RrqRepository

object Injection {
    fun provideRepository(): RrqRepository {
        return RrqRepository.getInstance()
    }
}