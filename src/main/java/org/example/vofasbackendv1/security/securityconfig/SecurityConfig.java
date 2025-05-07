package org.example.vofasbackendv1.security.securityconfig;
import org.example.vofasbackendv1.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint entryPoint;

    private final JwtAuthenticationFilter jwtFilter;


    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint entryPoint, JwtAuthenticationFilter jwtFilter) {
        this.entryPoint = entryPoint;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/vofas/api/v1/user/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/vofas/api/v1/static-qr/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/vofas/api/v1/static-qr/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/vofas/api/v1/static-qr/**").hasAnyAuthority("ADMIN", "COMPANY_REPRESENTATIVE")
                        .requestMatchers("/vofas/api/v1/my-account/**").hasAnyAuthority("ADMIN", "COMPANY_REPRESENTATIVE")
                        .requestMatchers("/vofas/api/v1/**").permitAll()
                        .requestMatchers("/swagger-ui").permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
