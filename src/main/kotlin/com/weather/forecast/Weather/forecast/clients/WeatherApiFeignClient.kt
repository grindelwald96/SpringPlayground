package com.weather.forecast.Weather.forecast.clients

import com.weather.forecast.Weather.forecast.DTO.weatherapi.CoordinateResponse
import com.weather.forecast.Weather.forecast.DTO.weatherapi.ForecastResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "OpenWeatherMap", url = "https://api.openweathermap.org")
interface WeatherApiFeignClient {
    @GetMapping("/data/3.0/onecall")
    fun getForecast(@RequestParam requestParams: Map<String, String>): ForecastResponse

    @GetMapping("geo/1.0/direct")
    fun getCoordinates(@RequestParam requestParams: Map<String, String>): List<CoordinateResponse>
}