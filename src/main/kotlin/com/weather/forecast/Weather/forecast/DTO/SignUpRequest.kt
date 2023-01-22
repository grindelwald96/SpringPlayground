package com.weather.forecast.Weather.forecast.DTO

class SignUpRequest : LoginRequest() {
    lateinit var name: String
        private set
    lateinit var city: String
        private set
}