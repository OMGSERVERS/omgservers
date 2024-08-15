package com.omgservers.service.service.jenkins.dto.getQueueItemResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetQueueItemResponse {

    Boolean cancelled;
    ExecutableDto executable;
}
