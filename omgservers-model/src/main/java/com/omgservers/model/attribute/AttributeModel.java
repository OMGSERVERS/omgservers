package com.omgservers.model.attribute;

import com.omgservers.exception.ServerSideBadRequestException;
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

    Long id;
    Long userId;
    Long playerId;
    Instant created;
    Instant modified;
    String name;
    String value;
}
