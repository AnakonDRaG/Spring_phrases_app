package com.SGW.Phrases.config

import com.SGW.Phrases.config.JWT.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider

    @Autowired
    UserDetailsService userDetailsService

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

    @Override
    void configure(AuthenticationManagerBuilder auth) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and().csrf().disable()
                .formLogin().loginPage("/api/")

        http.authorizeRequests().antMatchers(
                "/api/",
                "/api/auth/**",
                "/api/authors",
                "/api/authors/**",
                "/api/phrases",
                "/api/phrases/**",
                "/api/categories/**"

        ).permitAll().anyRequest()
                .authenticated();

    }




}


