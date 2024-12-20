package com.omgservers.service.module.dispatcher.impl.service.roomService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomResponse {

    @NotNull
    Boolean created;
}
