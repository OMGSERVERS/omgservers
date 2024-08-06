package com.omgservers.schema.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = EventDeserializer.class)
public class EventModel {

    @NotNull
    Long id;

    @NotBlank
    String idempotencyKey;

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
    @EqualsAndHashCode.Exclude
    EventBodyModel body;

    @NotNull
    EventStatusEnum status;

    @NotNull
    Boolean deleted;
}
