package com.weather.forecast.Weather.forecast.db.repository

import com.weather.forecast.Weather.forecast.db.UserCred
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigInteger

@Repository
interface UserCredsRepository: JpaRepository<UserCred, BigInteger> {
    fun findByUsername(username: String): List<UserCred>?
}