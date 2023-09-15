package com.omgservers.model.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexModel {

    Long id;
    Instant created;
    Instant modified;
    String name;
    Long version;
    @ToString.Exclude
    IndexConfigModel config;
}
