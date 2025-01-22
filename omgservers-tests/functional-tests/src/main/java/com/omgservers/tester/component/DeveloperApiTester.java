package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.dto.tenant.TenantDetailsDto;
import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.TenantDeploymentDetailsDto;
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

    public CreateTenantProjectDeveloperResponse createTenantProject(final String token,
                                                                    final Long tenantId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTenantProjectDeveloperRequest(tenantId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/create-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateTenantProjectDeveloperResponse.class);
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
                        new GetTenantProjectDetailsDeveloperRequest(tenantId.toString(), tenantProjectId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/get-project-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetTenantProjectDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    public DeleteTenantProjectDeveloperResponse deleteTenantProject(final String token,
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
                        new DeleteTenantProjectDeveloperRequest(tenantId.toString(), tenantProjectId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/delete-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeleteTenantProjectDeveloperResponse.class);
        return response;
    }

    /*
    Tenant stage
     */

    public CreateTenantStageDeveloperResponse createTenantStage(final String token,
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
                        new CreateTenantStageDeveloperRequest(tenantId.toString(), tenantProjectId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/create-stage");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateTenantStageDeveloperResponse.class);
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
                        new GetTenantStageDetailsDeveloperRequest(tenantId.toString(),
                                tenantProjectId.toString(),
                                tenantStageId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/get-stage-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetTenantStageDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    public DeleteTenantStageDeveloperResponse deleteTenantStage(final String token,
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
                        new DeleteTenantStageDeveloperRequest(tenantId.toString(),
                                tenantProjectId.toString(),
                                tenantStageId.toString())))
                .when().post("/service/v1/entrypoint/developer/request/delete-project");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeleteTenantStageDeveloperResponse.class);
        return response;
    }

    /*
    Tenant version
     */

    public CreateTenantVersionDeveloperResponse createTenantVersion(final String token,
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
                .body(objectMapper.writeValueAsString(new CreateTenantVersionDeveloperRequest(tenantId.toString(),
                        tenantProjectId.toString(),
                        tenantVersionConfig)))
                .when().post("/service/v1/entrypoint/developer/request/create-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(CreateTenantVersionDeveloperResponse.class);
        return response;
    }

    public UploadFilesArchiveDeveloperResponse uploadFilesArchive(final String token,
                                                                  final Long tenantId,
                                                                  final Long tenantVersionId,
                                                                  final String mainLua) throws IOException {

        final var archiveBytes = createVersionArchiveOperation.createArchive(Map.of("main.lua", mainLua));

        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.MULTIPART)
                .formParam("tenant", tenantId)
                .formParam("tenantVersionId", tenantVersionId)
                .multiPart("version.zip", "version.zip",
                        archiveBytes, "application/octet-stream")
                .when().post("/service/v1/entrypoint/developer/request/upload-files-archive");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(UploadFilesArchiveDeveloperResponse.class);
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
                        new GetTenantVersionDetailsDeveloperRequest(tenantId.toString(), tenantVersionId)))
                .when().post("/service/v1/entrypoint/developer/request/get-version-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetTenantVersionDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    public DeleteTenantVersionDeveloperResponse deleteTenantVersion(final String token,
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
                        new DeleteTenantVersionDeveloperRequest(tenantId.toString(), tenantVersionId)))
                .when().post("/service/v1/entrypoint/developer/request/delete-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeleteTenantVersionDeveloperResponse.class);
        return response;
    }

    /*
    Tenant deployment
     */

    public DeployTenantVersionDeveloperResponse deployTenantVersion(final String token,
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
                        new DeployTenantVersionDeveloperRequest(tenantId.toString(),
                                tenantProjectId.toString(),
                                tenantStageId.toString(),
                                tenantVersionId)))
                .when().post("/service/v1/entrypoint/developer/request/deploy-version");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeployTenantVersionDeveloperResponse.class);
        return response;
    }

    public TenantDeploymentDetailsDto getTenantDeploymentDetails(final String token,
                                                                 final Long tenantId,
                                                                 final Long tenantDeploymentId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new GetTenantDeploymentDetailsDeveloperRequest(tenantId.toString(), tenantDeploymentId)))
                .when().post("/service/v1/entrypoint/developer/request/get-deployment-details");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(GetTenantDeploymentDetailsDeveloperResponse.class);
        return response.getDetails();
    }

    public DeleteTenantDeploymentDeveloperResponse deleteTenantDeployment(final String token,
                                                                          final Long tenantId,
                                                                          final Long tenantDeploymentId)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Developer"))
                .baseUri(getConfigOperation.getConfig().externalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(
                        new DeleteTenantDeploymentDeveloperRequest(tenantId.toString(), tenantDeploymentId)))
                .when().post("/service/v1/entrypoint/developer/request/delete-deployment");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody()
                .as(DeleteTenantDeploymentDeveloperResponse.class);
        return response;
    }
}
