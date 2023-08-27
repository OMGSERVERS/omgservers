package com.omgservers.module.version.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.dto.version.DeleteVersionShardedRequest;
import com.omgservers.dto.version.DeleteVersionShardedResponse;
import com.omgservers.dto.version.GetBytecodeShardedRequest;
import com.omgservers.dto.version.GetBytecodeShardedResponse;
import com.omgservers.dto.version.GetStageConfigShardedRequest;
import com.omgservers.dto.version.GetStageConfigShardedResponse;
import com.omgservers.dto.version.GetVersionShardedRequest;
import com.omgservers.dto.version.GetVersionShardedResponse;
import com.omgservers.dto.version.SyncVersionShardedRequest;
import com.omgservers.dto.version.SyncVersionShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/version-api/v1/request")
public interface VersionServiceApi {

    @PUT
    @Path("/get-version")
    Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request);

    default GetVersionShardedResponse getVersion(long timeout, GetVersionShardedRequest request) {
        return getVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-version")
    Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request);

    default SyncVersionShardedResponse syncVersion(long timeout, SyncVersionShardedRequest request) {
        return syncVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request);

    default void deleteVersion(long timeout, DeleteVersionShardedRequest request) {
        deleteVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-bytecode")
    Uni<GetBytecodeShardedResponse> getBytecode(GetBytecodeShardedRequest request);

    default GetBytecodeShardedResponse getBytecode(long timeout, GetBytecodeShardedRequest request) {
        return getBytecode(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage-config")
    Uni<GetStageConfigShardedResponse> getStageConfig(GetStageConfigShardedRequest request);

    default GetStageConfigShardedResponse getStageConfig(long timeout, GetStageConfigShardedRequest request) {
        return getStageConfig(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
