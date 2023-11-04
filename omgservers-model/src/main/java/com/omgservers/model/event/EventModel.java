package com.omgservers.model.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    public static void validate(EventModel event) {
        if (event == null) {
            throw new IllegalArgumentException("event is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    Long groupId;

    @NotNull
    EventQualifierEnum qualifier;

    @NotNull
    @EqualsAndHashCode.Exclude
    Boolean relayed;

    @NotNull
    @EqualsAndHashCode.Exclude
    EventBodyModel body;

    @NotNull
    @EqualsAndHashCode.Exclude
    EventStatusEnum status;
}
