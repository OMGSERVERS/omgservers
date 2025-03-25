package com.omgservers.service.shard.pool.service.testInterface;

import com.omgservers.schema.module.pool.pool.*;
import com.omgservers.schema.module.pool.poolContainer.*;
import com.omgservers.schema.module.pool.poolRequest.*;
import com.omgservers.schema.module.pool.poolServer.*;
import com.omgservers.service.shard.pool.impl.service.poolService.PoolService;
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

    public ViewPoolServersResponse execute(final ViewPoolServersRequest request) {
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
