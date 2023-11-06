package com.omgservers.model.dto.system;

import com.omgservers.model.job.JobModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetJobResponse {

    JobModel job;
}
