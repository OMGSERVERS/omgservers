package com.omgservers.service.module.pool.impl.service.webService.impl.api;

import com.omgservers.model.dto.pool.DeletePoolRequest;
import com.omgservers.model.dto.pool.DeletePoolResponse;
import com.omgservers.model.dto.pool.DeletePoolServerRefRequest;
import com.omgservers.model.dto.pool.DeletePoolServerRefResponse;
import com.omgservers.model.dto.pool.FindPoolServerRefRequest;
import com.omgservers.model.dto.pool.FindPoolServerRefResponse;
import com.omgservers.model.dto.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.GetPoolResponse;
import com.omgservers.model.dto.pool.GetPoolServerRefRequest;
import com.omgservers.model.dto.pool.GetPoolServerRefResponse;
import com.omgservers.model.dto.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.SyncPoolResponse;
import com.omgservers.model.dto.pool.SyncPoolServerRefRequest;
import com.omgservers.model.dto.pool.SyncPoolServerRefResponse;
import com.omgservers.model.dto.pool.ViewPoolServerRefsRequest;
import com.omgservers.model.dto.pool.ViewPoolServerRefsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Pool Module API")
@Path("/omgservers/v1/module/pool/request")
public interface PoolApi {

    @PUT
    @Path("/get-pool")
    Uni<GetPoolResponse> getPool(GetPoolRequest request);

    @PUT
    @Path("/sync-pool")
    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);

    @PUT
    @Path("/delete-pool")
    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);

    @PUT
    @Path("/get-pool-server-ref")
    Uni<GetPoolServerRefResponse> getPoolServerRef(GetPoolServerRefRequest request);

    @PUT
    @Path("/find-pool-server-ref")
    Uni<FindPoolServerRefResponse> findPoolServerRef(FindPoolServerRefRequest request);

    @PUT
    @Path("/view-pool-server-ref")
    Uni<ViewPoolServerRefsResponse> viewPoolServerRefs(ViewPoolServerRefsRequest request);

    @PUT
    @Path("/sync-pool-server-ref")
    Uni<SyncPoolServerRefResponse> syncPoolServerRef(SyncPoolServerRefRequest request);

    @PUT
    @Path("/delete-pool-server-ref")
    Uni<DeletePoolServerRefResponse> deletePoolServerRef(DeletePoolServerRefRequest request);
}
