package com.omgservers.schema.model.job;

public enum JobQualifierEnum {
    /**
     * Tenant job.
     */
    TENANT,

    /**
     * Matchmaker job.
     */
    MATCHMAKER,

    /**
     * Runtime job.
     */
    RUNTIME,

    /**
     * Pool job.
     */
    POOL,

    /**
     * Build request job.
     */
    BUILD_REQUEST,

    /**
     * Queue job.
     */
    QUEUE,
}
