package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.model.dto.developer.UploadVersionDeveloperResponse;
import com.omgservers.model.tenantDashboard.TenantDashboardModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeveloperApiTester {

    final GetConfigOperation getConfigOperation;

    final ObjectMapper objectMapper;

    public String createDeveloperToken(final Long userId,
                                       final String password) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTokenDeveloperRequest(userId, password)))
                .when().put("/omgservers/v1/entrypoint/developer/request/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTokenDeveloperResponse.class);
        return response.getRawToken();
    }

    public TenantDashboardModel getTenantDashboard(String token, Long tenantId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new GetTenantDashboardDeveloperRequest(tenantId)))
                .when().put("/omgservers/v1/entrypoint/developer/request/get-tenant-dashboard");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(GetTenantDashboardDeveloperResponse.class);
        return response.getTenantDashboard();
    }

    public CreateProjectDeveloperResponse createProject(String token, Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateProjectDeveloperRequest(tenantId)))
                .when().put("/omgservers/v1/entrypoint/developer/request/create-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateProjectDeveloperResponse.class);
        return response;
    }

    public CreateVersionDeveloperResponse createVersion(final String token,
                                                        final Long tenantId,
                                                        final Long stageId,
                                                        final VersionConfigModel versionConfig,
                                                        final VersionSourceCodeModel sourceCode)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new CreateVersionDeveloperRequest(tenantId, stageId, versionConfig, sourceCode)))
                .when().put("/omgservers/v1/entrypoint/developer/request/create-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateVersionDeveloperResponse.class);
        return response;
    }

    public UploadVersionDeveloperResponse uploadVersion(final String token,
                                                        final Long tenantId,
                                                        final Long stageId,
                                                        final VersionConfigModel versionConfig,
                                                        final String lobbyScript,
                                                        final String matchScript)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.MULTIPART)
                .formParam("tenantId", tenantId)
                .formParam("stageId", stageId)
                .multiPart("config.json", "config.json",
                        objectMapper.writeValueAsString(versionConfig).getBytes(),
                        "application/octet-stream")
                .multiPart("lobby.lua", "lobby.lua", lobbyScript.getBytes(),
                        "application/octet-stream")
                .multiPart("match.lua", "match.lua", matchScript.getBytes(),
                        "application/octet-stream")
                .when().put("/omgservers/v1/entrypoint/developer/request/upload-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(UploadVersionDeveloperResponse.class);
        return response;
    }

    public DeleteVersionDeveloperResponse deleteVersion(String token, Long tenantId, Long id)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new DeleteVersionDeveloperRequest(tenantId, id)))
                .when().put("/omgservers/v1/entrypoint/developer/request/delete-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(DeleteVersionDeveloperResponse.class);
        return response;
    }
}
