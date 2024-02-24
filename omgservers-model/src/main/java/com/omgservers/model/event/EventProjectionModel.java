package com.omgservers.model.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventProjectionModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Instant delayed;

    @NotNull
    EventQualifierEnum qualifier;

    @NotNull
    EventStatusEnum status;

    @NotNull
    Boolean deleted;
}
