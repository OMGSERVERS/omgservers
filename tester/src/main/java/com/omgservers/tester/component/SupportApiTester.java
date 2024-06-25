package com.omgservers.tester.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.CreateTokenSupportRequest;
import com.omgservers.model.dto.support.CreateTokenSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import com.omgservers.tester.operation.getConfig.GetConfigOperation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SupportApiTester {

    final GetConfigOperation getConfigOperation;

    final ObjectMapper objectMapper;

    public String createSupportToken() throws JsonProcessingException {
        final var userId = getConfigOperation.getConfig().support().userId();
        final var password = getConfigOperation.getConfig().support().password();

        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Support"))
                .baseUri(getConfigOperation.getConfig().internalUri().toString())
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTokenSupportRequest(userId, password)))
                .when().put("/omgservers/v1/entrypoint/support/request/create-token");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTokenSupportResponse.class);
        return response.getRawToken();
    }

    public Long createTenant(final String token) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Support"))
                .baseUri(getConfigOperation.getConfig().internalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTenantSupportRequest()))
                .when().put("/omgservers/v1/entrypoint/support/request/create-tenant");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTenantSupportResponse.class);
        return response.getId();
    }

    public Boolean deleteTenant(final String token,
                                final Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Support"))
                .baseUri(getConfigOperation.getConfig().internalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new DeleteTenantSupportRequest(tenantId)))
                .when().put("/omgservers/v1/entrypoint/support/request/delete-tenant");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(DeleteVersionDeveloperResponse.class);
        return response.getDeleted();
    }

    public CreateDeveloperSupportResponse createDeveloper(final String token,
                                                          final Long tenantId) throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Support"))
                .baseUri(getConfigOperation.getConfig().internalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateDeveloperSupportRequest()))
                .when().put("/omgservers/v1/entrypoint/support/request/create-developer");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateDeveloperSupportResponse.class);
        return response;
    }

    public CreateTenantPermissionsSupportResponse createTenantPermissions(final String token,
                                                                          final Long tenantId,
                                                                          final Long userId,
                                                                          Set<TenantPermissionEnum> permissions)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Support"))
                .baseUri(getConfigOperation.getConfig().internalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new CreateTenantPermissionsSupportRequest(tenantId,
                        userId,
                        permissions)))
                .when().put("/omgservers/v1/entrypoint/support/request/create-tenant-permissions");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(CreateTenantPermissionsSupportResponse.class);
        return response;
    }

    public DeleteTenantPermissionsSupportResponse deleteTenantPermissions(final String token,
                                                                          final Long tenantId,
                                                                          final Long userId,
                                                                          Set<TenantPermissionEnum> permissions)
            throws JsonProcessingException {
        final var responseSpecification = RestAssured
                .with()
                .filter(new LoggingFilter("Support"))
                .baseUri(getConfigOperation.getConfig().internalUri().toString())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(new DeleteTenantPermissionsSupportRequest(tenantId,
                        userId,
                        permissions)))
                .when().put("/omgservers/v1/entrypoint/support/request/delete-tenant-permissions");
        responseSpecification.then().statusCode(200);

        final var response = responseSpecification.getBody().as(DeleteTenantPermissionsSupportResponse.class);
        return response;
    }
}
