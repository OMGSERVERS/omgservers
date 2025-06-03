package com.omgservers.dispatcher.server.dispatcher.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDispatcherResponse {

    @NotNull
    Boolean deleted;
}
