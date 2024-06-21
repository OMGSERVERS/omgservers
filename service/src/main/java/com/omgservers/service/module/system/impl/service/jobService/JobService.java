package com.omgservers.service.module.system.impl.service.jobService;

import com.omgservers.model.dto.system.job.DeleteJobRequest;
import com.omgservers.model.dto.system.job.DeleteJobResponse;
import com.omgservers.model.dto.system.job.FindJobRequest;
import com.omgservers.model.dto.system.job.FindJobResponse;
import com.omgservers.model.dto.system.job.GetJobRequest;
import com.omgservers.model.dto.system.job.GetJobResponse;
import com.omgservers.model.dto.system.job.SyncJobRequest;
import com.omgservers.model.dto.system.job.SyncJobResponse;
import com.omgservers.model.dto.system.job.ViewJobsRequest;
import com.omgservers.model.dto.system.job.ViewJobsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface JobService {

    Uni<GetJobResponse> getJob(@Valid GetJobRequest request);

    Uni<FindJobResponse> findJob(@Valid FindJobRequest request);

    Uni<ViewJobsResponse> viewJobs(@Valid ViewJobsRequest request);

    Uni<SyncJobResponse> syncJob(@Valid SyncJobRequest request);

    Uni<SyncJobResponse> syncJobWithIdempotency(@Valid SyncJobRequest request);

    Uni<DeleteJobResponse> deleteJob(@Valid DeleteJobRequest request);
}
