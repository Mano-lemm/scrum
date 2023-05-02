//package com.example.scrumtrial.configurations;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//public class BasicAuthWebSecurityConfiguration {
//
//    @Autowired
//    private AppBasicAuthenticationEntryPoint authenticationEntryPoint;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                //.antMatchers("/registration/*").permitAll()
//                .requestMatchers("/usr/*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .authenticationEntryPoint(authenticationEntryPoint);
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(8);
//    }
//}
