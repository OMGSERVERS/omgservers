package com.omgservers.service.module.pool.impl.service.poolService.testInterface;

import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.schema.module.pool.pool.GetPoolRequest;
import com.omgservers.schema.module.pool.pool.GetPoolResponse;
import com.omgservers.schema.module.pool.pool.SyncPoolRequest;
import com.omgservers.schema.module.pool.pool.SyncPoolResponse;
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
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.FindPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.GetPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerRequest;
import com.omgservers.schema.module.pool.poolServerContainer.SyncPoolServerContainerResponse;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersRequest;
import com.omgservers.schema.module.pool.poolServerContainer.ViewPoolServerContainersResponse;
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

    public GetPoolResponse getPool(final GetPoolRequest request) {
        return poolService.getPool(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolResponse syncPool(final SyncPoolRequest request) {
        return poolService.syncPool(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolResponse deletePool(final DeletePoolRequest request) {
        return poolService.deletePool(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPoolServerResponse getPoolServer(final GetPoolServerRequest request) {
        return poolService.getPoolServer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewPoolServerResponse viewPoolServers(final ViewPoolServersRequest request) {
        return poolService.viewPoolServers(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolServerResponse syncPoolServer(final SyncPoolServerRequest request) {
        return poolService.syncPoolServer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolServerResponse deletePoolServer(final DeletePoolServerRequest request) {
        return poolService.deletePoolServer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPoolRequestResponse getPoolRequest(final GetPoolRequestRequest request) {
        return poolService.getPoolRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindPoolRequestResponse findPoolRequest(final FindPoolRequestRequest request) {
        return poolService.findPoolRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewPoolRequestsResponse viewPoolRequests(final ViewPoolRequestsRequest request) {
        return poolService.viewPoolRequests(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolRequestResponse syncPoolRequest(final SyncPoolRequestRequest request) {
        return poolService.syncPoolRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolRequestResponse deletePoolRequest(final DeletePoolRequestRequest request) {
        return poolService.deletePoolRequest(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetPoolServerContainerResponse getPoolServerContainer(final GetPoolServerContainerRequest request) {
        return poolService.getPoolServerContainer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindPoolServerContainerResponse findPoolServerContainer(final FindPoolServerContainerRequest request) {
        return poolService.findPoolServerContainer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewPoolServerContainersResponse viewPoolServerContainers(final ViewPoolServerContainersRequest request) {
        return poolService.viewPoolServerContainers(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncPoolServerContainerResponse syncPoolServerContainer(final SyncPoolServerContainerRequest request) {
        return poolService.syncPoolServerContainer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeletePoolServerContainerResponse deletePoolServerContainer(final DeletePoolServerContainerRequest request) {
        return poolService.deletePoolServerContainer(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
