package com.example.employee_management.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
@Configuration

@RequiredArgsConstructor

public class SwaggerConfig {
    @Bean

    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("educationManage-service API")
                .description("This is API list that will be used in the system.")
                .version("1.0.0"));
        /* Begin: Add headers */

        Components components = new Components();
        SecurityRequirement securityRequirement = new SecurityRequirement();
// bearer jwt

        String securitySchemeName = "Bearer-Token";
        components.addSecuritySchemes(
                securitySchemeName,
                new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
        securityRequirement.addList(securitySchemeName);
        openAPI.components(components);
        openAPI.addSecurityItem(securityRequirement);
        /* End: Add headers */
        return openAPI;
    }
    @Bean

    @Primary

    public SwaggerUiConfigProperties swaggerUiConfigProperties(SwaggerUiConfigProperties config) {
        config.setEnabled(true);
        config.setDocExpansion("none");
        config.setOperationsSorter("alpha");
        config.setTagsSorter("alpha");
        return config;
    }
}
