package StudentsManagementSystem.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import StudentsManagementSystem.UserServices.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .exceptionHandling(exception -> exception

                .authenticationEntryPoint(
                    (request, response, authException) -> {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.setContentType("application/json");
                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"Authentication required\",\"data\":null}"
                        );
                    }
                )

                .accessDeniedHandler(
                    (request, response, accessDeniedException) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setContentType("application/json");
                        response.getWriter().write(
                            "{\"success\":false,\"message\":\"Access denied\",\"data\":null}"
                        );
                    }
                )
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/auth/register",
                    "/auth/login",
                    "/auth/register-admin"
                ).permitAll()

                .requestMatchers(HttpMethod.GET, "/Students/**")
                .hasAnyRole("USER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/Students/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/Students/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/Students/**")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}