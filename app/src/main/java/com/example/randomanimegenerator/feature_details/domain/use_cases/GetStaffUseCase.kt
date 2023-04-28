package com.example.randomanimegenerator.feature_details.domain.use_cases

import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository

class GetStaffUseCase(
    private val repository: DetailsRepository
) {
    operator fun invoke(id: Int) = repository.getStaff(id)
}