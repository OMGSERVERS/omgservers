package com.omgservers.base.impl.service.jobInternalService.impl;

import com.omgservers.base.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.base.impl.operation.getInternalsServiceApiClientOperation.InternalsServiceApiClient;
import com.omgservers.base.impl.service.jobInternalService.JobInternalService;
import com.omgservers.base.impl.service.jobInternalService.impl.method.deleteJobMethod.DeleteJobMethod;
import com.omgservers.base.impl.service.jobInternalService.impl.method.syncJobMethod.SyncJobMethod;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import com.omgservers.dto.internalModule.DeleteJobInternalRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalResponse;
import com.omgservers.dto.internalModule.SyncJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobInternalServiceImpl implements JobInternalService {

    final GetInternalsServiceApiClientOperation getInternalsServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    final DeleteJobMethod deleteJobMethod;
    final SyncJobMethod syncJobMethod;

    @Override
    public Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncJobInternalRequest::validate,
                getInternalsServiceApiClientOperation::getClient,
                InternalsServiceApiClient::syncJob,
                syncJobMethod::syncJob);
    }

    @Override
    public Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteJobInternalRequest::validate,
                getInternalsServiceApiClientOperation::getClient,
                InternalsServiceApiClient::deleteJob,
                deleteJobMethod::deleteJob);
    }
}
