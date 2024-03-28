package com.omgservers.service.module.pool.impl.service.webService;

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
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetPoolResponse> getPool(GetPoolRequest request);

    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);

    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);

    Uni<GetPoolServerRefResponse> getPoolServerRef(GetPoolServerRefRequest request);

    Uni<FindPoolServerRefResponse> findPoolServerRef(FindPoolServerRefRequest request);

    Uni<SyncPoolServerRefResponse> syncPoolServerRef(SyncPoolServerRefRequest request);

    Uni<DeletePoolServerRefResponse> deletePoolServerRef(DeletePoolServerRefRequest request);
}
