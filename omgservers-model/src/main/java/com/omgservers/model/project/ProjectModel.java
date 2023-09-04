package com.omgservers.model.project;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {

    public static void validate(ProjectModel project) {
        if (project == null) {
            throw new ServerSideBadRequestException("project is null");
        }
    }

    Long id;
    Long tenantId;
    Instant created;
    Instant modified;
    @ToString.Exclude
    ProjectConfigModel config;
}
