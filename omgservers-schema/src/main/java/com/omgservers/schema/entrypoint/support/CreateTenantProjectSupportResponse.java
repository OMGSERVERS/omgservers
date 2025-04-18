package com.omgservers.schema.entrypoint.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantProjectSupportResponse {

    Long projectId;
    Long stageId;
}
