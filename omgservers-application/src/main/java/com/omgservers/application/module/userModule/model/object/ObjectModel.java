package com.omgservers.application.module.userModule.model.object;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectModel {

    static public ObjectModel create(final UUID playerUuid,
                                     final String name,
                                     final byte[] body) {
        return create(playerUuid, UUID.randomUUID(), name, body);
    }

    static public ObjectModel create(final UUID playerUuid,
                                     final UUID uuid,
                                     final String name,
                                     final byte[] body) {
        if (playerUuid == null) {
            throw new ServerSideBadRequestException("player is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }
        if (name == null) {
            throw new ServerSideBadRequestException("fileName is null");
        }
        if (body == null) {
            throw new ServerSideBadRequestException("body is null");
        }

        Instant now = Instant.now();

        ObjectModel object = new ObjectModel();
        object.setPlayer(playerUuid);
        object.setCreated(now);
        object.setModified(now);
        object.setUuid(uuid);
        object.setName(name);
        object.setBody(body);
        return object;
    }

    UUID player;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    String name;
    @ToString.Exclude
    byte[] body;
}
