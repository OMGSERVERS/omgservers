package com.omgservers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.dto.developer.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developer.GetVersionStatusDeveloperResponse;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.model.version.VersionStatusEnum;
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
        log.info("Token was created, response={}", response);
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
        log.info("Project was created, response={}", response);
        return response;
    }

    public CreateVersionDeveloperResponse createVersion(String token, Long tenantId, Long stageId, VersionStageConfigModel stageConfig, VersionSourceCodeModel sourceCode) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateVersionDeveloperRequest(tenantId, stageId, stageConfig, sourceCode)))
                .when().put("/omgservers/developer-api/v1/requests/create-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateVersionDeveloperResponse.class);
        log.info("Version was created, response={}", response);
        return response;
    }

    public VersionStatusEnum getVersionStatus(String token, Long versionId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new GetVersionStatusDeveloperRequest(versionId)))
                .when().put("/omgservers/developer-api/v1/requests/get-version-status");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(GetVersionStatusDeveloperResponse.class);
        log.info("Version status was found, response={}", response);
        return response.getStatus();
    }

//
//    public Long createVersion(Long tenantId, Long stageId, VersionStageConfigModel stageConfig, VersionSourceCodeModel sourceCode) {
//        final var createVersionDeveloperRequest = new CreateVersionDeveloperRequest(tenantId, stageId, stageConfig, sourceCode);
//        final var createVersionDeveloperResponse = clientForAuthenticatedAccess.createVersion(TIMEOUT, rawToken, createVersionDeveloperRequest);
//        return createVersionDeveloperResponse.getId();
//    }
//
//    public VersionStatusEnum getVersionStatus(Long versionId) {
//        final var response = clientForAuthenticatedAccess
//                .getVersionStatus(TIMEOUT, rawToken, new GetVersionStatusDeveloperRequest(versionId));
//        return response.getStatus();
//    }
}
