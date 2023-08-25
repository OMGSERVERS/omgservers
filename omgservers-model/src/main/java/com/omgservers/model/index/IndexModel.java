package com.omgservers.model.index;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexModel {

    static public void validate(IndexModel indexModel) {
        if (indexModel == null) {
            throw new ServerSideBadRequestException("index is null");
        }
        validateId(indexModel.getId());
        validateCreated(indexModel.getCreated());
        validateModified(indexModel.getModified());
        validateName(indexModel.getName());
        validateVersion(indexModel.getVersion());
        IndexConfigModel.validateConfig(indexModel.getConfig());
    }

    static public void validateId(Long id) {
        if (id == null) {
            throw new ServerSideBadRequestException("id field is null");
        }
    }

    static public void validateCreated(Instant created) {
        if (created == null) {
            throw new ServerSideBadRequestException("created field is null");
        }
    }

    static public void validateModified(Instant modified) {
        if (modified == null) {
            throw new ServerSideBadRequestException("modified field is null");
        }
    }

    static public void validateName(String name) {
        if (name == null) {
            throw new ServerSideBadRequestException("fileName is null");
        }
        if (name.isBlank()) {
            throw new ServerSideBadRequestException("fileName is blank");
        }
        if (name.length() > 128) {
            throw new ServerSideBadRequestException("fileName is too long");
        }
        if (!name.matches("^[a-z0-9-]*$")) {
            throw new ServerSideBadRequestException("fileName is wrong, value=" + name);
        }
    }

    static public void validateVersion(Long version) {
        if (version == null) {
            throw new ServerSideBadRequestException("version field is null");
        }
        if (version < 1) {
            throw new ServerSideBadRequestException("version value is wrong, value=" + version);
        }
    }

    Long id;
    Instant created;
    Instant modified;
    String name;
    Long version;
    @ToString.Exclude
    IndexConfigModel config;
}
