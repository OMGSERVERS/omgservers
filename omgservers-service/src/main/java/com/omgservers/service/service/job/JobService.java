package com.omgservers.service.service.job;

import com.omgservers.service.service.job.dto.DeleteJobRequest;
import com.omgservers.service.service.job.dto.DeleteJobResponse;
import com.omgservers.service.service.job.dto.FindJobRequest;
import com.omgservers.service.service.job.dto.FindJobResponse;
import com.omgservers.service.service.job.dto.GetJobRequest;
import com.omgservers.service.service.job.dto.GetJobResponse;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
import com.omgservers.service.service.job.dto.ViewJobsRequest;
import com.omgservers.service.service.job.dto.ViewJobsResponse;
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
