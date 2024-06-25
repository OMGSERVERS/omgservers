package com.omgservers.model.dto.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectSupportResponse {

    Long projectId;
    Long stageId;
    String stageSecret;
}
