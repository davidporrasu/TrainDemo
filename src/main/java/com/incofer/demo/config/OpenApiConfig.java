/*
 * © 2021 Wabtec Corporation. All Rights Reserved.
 */
package com.incofer.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig
{
    /**
     * customOpenAPI
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Your API Title")
                        .version("1.0.0"))
                .servers(List.of(new Server().url("/")))
                .components(new Components())
                ;
    }

    private List<SecurityScheme> securitySchemes() {
        return List.of(new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"));
    }

    @Bean
    public GroupedOpenApi customOpenAPIGroup() {
        String[] paths = {"/com.incofer.demo.rest/**"}; // Specify your base package here
        return GroupedOpenApi.builder()
                .group("your-api-group") // Provide a name for the API group
                .pathsToMatch(paths)
                .build();
    }
}
