package com.omgservers.model.dto.system.job;

import com.omgservers.model.job.JobModel;
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
