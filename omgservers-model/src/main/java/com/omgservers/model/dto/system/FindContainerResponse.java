package com.omgservers.model.dto.system;

import com.omgservers.model.container.ContainerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindContainerResponse {

    @NotNull
    ContainerModel container;
}
