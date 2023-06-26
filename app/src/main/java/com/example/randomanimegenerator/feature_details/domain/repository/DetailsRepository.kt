package com.example.randomanimegenerator.feature_details.domain.repository

import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.MainModel
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_generator.presentation.Type
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun getStatus(id: Int, type: String, userUID: String): Flow<String>

    fun getInfo(id: Int, type: Type): Flow<Resource<MainModel>>

    fun getReviews(id: Int, type: Type): Flow<Resource<List<Review>>>

    fun getRecommendations(id: Int, type: Type): Flow<Resource<List<Recommendation>>>

    fun getCharacters(id: Int, type: Type): Flow<Resource<List<Character>>>

    fun getStaff(id: Int): Flow<Resource<List<Staff>>>

    suspend fun removeFromUserFavorites(malId: Int, type: String, userUID: String)
    
    suspend fun addToUserFavorites(malId: Int, type: String, userUID: String, status: String, entryId: Int, title: String, imageUrl: String)

    suspend fun isEntryUserFavorite(malId: Int, type: String, userUID: String): Boolean
            
    suspend fun updateLibraryStatus(malId: Int, type: String, libraryStatus: String, userUID: String)
}