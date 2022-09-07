package com.example.architecture_mvc_mvp_mvvm.domain

import kotlinx.coroutines.Deferred

interface AuthRepository {
    suspend fun login(email: String, password: String) :Deferred<String> //?
}