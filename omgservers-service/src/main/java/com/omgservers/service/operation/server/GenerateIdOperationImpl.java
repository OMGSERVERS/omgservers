package com.omgservers.service.operation.server;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GenerateIdOperationImpl implements GenerateIdOperation {

    final long instanceId;

    long lastTimestamp;
    long sequence;

    public GenerateIdOperationImpl(final GetServiceConfigOperation getServiceConfigOperation) {
        instanceId = getServiceConfigOperation.getServiceConfig().server().instanceId();
        if (instanceId < 0 || instanceId >= 1 << INSTANCE_ID_BITS) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.WRONG_CONFIGURATION,
                    "server instanceId is wrong, value=" + instanceId);
        }

        log.info("Generator was initialized, " +
                        "(timestampBits={}, instanceIdBits={}, sequenceBits={}) instanceId={}",
                TIMESTAMP_BITS, INSTANCE_ID_BITS, SEQUENCE_BITS, instanceId);

        lastTimestamp = 0;
        sequence = 0;
    }

    @Override
    public synchronized long generateId() {
        var timestamp = System.currentTimeMillis() - TIMESTAMP_EPOCH;

        if (timestamp < lastTimestamp) {
            timestamp = lastTimestamp;
        }

        if (timestamp == lastTimestamp) {
            sequence += 1;

            if (sequence > SEQUENCE_MASK) {
                throw new ServerSideInternalException(ExceptionQualifierEnum.ID_GENERATOR_FAILED,
                        String.format("sequence was overflowed, sequence=%d, timestamp=%d", sequence, timestamp));
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return timestamp << TIMESTAMP_OFFSET |
                instanceId << INSTANCE_ID_OFFSET |
                sequence;
    }
}
