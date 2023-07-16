package com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl;

import com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.application.module.internalModule.impl.operation.getInternalsServiceApiClientOperation.InternalsServiceApiClient;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.JobInternalService;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.deleteJobMethod.DeleteJobMethod;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.impl.method.syncJobMethod.SyncJobMethod;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
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
