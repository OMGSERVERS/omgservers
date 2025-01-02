package com.omgservers.dispatcher.service.router.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferClientBinaryMessageResponse {

    @NotNull
    Boolean transferred;
}
