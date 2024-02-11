package com.omgservers.model.dto.system;

import com.omgservers.model.handler.HandlerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetHandlerResponse {

    @NotNull
    HandlerModel handler;
}
