package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.BuildVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.dto.StageDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.TenantDashboardDto;
import com.omgservers.schema.entrypoint.developer.dto.VersionDashboardDto;
import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.tester.operation.createVersionArchive.CreateVersionArchiveOperation;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import com.omgservers.tester.operation.getLuaFile.GetLuaFileOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeveloperApiTester {

    final CreateVersionArchiveOperation createVersionArchiveOperation;
    final GetLuaFileOperation getLuaFileOperation;
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

    public TenantDashboardDto getTenantDashboard(final String token,
                                                 final Long tenantId)
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

    public StageDashboardDto getStageDashboard(final String token,
                                               final Long tenantId,
                                               final Long stageId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new GetStageDashboardDeveloperRequest(tenantId, stageId)))
                .when().put("/omgservers/v1/entrypoint/developer/request/get-stage-dashboard");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(GetStageDashboardDeveloperResponse.class);
        return response.getStageDashboard();
    }

    public CreateVersionDeveloperResponse createVersion(final String token,
                                                        final Long tenantId,
                                                        final Long stageId,
                                                        final VersionConfigDto versionConfig) throws IOException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(objectMapper.writeValueAsString(
                        new CreateVersionDeveloperRequest(tenantId, stageId, versionConfig)))
                .when().put("/omgservers/v1/entrypoint/developer/request/create-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateVersionDeveloperResponse.class);
        return response;
    }

    public VersionDashboardDto getVersionDashboard(final String token,
                                                   final Long tenantId,
                                                   final Long versionId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new GetVersionDashboardDeveloperRequest(tenantId, versionId)))
                .when().put("/omgservers/v1/entrypoint/developer/request/get-version-dashboard");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(GetVersionDashboardDeveloperResponse.class);
        return response.getVersionDashboard();
    }

    public BuildVersionDeveloperResponse buildVersion(final String token,
                                                      final Long tenantId,
                                                      final Long stageId,
                                                      final VersionConfigDto versionConfig,
                                                      final String mainLua) throws IOException {

        final var archiveBytes = createVersionArchiveOperation.createArchive(Map.of("main.lua", mainLua));

        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.MULTIPART)
                .formParam("tenantId", tenantId)
                .formParam("stageId", stageId)
                .multiPart("config.json", "config.json",
                        objectMapper.writeValueAsString(versionConfig).getBytes(StandardCharsets.UTF_8),
                        "application/octet-stream")
                .multiPart("version.zip", "version.zip",
                        archiveBytes, "application/octet-stream")
                .when().put("/omgservers/v1/entrypoint/developer/request/build-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(BuildVersionDeveloperResponse.class);
        return response;
    }

    public DeployVersionDeveloperResponse deployVersion(final String token, final Long tenantId, final Long versionId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new DeployVersionDeveloperRequest(tenantId, versionId)))
                .when().put("/omgservers/v1/entrypoint/developer/request/deploy-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(DeployVersionDeveloperResponse.class);
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
