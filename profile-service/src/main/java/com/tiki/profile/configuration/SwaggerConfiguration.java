package com.tiki.profile.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Api Service API", description = "Product API documentation",
        version = "1.0"),
        servers = {@Server(url = "${app.servlet.context-path}", description = "Default Server URL")})
public class SwaggerConfiguration {
}
