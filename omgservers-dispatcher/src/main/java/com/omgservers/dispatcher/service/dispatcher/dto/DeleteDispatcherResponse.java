package com.omgservers.dispatcher.service.dispatcher.dto;

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
