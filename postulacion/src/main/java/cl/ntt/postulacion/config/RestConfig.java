package cl.ntt.postulacion.config;

import cl.ntt.postulacion.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RestConfig {
    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final List<String> originsProd = Arrays.asList("https://postulacion.ntt.cl");

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(getAllowedOrigins());
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        cors.setAllowedHeaders(Arrays.asList("*"));

        http.cors().configurationSource(request -> cors).and()
                .csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedPage("/denied");

        for (String endpoint : endpoints()) {
            http.authorizeRequests().antMatchers(endpoint).permitAll();
        }

        if ("prod".equals(activeProfile)) {
            http.authorizeRequests()
                    .antMatchers("/root/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated();
        }

        return http.build();
    }

    private List<String> endpoints() {
        List<String> urls = new ArrayList<>(Arrays.asList(
                "/usuarios"
        ));
        if ("dev".equals(activeProfile)) {
            List<String> swagger = Arrays.asList("/swagger/**", "/api-docs/**", "/swagger-ui/**");
            urls.addAll(swagger);
        }
        return urls;
    }

    private List<String> getAllowedOrigins() {
        if ("dev".equals(activeProfile)) {
            return originsDev();
        } else if ("prod".equals(activeProfile)) {
            return originsProd;
        }
        return null;
    }

    private List<String> originsDev() {
        List<String> lista = new ArrayList<>();
        for (int i = 4000; i <= 5000; i++) {
            lista.add("http://localhost:" + i);
        }

        lista.add("https://desktop-1t8q8aa");
        return lista;
    }
}
