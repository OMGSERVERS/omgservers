package com.omgservers.model.dockerRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DockerRepositoryModel {

    String namespace;
    Long tenantId;
    Long projectId;
    Long stageId;
    DockerContainerQualifierEnum qualifier;

    @Override
    public String toString() {
        return String.format("%s/%d/%d/%d/%s", namespace, tenantId, projectId, stageId, qualifier.getQualifier());
    }
}