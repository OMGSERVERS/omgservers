package com.omgservers.model.object;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectModel {

    @NotNull
    Long id;

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    Instant created;

    @NotNull
    Instant modified;

    @NotNull
    @Size(max = 64)
    String name;

    @NotNull
    @ToString.Exclude
    byte[] body;
}
