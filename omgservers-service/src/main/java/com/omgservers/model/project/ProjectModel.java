package com.omgservers.model.project;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    @ToString.Exclude
    ProjectConfigModel config;
}
