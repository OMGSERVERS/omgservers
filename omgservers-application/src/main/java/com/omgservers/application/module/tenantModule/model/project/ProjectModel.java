package com.omgservers.application.module.tenantModule.model.project;

import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {

    static public void validate(ProjectModel project) {
        if (project == null) {
            throw new ServerSideBadRequestException("project is null");
        }
    }

    Long id;
    Long tenantId;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    Long ownerId;
    @ToString.Exclude
    ProjectConfigModel config;
}
