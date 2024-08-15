package com.omgservers.service.service.jenkins.dto.getJobByBuildNumber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetJobByBuildNumberResponse {

    ResultEnum result;

    @JsonProperty("inProgress")
    Boolean inProgress;
}
