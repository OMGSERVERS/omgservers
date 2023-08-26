package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.dto.versionModule.DeleteVersionShardRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeShardRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigShardRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionShardRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionShardRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/version-api/v1/request")
public interface VersionServiceApi {

    @PUT
    @Path("/get-version")
    Uni<GetVersionInternalResponse> getVersion(GetVersionShardRequest request);

    default GetVersionInternalResponse getVersion(long timeout, GetVersionShardRequest request) {
        return getVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-version")
    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionShardRequest request);

    default SyncVersionInternalResponse syncVersion(long timeout, SyncVersionShardRequest request) {
        return syncVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionShardRequest request);

    default void deleteVersion(long timeout, DeleteVersionShardRequest request) {
        deleteVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-bytecode")
    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeShardRequest request);

    default GetBytecodeInternalResponse getBytecode(long timeout, GetBytecodeShardRequest request) {
        return getBytecode(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage-config")
    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigShardRequest request);

    default GetStageConfigInternalResponse getStageConfig(long timeout, GetStageConfigShardRequest request) {
        return getStageConfig(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
