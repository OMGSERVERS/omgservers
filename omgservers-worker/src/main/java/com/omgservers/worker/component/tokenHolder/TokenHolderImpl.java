package com.omgservers.worker.component.tokenHolder;

import com.omgservers.worker.exception.TokenIsNotReadyYetException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TokenHolderImpl implements TokenHolder {

    final TokenContainer tokenContainer;

    public String getToken() {
        final var token = tokenContainer.get();
        if (Objects.isNull(token) || token.isBlank()) {
            throw new TokenIsNotReadyYetException("token is not ready yet");
        }
        return token;
    }
}
