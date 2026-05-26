// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.grpc.util;

import com.google.protobuf.ByteString;
import com.google.protobuf.UnsafeByteOperations;
import com.hederahashgraph.api.proto.java.Timestamp;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.concurrent.TimeoutException;
import lombok.CustomLog;
import lombok.experimental.UtilityClass;
import org.hiero.mirror.common.exception.InvalidEntityException;
import org.hiero.mirror.grpc.exception.EntityNotFoundException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.dao.TransientDataAccessException;
import reactor.core.Exceptions;

@CustomLog
@UtilityClass
public final class ProtoUtil {

    static final String DB_ERROR = "Error querying the data source. Please retry later";

    static final String OVERFLOW_ERROR = "Client lags too much behind. Please retry later";

    static final String UNKNOWN_ERROR = "Unknown error";

    public static Instant fromTimestamp(Timestamp timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ByteString toByteString(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static StatusRuntimeException toStatusRuntimeException(Throwable t) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static StatusRuntimeException clientError(Throwable t, Status status, String message) {
        log.warn("Client error {}: {}", t.getClass().getSimpleName(), t.getMessage());
        return status.augmentDescription(message).asRuntimeException();
    }

    private static StatusRuntimeException serverError(Throwable t, Status status, String message) {
        log.error("Server error: ", t);
        return status.augmentDescription(message).asRuntimeException();
    }

    public static Timestamp toTimestamp(Long secondsNanos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Timestamp toTimestamp(Instant instant) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
