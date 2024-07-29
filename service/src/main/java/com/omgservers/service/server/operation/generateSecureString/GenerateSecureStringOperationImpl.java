package com.omgservers.service.server.operation.generateSecureString;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@ApplicationScoped
class GenerateSecureStringOperationImpl implements GenerateSecureStringOperation {

    final SecureRandom secureRandom;

    public GenerateSecureStringOperationImpl() {
        secureRandom = new SecureRandom();
    }

    @Override
    public String generateSecureString() {
        final var randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
