package ru.netology.necommerce.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import ru.netology.necommerce.dto.AnonymousUser
import ru.netology.necommerce.filter.AuthTokenFilter
import ru.netology.necommerce.service.UserService

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AppWebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var userService: UserService

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply{
            allowedOrigins = listOf("*")
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            allowCredentials = true
        })
    }

    override fun configure(web: WebSecurity?) {
        web
            ?.ignoring()
            ?.antMatchers("/media/*")
    }

    override fun configure(http: HttpSecurity?) {
        http
            ?.cors()?.and()
            ?.csrf()?.disable()
            ?.exceptionHandling()?.and()
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)?.and()
            ?.addFilterAfter(AuthTokenFilter(userService), BasicAuthenticationFilter::class.java)
            ?.anonymous {
                it.principal(AnonymousUser).authorities(*AnonymousUser.authorities.toTypedArray())
            }
            ?.authorizeRequests()?.anyRequest()?.permitAll()
    }
}