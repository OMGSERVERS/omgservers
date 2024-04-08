package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SupportApiTester {

    final GetConfigOperation getConfigOperation;

    final ObjectMapper objectMapper;

    public Long createTenant() throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Admin"))
                .baseUri(getConfigOperation.getConfig().adminUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTenantSupportRequest()))
                .when().put("/omgservers/v1/entrypoint/support/request/create-tenant");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTenantSupportResponse.class);
        return response.getId();
    }

    public Boolean deleteTenant(final Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Admin"))
                .baseUri(getConfigOperation.getConfig().adminUri().toString())
                .contentType(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new DeleteTenantSupportRequest(tenantId)))
                .when().put("/omgservers/v1/entrypoint/support/request/delete-tenant");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(DeleteVersionDeveloperResponse.class);
        return response.getDeleted();
    }

    public CreateDeveloperSupportResponse createDeveloper(final Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Admin"))
                .baseUri(getConfigOperation.getConfig().adminUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateDeveloperSupportRequest(tenantId)))
                .when().put("/omgservers/v1/entrypoint/support/request/create-developer");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateDeveloperSupportResponse.class);
        return response;
    }
}
