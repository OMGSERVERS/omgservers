package com.omgservers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.dto.admin.CreateTenantAdminRequest;
import com.omgservers.dto.admin.CreateTenantAdminResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AdminCli {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    final ObjectMapper objectMapper;

    public Long createTenant() throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .contentType(ContentType.JSON)
                .auth().basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .body(objectMapper.writeValueAsString(new CreateTenantAdminRequest()))
                .when().put("/omgservers/admin-api/v1/request/create-tenant");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTenantAdminResponse.class);
        log.info("Tenant was created, response={}", response);
        return response.getId();
    }

    public CreateDeveloperAdminResponse createDeveloper(Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .contentType(ContentType.JSON)
                .auth().basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .body(objectMapper.writeValueAsString(new CreateDeveloperAdminRequest(tenantId)))
                .when().put("/omgservers/admin-api/v1/request/create-developer");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateDeveloperAdminResponse.class);
        log.info("Developer was created, response={}", response);
        return response;
    }
}
