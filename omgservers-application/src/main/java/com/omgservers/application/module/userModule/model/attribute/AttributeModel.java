package com.omgservers.application.module.userModule.model.attribute;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeModel {

    static public void validate(AttributeModel attribute) {
        if (attribute == null) {
            throw new ServerSideBadRequestException("attribute is null");
        }
    }

    Long id;
    Long playerId;
    Instant created;
    Instant modified;
    String name;
    String value;
}
