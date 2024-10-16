package com.omgservers.service.module.dispatcher.impl.service.routerService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferServerTextMessageResponse {

    @NotNull
    Boolean transferred;
}
