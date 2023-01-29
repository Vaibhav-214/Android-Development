package com.example.amphibians.data

import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApiService

interface AmphibianRepository {
    suspend fun getAmphibianData(): List<Amphibian>
}

class OnlineAmphibianRepository (
   private val amphibianApiService: AmphibianApiService
        ): AmphibianRepository {
    override suspend fun getAmphibianData(): List<Amphibian> {
        return amphibianApiService.getAmphibians()
    }
}