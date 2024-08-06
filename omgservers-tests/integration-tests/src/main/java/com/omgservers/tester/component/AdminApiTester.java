package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
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
                .baseUri(getConfigOperation.getConfig().internalUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTokenAdminRequest(userId, password)))
                .when().put("/omgservers/v1/entrypoint/admin/request/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTokenAdminResponse.class);
        return response.getRawToken();
    }
}
