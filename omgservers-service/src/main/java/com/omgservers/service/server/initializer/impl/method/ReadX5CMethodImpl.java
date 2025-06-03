package com.omgservers.service.server.initializer.impl.method;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.server.ExecuteStateOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ReadX5CMethodImpl implements ReadX5CMethod {

    final GetServiceConfigOperation getServiceConfigOperation;
    final ExecuteStateOperation executeStateOperation;

    @Override
    public Uni<Void> execute() {
        log.debug("Read x5c");

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var certLocation = getServiceConfigOperation.getServiceConfig().jwt().certLocation();

                    final var x5c = readX5C(certLocation);
                    executeStateOperation.setX5C(x5c);

                    log.info("X5C read, {}", x5c);
                })
                .replaceWithVoid();
    }

    String readX5C(final String location) {
        try (final var inputStream = getInputStream(location)) {
            final var factory = CertificateFactory.getInstance("X.509");
            final var cert = (X509Certificate) factory.generateCertificate(inputStream);
            byte[] encoded = cert.getEncoded();
            final var x5c = Base64.getEncoder().encodeToString(encoded);
            return x5c;
        } catch (Exception e) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.X5C_NOT_FOUND, e.getMessage());
        }
    }

    InputStream getInputStream(final String location) throws Exception {
        if (location.startsWith("file:")) {
            final var filename = location.substring("file:".length());
            log.info("Reading from file \"{}\"", filename);
            return Files.newInputStream(Paths.get(filename));
        } else {
            log.info("Reading from resource \"{}\"", location);
            final var resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
            if (resourceStream == null) {
                throw new IllegalArgumentException("Resource not found: " + location);
            }
            return resourceStream;
        }
    }
}
