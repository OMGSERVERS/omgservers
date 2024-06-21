package com.omgservers.model.index;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexModel {

    @NotNull
    Long id;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant created;

    @NotNull
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    @Size(max = 64)
    String name;

    @NotNull
    @ToString.Exclude
    IndexConfigModel config;

    @NotNull
    Boolean deleted;
}
