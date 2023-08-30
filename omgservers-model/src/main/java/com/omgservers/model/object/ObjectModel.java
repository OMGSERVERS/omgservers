package com.omgservers.model.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectModel {

    Long id;
    Long playerId;
    Instant created;
    Instant modified;
    String name;
    @ToString.Exclude
    byte[] body;
}