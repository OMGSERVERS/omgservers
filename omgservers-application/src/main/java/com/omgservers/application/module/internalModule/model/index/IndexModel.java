package com.omgservers.application.module.internalModule.model.index;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexModel {

    static public IndexModel create(final String name, final IndexConfigModel config) {
        Instant now = Instant.now();

        IndexModel model = new IndexModel();
        model.setUuid(UUID.randomUUID());
        model.setCreated(now);
        model.setModified(now);
        model.setName(name);
        model.setVersion(1L);
        model.setConfig(config);
        return model;
    }

    static public void validateIndexModel(IndexModel indexModel) {
        if (indexModel == null) {
            throw new ServerSideBadRequestException("index is null");
        }
        validateUuid(indexModel.getUuid());
        validateCreated(indexModel.getCreated());
        validateModified(indexModel.getModified());
        validateName(indexModel.getName());
        validateVersion(indexModel.getVersion());
        IndexConfigModel.validateConfig(indexModel.getConfig());
    }

    static public void validateUuid(UUID uuid) {
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid field is null");
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

    UUID uuid;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    String name;
    Long version;
    @ToString.Exclude
    IndexConfigModel config;
}
