package com.omgservers.application.module.userModule.model.token;

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
public class TokenModel {

    static public void validateToken(TokenModel tokenModel) {
        if (tokenModel == null) {
            throw new ServerSideBadRequestException("token is null");
        }
        validateUser(tokenModel.getUser());
        validateCreated(tokenModel.getCreated());
        validateUuid(tokenModel.getUuid());
        validateExpire(tokenModel.getExpire());
        validateHash(tokenModel.getHash());
    }

    static public void validateUser(UUID user) {
        if (user == null) {
            throw new ServerSideBadRequestException("user field is null");
        }
    }

    static public void validateCreated(Instant created) {
        if (created == null) {
            throw new ServerSideBadRequestException("created field is null");
        }
    }

    static public void validateUuid(UUID uuid) {
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid field is null");
        }
    }

    static public void validateExpire(Instant expire) {
        if (expire == null) {
            throw new ServerSideBadRequestException("expire field is null");
        }
    }

    static public void validateHash(String hash) {
        if (hash == null) {
            throw new ServerSideBadRequestException("hash field is null");
        }
        if (hash.isBlank()) {
            throw new ServerSideBadRequestException("hash string is blank");
        }
        if (hash.length() > 128) {
            throw new ServerSideBadRequestException("hash string is too long");
        }
    }

    UUID user;
    @ToString.Exclude
    Instant created;
    UUID uuid;
    Instant expire;
    @ToString.Exclude
    String hash;
}
