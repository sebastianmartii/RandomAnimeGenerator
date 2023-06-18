package com.example.randomanimegenerator.feature_profile.domain.use_cases

import com.example.randomanimegenerator.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetEntriesCountUseCase(
    private val repo: ProfileRepository
) {
    operator fun invoke(type: String): Flow<List<String>> = repo.getEntriesCount(type)
}