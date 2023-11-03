package com.omgservers.model.dto.system;

import com.omgservers.model.container.ContainerQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindContainerRequest {

    @NotNull
    Long entityId;

    @NotNull
    ContainerQualifierEnum qualifier;

    @NotNull
    Boolean deleted;
}
