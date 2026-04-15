package dev._xdbe.booking.creelhouse.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                // Step 4a: add access control
                .requestMatchers("/", "/h2-console/**").permitAll()
                .requestMatchers("/dashboard/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                // Step 4a: end
            )
            // Step 4b: Add login form
            .formLogin(withDefaults())
            // Step 4b: End of login form configuration

            .csrf((csrf) -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers ->
                headers.frameOptions(frameOptions ->
                    frameOptions.disable()
                )
            )
            .build();
    }

    // Step 3: add InMemoryUserDetailsManager
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("admin")
            .password("{bcrypt}$2a$10$Q6rwAAJWdMqESdhB9ZdjHOBkbDEVSf/HhBfRdjhy9qPtMIJfKk0nW")
            .roles("ADMIN")
            .build();
            
     	UserDetails guest = User.builder()
            .username("guest")
            .password("{bcrypt}$2a$10$Q6rwAAJWdMqESdhB9ZdjHOBkbDEVSf/HhBfRdjhy9qPtMIJfKk0nW")
            .roles("GUEST")
            .build();

        return new InMemoryUserDetailsManager(admin, guest);
    }
    // Step 3: end
}
