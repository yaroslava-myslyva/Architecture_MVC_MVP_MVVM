package com.example.architecture_mvc_mvp_mvvm.domain.implementations

import com.example.architecture_mvc_mvp_mvvm.domain.AuthRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AuthRepositoryImpl: AuthRepository {
    override suspend fun login(email: String, password: String): Deferred<String> {
        return GlobalScope.async {
            Thread.sleep(300) // мок - ?
            ""
        }
    }
}