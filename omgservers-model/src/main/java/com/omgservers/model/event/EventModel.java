package com.omgservers.model.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    static public void validate(EventModel event) {
        if (event == null) {
            throw new IllegalArgumentException("event is null");
        }
    }

    Long id;
    Instant created;
    Instant modified;
    Long groupId;
    EventQualifierEnum qualifier;
    @EqualsAndHashCode.Exclude
    EventBodyModel body;
    EventStatusEnum status;
}
