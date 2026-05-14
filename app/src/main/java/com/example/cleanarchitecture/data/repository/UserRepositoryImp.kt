package com.example.cleanarchitecture.data.repository

import com.example.cleanarchitecture.data.remote.RetrofitInstance
import com.example.cleanarchitecture.domain.model.DomainUserResponse
import com.example.cleanarchitecture.domain.repository.UserRepository

class UserRepositoryImp : UserRepository {
    val apiService by lazy { RetrofitInstance.getApiService() }

    override suspend fun getUser(q: Int): Result<List<DomainUserResponse>> {

      return try {
            val response = apiService.getUser(q)
            val listOfUser = response.results.map { DomainUserResponse( results = it) }
            Result.success(listOfUser)
        }catch (e : Exception){
           Result.failure(e)
        }
    }
}