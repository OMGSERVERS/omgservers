package com.omgservers.model.token;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {

    static public void validate(TokenModel tokenModel) {
        if (tokenModel == null) {
            throw new ServerSideBadRequestException("token is null");
        }
        validateUserId(tokenModel.getUserId());
        validateCreated(tokenModel.getCreated());
        validateId(tokenModel.getId());
        validateExpire(tokenModel.getExpire());
        validateHash(tokenModel.getHash());
    }

    static public void validateUserId(Long userId) {
        if (userId == null) {
            throw new ServerSideBadRequestException("userId field is null");
        }
    }

    static public void validateCreated(Instant created) {
        if (created == null) {
            throw new ServerSideBadRequestException("created field is null");
        }
    }

    static public void validateId(Long id) {
        if (id == null) {
            throw new ServerSideBadRequestException("id field is null");
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

    Long id;
    Long userId;
    Instant created;
    Instant expire;
    @ToString.Exclude
    String hash;
}
