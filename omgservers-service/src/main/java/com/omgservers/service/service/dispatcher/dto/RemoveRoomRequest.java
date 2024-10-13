package com.omgservers.service.service.dispatcher.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveRoomRequest {

    @NotNull
    Long runtimeId;
}
