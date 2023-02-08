package com.linda.APLproject.Configuration;

import com.linda.APLproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppSecurityConfiguration {
    private final AppPasswordConfig bcrypt;
    private final UserService userService;
    @Autowired
    public AppSecurityConfiguration(AppPasswordConfig bcrypt, UserService userService) {
        this.bcrypt = bcrypt;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // .csrf().disable()

                .authorizeHttpRequests( requests -> {
                            requests
                                    .requestMatchers( "/","/login", "/logout" , "/error", "/rest/**", "/register", "/static/**").permitAll()
                                    .requestMatchers("/admin").hasRole("ADMIN")
                                    .anyRequest()
                                    .authenticated();
                        }
                )

                .formLogin( formlogin -> {
                            formlogin.loginPage("/login");
                        }
                )
                .rememberMe(rememberMe -> {
                            rememberMe
                                    .rememberMeParameter("remember-me")
                                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))   // 3 Weeks
                                    .key("someSecureKey")
                                    .userDetailsService(userService);
                        }
                )

                .logout( logout -> {
                            logout
                                    .logoutUrl("/logout")
                                    .logoutSuccessUrl("/login")
                                    .clearAuthentication(true)
                                    .invalidateHttpSession(true)
                                    .deleteCookies("remember-me", "JSESSIONID");
                        }
                )
                .authenticationProvider(authenticationOverride());

        return http.build();
    }
    public DaoAuthenticationProvider authenticationOverride() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userService);            // Query
        provider.setPasswordEncoder(bcrypt.bCryptPasswordEncoder()); // Encoder BCRYPT

        return provider;
    }
}
