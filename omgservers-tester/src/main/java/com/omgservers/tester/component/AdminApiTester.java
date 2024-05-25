package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.admin.CreateSupportAdminRequest;
import com.omgservers.model.dto.admin.CreateSupportAdminResponse;
import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AdminApiTester {

    final GetConfigOperation getConfigOperation;

    final ObjectMapper objectMapper;

    public String createAdminToken() throws JsonProcessingException {
        final var userId = getConfigOperation.getConfig().admin().userId();
        final var password = getConfigOperation.getConfig().admin().password();

        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Admin"))
                .baseUri(getConfigOperation.getConfig().serviceUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTokenAdminRequest(userId, password)))
                .when().put("/omgservers/v1/entrypoint/admin/request/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTokenAdminResponse.class);
        return response.getRawToken();
    }

    public CreateSupportAdminResponse createSupport(final String token) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Admin"))
                .baseUri(getConfigOperation.getConfig().serviceUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateSupportAdminRequest()))
                .when().put("/omgservers/v1/entrypoint/admin/request/create-support");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateSupportAdminResponse.class);
        return response;
    }
}
