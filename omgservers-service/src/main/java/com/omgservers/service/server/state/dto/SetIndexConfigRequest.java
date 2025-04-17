package com.omgservers.service.server.state.dto;

import com.omgservers.schema.model.index.IndexConfigDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetIndexConfigRequest {

    @NotNull
    IndexConfigDto indexConfig;
}
