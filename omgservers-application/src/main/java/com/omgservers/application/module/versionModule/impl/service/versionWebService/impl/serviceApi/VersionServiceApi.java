package com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi;

import com.omgservers.dto.versionModule.DeleteVersionRoutedRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeRoutedRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigRoutedRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionRoutedRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionRoutedRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/version-api/v1/request")
public interface VersionServiceApi {

    @PUT
    @Path("/get-version")
    Uni<GetVersionInternalResponse> getVersion(GetVersionRoutedRequest request);

    default GetVersionInternalResponse getVersion(long timeout, GetVersionRoutedRequest request) {
        return getVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-version")
    Uni<SyncVersionInternalResponse> syncVersion(SyncVersionRoutedRequest request);

    default SyncVersionInternalResponse syncVersion(long timeout, SyncVersionRoutedRequest request) {
        return syncVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionRoutedRequest request);

    default void deleteVersion(long timeout, DeleteVersionRoutedRequest request) {
        deleteVersion(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-bytecode")
    Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeRoutedRequest request);

    default GetBytecodeInternalResponse getBytecode(long timeout, GetBytecodeRoutedRequest request) {
        return getBytecode(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage-config")
    Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigRoutedRequest request);

    default GetStageConfigInternalResponse getStageConfig(long timeout, GetStageConfigRoutedRequest request) {
        return getStageConfig(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
