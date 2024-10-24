package com.omgservers.service.module.pool.impl.service.poolService.testInterface;

import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.FindPoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.SyncPoolContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersResponse;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.FindPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.GetPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServerResponse;
import com.omgservers.schema.module.pool.poolServer.ViewPoolServersRequest;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final PoolService poolService;

    public GetPoolResponse execute(final GetPoolRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolResponse execute(final SyncPoolRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolResponse execute(final DeletePoolRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPoolServerResponse execute(final GetPoolServerRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewPoolServerResponse execute(final ViewPoolServersRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolServerResponse execute(final SyncPoolServerRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolServerResponse execute(final DeletePoolServerRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPoolRequestResponse execute(final GetPoolRequestRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindPoolRequestResponse execute(final FindPoolRequestRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewPoolRequestsResponse execute(final ViewPoolRequestsRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolRequestResponse execute(final SyncPoolRequestRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolRequestResponse execute(final DeletePoolRequestRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPoolContainerResponse execute(final GetPoolContainerRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindPoolContainerResponse execute(final FindPoolContainerRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewPoolContainersResponse execute(final ViewPoolContainersRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolContainerResponse execute(final SyncPoolContainerRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolContainerResponse execute(final DeletePoolContainerRequest request) {
        return poolService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
