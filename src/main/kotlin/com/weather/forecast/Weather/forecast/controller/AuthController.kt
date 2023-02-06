package com.weather.forecast.Weather.forecast.controller

import com.weather.forecast.Weather.forecast.DTO.LoginRequest
import com.weather.forecast.Weather.forecast.DTO.SignUpRequest
import com.weather.forecast.Weather.forecast.db.User
import com.weather.forecast.Weather.forecast.db.UserCred
import com.weather.forecast.Weather.forecast.db.repository.UserCredsRepository
import com.weather.forecast.Weather.forecast.db.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class AuthController {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userCredsRepository: UserCredsRepository

    @PostMapping("/register")
    fun registerUser(@RequestBody registerRequest: SignUpRequest): String {
        // Validate there are no users with the given username
        if (userCredsRepository.findByUsername(registerRequest.username)?.isNotEmpty() == true)
            throw ResponseStatusException(HttpStatusCode.valueOf(409), "Username already present ! Try with a different one")

        val newUser = User.createFromRequest(registerRequest)
        userRepository.save(newUser)
        val newCred = UserCred.createFromRequestAndUser(newUser, registerRequest)
        userCredsRepository.save(newCred)

        return "SUCCESS"
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: LoginRequest): String {
        val userCred = userCredsRepository.findByUsername(loginRequest.username)
        if (userCred?.isEmpty() == true)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found")
        if (userCred!![0].password != loginRequest.password)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Password incorrect")
        return "Succes you are now logged in !"
    }
}