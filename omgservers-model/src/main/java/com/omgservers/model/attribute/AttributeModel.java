package com.omgservers.model.attribute;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeModel {

    public static void validate(AttributeModel attribute) {
        if (attribute == null) {
            throw new ServerSideBadRequestException("attribute is null");
        }
    }

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
    @Size(max = 1024)
    String value;
}
