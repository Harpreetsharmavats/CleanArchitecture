package com.example.cleanarchitecture.domain.repository

import com.example.cleanarchitecture.domain.model.DomainUserResponse

interface UserRepository {
    suspend fun getUser(q: Int) : Result<List<DomainUserResponse>>
}