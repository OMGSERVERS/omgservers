package com.omgservers.service.module.system.impl.service.webService.impl.api;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/system-api/v1/request")
public interface SystemApi {

    @PUT
    @Path("/get-index")
    Uni<GetIndexResponse> getIndex(GetIndexRequest request);

    @PUT
    @Path("/find-index")
    Uni<FindIndexResponse> findIndex(FindIndexRequest request);

    @PUT
    @Path("/sync-index")
    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);

    @PUT
    @Path("/delete-index")
    Uni<DeleteIndexResponse> deleteIndex(DeleteIndexRequest request);

    @PUT
    @Path("/get-service-account")
    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);

    @PUT
    @Path("/find-service-account")
    Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request);

    @PUT
    @Path("/sync-service-account")
    Uni<SyncServiceAccountResponse> syncServiceAccount(SyncServiceAccountRequest request);

    @PUT
    @Path("/delete-service-account")
    Uni<DeleteServiceAccountResponse> deleteServiceAccount(DeleteServiceAccountRequest request);
}
