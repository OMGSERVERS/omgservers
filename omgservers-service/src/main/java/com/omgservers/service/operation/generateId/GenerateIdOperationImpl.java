package com.omgservers.service.operation.generateId;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GenerateIdOperationImpl implements GenerateIdOperation {

    final long datacenterId;
    final long instanceId;

    long lastTimestamp;
    long sequence;

    public GenerateIdOperationImpl(final GetConfigOperation getConfigOperation) {
        datacenterId = getConfigOperation.getServiceConfig().generator().datacenterId();
        if (datacenterId < 0 || datacenterId >= 1 << DATACENTER_ID_BITS) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.WRONG_CONFIGURATION,
                    "wrong datacenterId, value=" + datacenterId);
        }

        instanceId = getConfigOperation.getServiceConfig().generator().instanceId();
        if (instanceId < 0 || instanceId >= 1 << INSTANCE_ID_BITS) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.WRONG_CONFIGURATION,
                    "wrong instanceId, value=" + instanceId);
        }

        log.info("Generator was initialized, " +
                        "(timestampBits={}, datacenterIdBits={}, instanceIdBits={}, sequenceBits={}) " +
                        "datacenterId={}, instanceId={}",
                TIMESTAMP_BITS, DATACENTER_ID_BITS, INSTANCE_ID_BITS, SEQUENCE_BITS,
                datacenterId, instanceId);

        lastTimestamp = 0;
        sequence = 0;
    }

    @Override
    public synchronized long generateId() {
        var timestamp = System.currentTimeMillis() - TIMESTAMP_EPOCH;

        if (timestamp == lastTimestamp) {
            sequence += 1;

            if (sequence >= (1 << SEQUENCE_BITS)) {
                throw new ServerSideInternalException(ExceptionQualifierEnum.ID_GENERATOR_FAILED,
                        String.format("sequence was overflowed, sequence=%d, timestamp=%d", sequence, timestamp));
            }
        } else if (timestamp > lastTimestamp) {
            sequence = 0;
        }

        if (timestamp < lastTimestamp) {
            throw new ServerSideInternalException(ExceptionQualifierEnum.ID_GENERATOR_FAILED,
                    String.format("wrong system time, current=%d, last=%d", timestamp, lastTimestamp));
        } else {
            lastTimestamp = timestamp;
        }

        var id = timestamp << TIMESTAMP_OFFSET |
                datacenterId << DATACENTER_ID_OFFSET |
                instanceId << INSTANCE_ID_OFFSET |
                sequence;
        return id;
    }
}
