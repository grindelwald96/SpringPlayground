package com.weather.forecast.Weather.forecast.controller

import com.weather.forecast.Weather.forecast.DTO.ForecastResponse
import com.weather.forecast.Weather.forecast.DTO.weatherapi.CoordinateResponse
import com.weather.forecast.Weather.forecast.clients.WeatherApiFeignClient
import com.weather.forecast.Weather.forecast.db.User
import com.weather.forecast.Weather.forecast.db.repository.CoordsRedisRepo
import com.weather.forecast.Weather.forecast.db.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ForecastController {
    private val logger = LoggerFactory.getLogger(ForecastController::class.java)

    @Value("\${weather.key}")
    lateinit var weatherApiKey: String

    @Autowired
    lateinit var weatherClient: WeatherApiFeignClient

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var coordsRedisRepo: CoordsRedisRepo

    @GetMapping("/forecast")
    fun getForecastForTmrw(@RequestParam("userId") userId: Long): ForecastResponse {
        val user = getUserById(userId)

        val forecastResponse = weatherClient.getForecast(
            mapOf(
                "appid" to weatherApiKey,
                "days" to "2",
                "lat" to "",
                "long" to "",
                "exclude" to "hourly,minutely,current"
            )
        )

        return forecastResponse.daily.last().weather.last()
    }

    @GetMapping("/coordinates")
    fun getUserCoordinates(@RequestParam("userId") userId: Long): CoordinateResponse {
        val user = getUserById(userId)

        val optionalCoord = coordsRedisRepo.findById(user.city)

        if (optionalCoord.isPresent) {
            logger.info("Cache hit!")
            return optionalCoord.get()
        }

        logger.info("Cache miss! Fetching coordinates from weather client")
        val coordinatesResponse = weatherClient.getCoordinates(
            mapOf(
                "appid" to weatherApiKey,
                "q" to user.city
            )
        )
        coordsRedisRepo.save(coordinatesResponse.last())
        logger.info("Caching response from weather client API: {}", coordinatesResponse)

        return coordinatesResponse.last()
    }

    private fun getUserById(userId: Long): User {
        val user = userRepository.findById(userId.toBigInteger())

        return user.orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST) }
    }
}