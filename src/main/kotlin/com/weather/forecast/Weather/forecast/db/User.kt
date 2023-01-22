package com.weather.forecast.Weather.forecast.db

import com.weather.forecast.Weather.forecast.DTO.SignUpRequest
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigInteger
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    lateinit var id: BigInteger
        private set
    lateinit var name: String
    lateinit var city: String
    @Column(name = "created_at")
    @CreationTimestamp
    lateinit var createdAt: LocalDateTime
    @Column(name = "updated_at")
    @UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    companion object {
        fun createFromRequest(request: SignUpRequest): User {
            val result = User()
            result.name = request.name
            result.city = request.city
            return result
        }
    }
}