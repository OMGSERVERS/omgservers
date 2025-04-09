package com.omgservers.service.server.job.dto;

import com.omgservers.schema.model.job.JobModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewJobsResponse {

    List<JobModel> jobs;
}
