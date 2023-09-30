package com.omgservers.model.eventProjection;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventProjectionModel {

    @NotNull
    Long id;

    @NotNull
    Long groupId;
}
