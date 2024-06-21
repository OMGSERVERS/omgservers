package com.omgservers.model.dto.developer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDeveloperResponse {

    Long projectId;
    Long stageId;
    String secret;
}
