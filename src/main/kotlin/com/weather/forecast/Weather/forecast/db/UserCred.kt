package com.weather.forecast.Weather.forecast.db

import com.weather.forecast.Weather.forecast.DTO.SignUpRequest
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigInteger
import java.time.LocalDateTime

@Entity
@Table(name = "user_creds")
class UserCred {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var id: BigInteger
    @Column(name = "user_id")
    lateinit var userId: BigInteger
    lateinit var username: String
    lateinit var password: String
    @Column(name = "created_at")
    @CreationTimestamp
    lateinit var createdAt: LocalDateTime
    @Column(name = "updated_at")
    @UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun createFromRequestAndUser(user: User, request: SignUpRequest): UserCred {
            val userCred = UserCred()

            userCred.userId = user.id
            userCred.username = request.username
            userCred.password = request.password

            return userCred
        }
    }
}