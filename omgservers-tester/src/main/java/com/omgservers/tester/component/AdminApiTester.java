package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.admin.CreateDeveloperAdminRequest;
import com.omgservers.model.dto.admin.CreateDeveloperAdminResponse;
import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminResponse;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminRequest;
import com.omgservers.model.dto.admin.CreateServiceAccountAdminResponse;
import com.omgservers.model.dto.admin.CreateTenantAdminRequest;
import com.omgservers.model.dto.admin.CreateTenantAdminResponse;
import com.omgservers.model.dto.admin.DeleteTenantAdminRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.index.IndexModel;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AdminApiTester {

    final GetConfigOperation getConfigOperation;

    final ObjectMapper objectMapper;

    public IndexModel createIndex(final List<URI> addresses) throws IOException {
        final var responseSpecification = RestAssured
                .with()
                .baseUri(getConfigOperation.getConfig().gatewayUri().toString())
                .contentType(ContentType.JSON)
                .auth()
                .basic(getConfigOperation.getConfig().adminUsername(), getConfigOperation.getConfig().adminPassword())
                .body(objectMapper.writeValueAsString(new CreateIndexAdminRequest(addresses)))
                .log().all(false)
                .when()
                .put("/omgservers/admin-api/v1/request/create-index");
        responseSpecification.then().log().all(false);

        if (responseSpecification.getStatusCode() == 200) {
            final var response = responseSpecification.getBody().as(CreateIndexAdminResponse.class);
            return response.getIndex();
        } else {
            throw new RuntimeException("Create index failed");
        }
    }

    public ServiceAccountModel createServiceAccount(final String username,
                                                    final String password)
            throws IOException {
        final var responseSpecification = RestAssured
                .with()
                .baseUri(getConfigOperation.getConfig().gatewayUri().toString())
                .contentType(ContentType.JSON)
                .auth()
                .basic(getConfigOperation.getConfig().adminUsername(), getConfigOperation.getConfig().adminPassword())
                .body(objectMapper.writeValueAsString(new CreateServiceAccountAdminRequest(username, password)))
                .log().all(false)
                .when()
                .put("/omgservers/admin-api/v1/request/create-service-account");
        responseSpecification.then().log().all(false);

        if (responseSpecification.getStatusCode() == 200) {
            final var response = responseSpecification.getBody().as(CreateServiceAccountAdminResponse.class);
            return response.getServiceAccount();
        } else {
            throw new RuntimeException("Create service account failed");
        }
    }

    public Long createTenant() throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .baseUri(getConfigOperation.getConfig().gatewayUri().toString())
                .contentType(ContentType.JSON)
                .auth()
                .basic(getConfigOperation.getConfig().adminUsername(), getConfigOperation.getConfig().adminPassword())
                .body(objectMapper.writeValueAsString(new CreateTenantAdminRequest()))
                .log().all(false)
                .when().put("/omgservers/admin-api/v1/request/create-tenant");
        responseSpecification.then().log().all(false).statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTenantAdminResponse.class);
        return response.getId();
    }

    public Boolean deleteTenant(final Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .baseUri(getConfigOperation.getConfig().gatewayUri().toString())
                .contentType(ContentType.JSON)
                .auth()
                .basic(getConfigOperation.getConfig().adminUsername(), getConfigOperation.getConfig().adminPassword())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new DeleteTenantAdminRequest(tenantId)))
                .when().put("/omgservers/admin-api/v1/request/delete-tenant");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(DeleteVersionDeveloperResponse.class);
        return response.getDeleted();
    }

    public CreateDeveloperAdminResponse createDeveloper(final Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .baseUri(getConfigOperation.getConfig().gatewayUri().toString())
                .contentType(ContentType.JSON)
                .auth()
                .basic(getConfigOperation.getConfig().adminUsername(), getConfigOperation.getConfig().adminPassword())
                .body(objectMapper.writeValueAsString(new CreateDeveloperAdminRequest(tenantId)))
                .when().put("/omgservers/admin-api/v1/request/create-developer");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateDeveloperAdminResponse.class);
        return response;
    }
}
