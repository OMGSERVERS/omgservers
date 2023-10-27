package com.omgservers.dto.internal;

import com.omgservers.model.container.ContainerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContainerResponse {

    @NotNull
    ContainerModel container;
}
