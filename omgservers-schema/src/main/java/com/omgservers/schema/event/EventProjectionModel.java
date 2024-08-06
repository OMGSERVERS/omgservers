package com.omgservers.schema.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventProjectionModel {

    @NotNull
    Long id;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
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
