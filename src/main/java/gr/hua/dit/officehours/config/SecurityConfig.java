package gr.hua.dit.officehours.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration.
 */
@Configuration
@EnableMethodSecurity // enables @PreAuthorize
public class SecurityConfig {

    // TODO API Security (stateless - JWT based)

    /**
     * UI chain {@code "/**"} (stateful - cookie based).
     */
    @Bean
    @Order(2)
    public SecurityFilterChain uiChain(final HttpSecurity http) throws Exception {
        http
            .securityMatcher("/**")
            .authorizeHttpRequests(auth -> auth
                // TODO EXERCISE permit all: / /login /register
                // TODO EXERCISE authenticated: /profile /logout
                .anyRequest().permitAll() // everything else.
            )
            .formLogin(form -> form
                .loginPage("/login") // custom login page (see login.html)
                .loginProcessingUrl("/login") // POST request target (handled by Spring Security)
                .defaultSuccessUrl("/profile", true)
                // TODO EXERCISE failure url /login? (error)
                .permitAll()
            )
            .logout(logout -> logout
                // .logoutUrl("/logout") // POST request target (handled by Spring Security) // TODO EXERCISE uncomment
                // TODO logout success url (+ message?)
                // TODO EXERCISE Delete cookies JSESSIONID
                // TODO EXERCISE invalidate HTTP Session.
                .permitAll()
            )
            // Disable basic security.
            .httpBasic(basic -> {});

        return http.build();
    }

    // TODO EXERCISE We need a password encoder.

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
