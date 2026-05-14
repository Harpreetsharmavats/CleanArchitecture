package com.example.cleanarchitecture.domain.useCases

import com.example.cleanarchitecture.data.repository.UserRepositoryImp
import com.example.cleanarchitecture.domain.model.DomainUserResponse
import com.example.cleanarchitecture.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserUseCase {

    private val repository : UserRepository by lazy { UserRepositoryImp() }

    operator fun invoke(q : Int) = flow<Result<List<DomainUserResponse>>>{
        val response = repository.getUser(q)
        emit(response)
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}