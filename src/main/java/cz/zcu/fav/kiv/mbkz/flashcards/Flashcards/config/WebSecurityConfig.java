package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.config;


import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String KEYCLOAK_RESOURCE_FILE = "security/keycloak.json";

    @Bean
    public AdapterConfig getKeycloakAdapterConfig() throws IOException {
        ClassPathResource keycloakConfigResource = new ClassPathResource(KEYCLOAK_RESOURCE_FILE);
        InputStream keycloakConfigInputStream = keycloakConfigResource.getInputStream();
        return KeycloakDeploymentBuilder.loadAdapterConfig(keycloakConfigInputStream);
    }


    @Bean
    public KeycloakDeployment getKeycloakDeployment(AdapterConfig adapterConfig) {
        return KeycloakDeploymentBuilder.build(adapterConfig);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
