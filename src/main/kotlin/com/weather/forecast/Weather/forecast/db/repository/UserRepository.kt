package com.weather.forecast.Weather.forecast.db.repository

import com.weather.forecast.Weather.forecast.db.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigInteger

@Repository
interface UserRepository: JpaRepository<User, BigInteger>