package com.weather.forecast.Weather.forecast

import com.weather.forecast.Weather.forecast.DTO.ForecastResponse
import com.weather.forecast.Weather.forecast.db.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ForecastController {
    @Value("weather.key")
    lateinit var weatherApiKey: String

    @Autowired
    lateinit var weatherClient: WeatherApiFeignClient

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/forecast")
    fun getForecastForTmrw(@RequestParam("userId") userId: Long): ForecastResponse {
        val user = userRepository.findById(userId.toBigInteger())

        if (user.isEmpty) throw ResponseStatusException(HttpStatus.BAD_REQUEST)

        val forecastResponse = weatherClient.getForecast(
            mapOf(
                "appid" to weatherApiKey,
                "days" to "2",
                "lat" to "",
                "long" to "",
                "exclude" to "hourly,minutely,current"
            )
        )

        return forecastResponse.forecast.last().condition
    }
}