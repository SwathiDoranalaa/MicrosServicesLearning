package com.ricomincia.account;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title="Accounts microservice REST API Documentation",
                description="Banks Accounts microservice REST API Documentation",
                version="v1",
                contact = @Contact(
                        name="Swathi",
                        email="abc@gmail.com",
                        url="https://example.com"
                ),
                license=@License(
                        name = "Apache 2.0",
                        url = "https://www.example.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Accounts microservice REST API Documentation",
                url="http://localhost:8081/accounts.html"
        )
)
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
