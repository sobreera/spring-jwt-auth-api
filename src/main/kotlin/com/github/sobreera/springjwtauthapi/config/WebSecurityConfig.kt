package com.github.sobreera.springjwtauthapi.config

import com.github.sobreera.springjwtauthapi.support.JWTAuthenticationFilter
import com.github.sobreera.springjwtauthapi.support.JWTAuthorizationFilter
import com.github.sobreera.springjwtauthapi.support.SecurityConstants.LOGIN_URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
        @Autowired val userDetailsService: UserDetailsService
): WebSecurityConfigurerAdapter() {

    private val AUTH_WHITELIST = arrayOf(
        // -- swagger ui
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**",
        // Login
        LOGIN_URL,
        // Other
        "/user/{userId}"
    )

    override fun configure(http: HttpSecurity?) {
        http!!.cors()
                .and().authorizeRequests()
                    .antMatchers(*AUTH_WHITELIST).permitAll()
                    .antMatchers("/user/{userId}/private").hasAuthority("ROLE_USER")
                    .anyRequest().authenticated()   // TODO(この記述のせいで存在しないリソースもJWTAuthorizationFilterを通り403エラーとなってしまう)
                .and().logout()
                .and().csrf().disable()
                .addFilter(JWTAuthenticationFilter(authenticationManager()))
                .addFilter(JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Autowired
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}
