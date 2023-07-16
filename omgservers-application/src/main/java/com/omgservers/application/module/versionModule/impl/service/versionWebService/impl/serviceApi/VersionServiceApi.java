package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.*;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetVersionInternalResponse;
import io.smallrye.mutiny.Uni;

import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/version-api/v1/request")
public interface VersionServiceApi {

    @PUT
    @Path("/get-version")
    Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request);

    default GetVersionInternalResponse getVersion(long timeout, GetVersionInternalRequest request) {
        return getVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/create-version")
    Uni<Void> createVersion(CreateVersionInternalRequest request);

    default void createVersion(long timeout, CreateVersionInternalRequest request) {
        createVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-version")
    Uni<Void> syncVersion(SyncVersionInternalRequest request);

    default void syncVersion(long timeout, SyncVersionInternalRequest request) {
        syncVersion(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-version")
    Uni<Void> deleteVersion(DeleteVersionInternalRequest request);

    default void deleteVersion(long timeout, DeleteVersionInternalRequest request) {
        deleteVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-bytecode")
    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request);

    default GetBytecodeInternalResponse getBytecode(long timeout, GetBytecodeInternalRequest request) {
        return getBytecode(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage-config")
    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request);

    default GetStageConfigInternalResponse getStageConfig(long timeout, GetStageConfigInternalRequest request) {
        return getStageConfig(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
