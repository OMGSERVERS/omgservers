package com.omgservers.schema.model.node;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeModel {

    @NotNull
    Long id;

    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Instant modified;

    @NotNull
    Boolean deleted;
}
