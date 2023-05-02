package com.example.scrumtrial.configurations;

import com.example.scrumtrial.configurations.experemental.AppBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Securitycfg {

    private AppBasicAuthenticationEntryPoint appBasicAuthenticationEntryPoint;
    // TODO : change to UserDetailsManager
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder pwdEncoder){
        UserDetails usr = User.withUsername("admin")
                .password(pwdEncoder.encode("pwd"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(usr);
    }

    // TODO: fix security filters
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(appBasicAuthenticationEntryPoint);
        return http.build();
    }

    @Bean
    public PasswordEncoder pwdEncoder(){
        return new BCryptPasswordEncoder();
    }
}
