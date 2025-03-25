package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.*;
import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantProject.TenantProjectDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantVersion.TenantVersionDetailsDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.tester.operation.createVersionArchive.CreateVersionArchiveOperation;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import com.omgservers.tester.operation.getLuaFile.GetLuaFileOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
                .when().post("/service/v1/entrypoint/developer/request/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateTokenDeveloperResponse.class);
        return response.getRawToken();
    }

    public TenantDetailsDto getTenantDetails(final String token,
                                             final Long tenantId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new GetTenantDetailsDeveloperRequest(tenantId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/get-tenant-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetTenantDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    /*
    Tenant project
     */

    public CreateProjectDeveloperResponse createTenantProject(final String token,
                                                              final Long tenantId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateProjectDeveloperRequest(tenantId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/create-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateProjectDeveloperResponse.class);
        return response;
    }

    public TenantProjectDetailsDto getTenantProjectDetails(final String token,
                                                           final Long tenantId,
                                                           final Long tenantProjectId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new GetProjectDetailsDeveloperRequest(tenantId.toString(), tenantProjectId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/get-project-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetProjectDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    public DeleteProjectDeveloperResponse deleteTenantProject(final String token,
                                                              final Long tenantId,
                                                              final Long tenantProjectId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new DeleteProjectDeveloperRequest(tenantId.toString(), tenantProjectId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/delete-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeleteProjectDeveloperResponse.class);
        return response;
    }

    /*
    Tenant stage
     */

    public CreateStageDeveloperResponse createTenantStage(final String token,
                                                          final Long tenantId,
                                                          final Long tenantProjectId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new CreateStageDeveloperRequest(tenantId.toString(), tenantProjectId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/create-stage");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateStageDeveloperResponse.class);
        return response;
    }

    public TenantStageDetailsDto getTenantStageDetails(final String token,
                                                       final Long tenantId,
                                                       final Long tenantProjectId,
                                                       final Long tenantStageId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new GetStageDetailsDeveloperRequest(tenantId.toString(),
                                tenantProjectId.toString(),
                                tenantStageId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/get-stage-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetStageDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    public DeleteStageDeveloperResponse deleteTenantStage(final String token,
                                                          final Long tenantId,
                                                          final Long tenantProjectId,
                                                          final Long tenantStageId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new DeleteStageDeveloperRequest(tenantId.toString(),
                                tenantProjectId.toString(),
                                tenantStageId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/delete-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeleteStageDeveloperResponse.class);
        return response;
    }

    /*
    Tenant version
     */

    public CreateVersionDeveloperResponse createTenantVersion(final String token,
                                                              final Long tenantId,
                                                              final Long tenantProjectId,
                                                              final TenantVersionConfigDto tenantVersionConfig)
            throws IOException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(objectMapper.writeValueAsString(new CreateVersionDeveloperRequest(tenantId.toString(),
                        tenantProjectId.toString(),
                        tenantVersionConfig)))
                .when().post("/service/v1/entrypoint/developer/request/create-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateVersionDeveloperResponse.class);
        return response;
    }

    public TenantVersionDetailsDto getTenantVersionDetails(final String token,
                                                           final Long tenantId,
                                                           final Long tenantVersionId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new GetVersionDetailsDeveloperRequest(tenantId.toString(), tenantVersionId)))
                .when().post("/service/v1/entrypoint/developer/request/get-version-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetVersionDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    public DeleteVersionDeveloperResponse deleteTenantVersion(final String token,
                                                              final Long tenantId,
                                                              final Long tenantVersionId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new DeleteVersionDeveloperRequest(tenantId.toString(), tenantVersionId)))
                .when().post("/service/v1/entrypoint/developer/request/delete-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeleteVersionDeveloperResponse.class);
        return response;
    }

    /*
    Tenant deployment
     */

    public CreateDeploymentDeveloperResponse deployTenantVersion(final String token,
                                                                 final Long tenantId,
                                                                 final Long tenantProjectId,
                                                                 final Long tenantStageId,
                                                                 final Long tenantVersionId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new CreateDeploymentDeveloperRequest(tenantId.toString(),
                                tenantProjectId.toString(),
                                tenantStageId.toString(),
                                tenantVersionId)))
                .when().post("/service/v1/entrypoint/developer/request/deploy-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateDeploymentDeveloperResponse.class);
        return response;
    }
}
