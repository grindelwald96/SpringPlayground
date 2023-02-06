package com.weather.forecast.Weather.forecast.DTO.weatherapi

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("coords")
class CoordinateResponse : Serializable {
    @Id
    lateinit var name: String
    var lat: Double = 0.0
    var lon: Double = 0.0
}