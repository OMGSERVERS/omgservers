package com.omgservers.service.module.pool.impl.service.poolService;

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
import jakarta.validation.Valid;

public interface PoolService {

    Uni<GetPoolResponse> getPool(@Valid GetPoolRequest request);

    Uni<SyncPoolResponse> syncPool(@Valid SyncPoolRequest request);

    Uni<DeletePoolResponse> deletePool(@Valid DeletePoolRequest request);

    Uni<GetPoolServerRefResponse> getPoolServerRef(@Valid GetPoolServerRefRequest request);

    Uni<FindPoolServerRefResponse> findPoolServerRef(@Valid FindPoolServerRefRequest request);

    Uni<SyncPoolServerRefResponse> syncPoolServerRef(@Valid SyncPoolServerRefRequest request);

    Uni<DeletePoolServerRefResponse> deletePoolServerRef(@Valid DeletePoolServerRefRequest request);
}
