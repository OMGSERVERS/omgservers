package com.omgservers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeveloperCli {

    final ObjectMapper objectMapper;

    public String createDeveloperToken(Long userId, String password) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTokenDeveloperRequest(userId, password)))
                .when().put("/omgservers/developer-api/v1/requests/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTokenDeveloperResponse.class);
        return response.getRawToken();
    }

    public CreateProjectDeveloperResponse createProject(String token, Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateProjectDeveloperRequest(tenantId)))
                .when().put("/omgservers/developer-api/v1/requests/create-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateProjectDeveloperResponse.class);
        return response;
    }

    public CreateVersionDeveloperResponse createVersion(String token, Long tenantId, Long stageId, VersionConfigModel versionConfig, VersionSourceCodeModel sourceCode) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateVersionDeveloperRequest(tenantId, stageId, versionConfig, sourceCode)))
                .when().put("/omgservers/developer-api/v1/requests/create-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateVersionDeveloperResponse.class);
        return response;
    }
}
