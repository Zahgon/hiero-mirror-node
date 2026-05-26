// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.state;

import com.hedera.hapi.node.base.FileID;
import com.hedera.hapi.node.base.Key;
import com.hedera.hapi.node.base.KeyList;
import com.hedera.hapi.node.base.Timestamp;
import com.hedera.pbj.runtime.ParseException;
import com.hedera.pbj.runtime.io.buffer.Bytes;
import java.time.Instant;
import lombok.experimental.UtilityClass;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.util.DomainUtils;

@UtilityClass
public class Utils {

    public static final long DEFAULT_AUTO_RENEW_PERIOD = 7776000L;

    public static final int EVM_ADDRESS_LEN = 20;

    public static final Key EMPTY_KEY_LIST = Key.newBuilder().keyList(KeyList.DEFAULT).build();

    public static final Key DEFAULT_KEY = Key.newBuilder().keyList(KeyList.newBuilder().keys(Key.newBuilder().ecdsaSecp256k1(Bytes.wrap(new byte[] { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 })).build()).build()).build();

    public static Key parseKey(final byte[] keyBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts a timestamp in nanoseconds to a PBJ Timestamp object.
     *
     * @param timestamp The timestamp in nanoseconds.
     * @return The PBJ Timestamp object.
     */
    public static Timestamp convertToTimestamp(final long timestamp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long getCurrentTimestamp() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static FileID toFileID(final EntityId entityId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
