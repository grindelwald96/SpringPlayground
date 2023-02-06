package com.weather.forecast.Weather.forecast.db.repository

import com.weather.forecast.Weather.forecast.DTO.weatherapi.CoordinateResponse
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CoordsRedisRepo: CrudRepository<CoordinateResponse, String>