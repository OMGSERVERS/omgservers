package com.omgservers.service.service.job.dto;

import com.omgservers.schema.model.job.JobModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetJobResponse {

    JobModel job;
}
