package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.dto.versionModule.DeleteVersionInternalRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeInternalRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigInternalRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionInternalRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionInternalRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
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
    @Path("/sync-version")
    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request);

    default SyncVersionInternalResponse syncVersion(long timeout, SyncVersionInternalRequest request) {
        return syncVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionInternalRequest request);

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
