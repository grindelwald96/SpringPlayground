package com.weather.forecast.Weather.forecast

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class WeatherForecastApplication

fun main(args: Array<String>) {
	runApplication<WeatherForecastApplication>(*args)
}
