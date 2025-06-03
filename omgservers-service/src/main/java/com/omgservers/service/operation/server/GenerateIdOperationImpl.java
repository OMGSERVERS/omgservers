package com.omgservers.service.operation.server;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
class GenerateIdOperationImpl implements GenerateIdOperation {

    final ExecuteStateOperation executeStateOperation;

    volatile Generator generator;

    public GenerateIdOperationImpl(final ExecuteStateOperation executeStateOperation) {
        this.executeStateOperation = executeStateOperation;
    }

    @Override
    public long generateId() {
        if (generator == null) {
            synchronized (this) {
                if (generator == null) {
                    generator = createGenerator();
                }
            }
        }

        return generator.generateId();
    }

    Generator createGenerator() {
        final var nodeId = executeStateOperation.getNodeId();
        if (Objects.nonNull(nodeId)) {
            return new Generator(nodeId);
        } else {
            throw new ServerSideInternalException(ExceptionQualifierEnum.ID_GENERATOR_FAILED,
                    "nodeId not acquired yet");
        }
    }

    static class Generator {

        final long nodeId;

        long lastTimestamp;
        long sequence;

        Generator(final long nodeId) {
            this.nodeId = nodeId;

            lastTimestamp = 0;
            sequence = 0;

            log.info("Generator created, (timestampBits={}, nodeIdBits={}, sequenceBits={}), nodeId={}",
                    TIMESTAMP_BITS, NODE_ID_BITS, SEQUENCE_BITS, nodeId);
        }

        synchronized long generateId() {
            var timestamp = System.currentTimeMillis() - TIMESTAMP_EPOCH;

            if (timestamp < lastTimestamp) {
                timestamp = lastTimestamp;
            }

            if (timestamp == lastTimestamp) {
                sequence += 1;

                if (sequence > SEQUENCE_MASK) {
                    throw new ServerSideInternalException(ExceptionQualifierEnum.ID_GENERATOR_FAILED,
                            String.format("sequence overflowed, sequence=%d, timestamp=%d", sequence, timestamp));
                }
            } else {
                sequence = 0;
            }

            lastTimestamp = timestamp;

            return timestamp << TIMESTAMP_OFFSET |
                    nodeId << NODE_ID_OFFSET |
                    sequence;
        }
    }
}
