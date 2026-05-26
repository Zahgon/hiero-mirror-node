// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.evm.contracts.execution.traceability;

import static org.hiero.mirror.web3.convert.BytesDecoder.startsWithErrorSelector;
import static org.hiero.mirror.web3.validation.HexValidator.HEX_PREFIX;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.hedera.node.app.service.contract.impl.state.RootProxyWorldUpdater;
import com.hederahashgraph.api.proto.java.ResponseCodeEnum;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.tuweni.bytes.Bytes;
import org.bouncycastle.util.encoders.Hex;
import org.hiero.mirror.web3.common.ContractCallContext;
import org.hiero.mirror.web3.convert.BytesDecoder;
import org.hyperledger.besu.evm.ModificationNotAllowedException;
import org.hyperledger.besu.evm.frame.MessageFrame;
import org.springframework.util.CollectionUtils;

public abstract class AbstractOpcodeTracer {

    // Value taken by analyzing a heavy call output
    private static final int HEX_CACHE_MAX_SIZE = 1600;

    // Common cache that keeps hex string representation of different Bytes keys. We can have the same Bytes occurrence
    // on multiple opcode data and this cache helps to avoid unnecessary string allocations.
    private final LoadingCache<Bytes, String> hexCache = Caffeine.newBuilder().maximumSize(HEX_CACHE_MAX_SIZE).expireAfterAccess(Duration.ofMinutes(1)).build(Bytes::toHexString);

    protected final List<String> captureMemory(final MessageFrame frame, final OpcodeContext options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final List<String> captureStack(final MessageFrame frame, final OpcodeContext options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Map<String, String> captureStorage(final MessageFrame frame, final OpcodeContext options, final ContractCallContext context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected final String getRevertReasonFromContractActions(final ContractCallContext context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Formats the revert reason to be consistent with the revert reason format in the EVM. <a
     * href="https://besu.hyperledger.org/23.10.2/private-networks/how-to/send-transactions/revert-reason#revert-reason-format">...</a>
     *
     * @param revertReason the revert reason as byte array
     * @return the formatted revert reason as hex string
     */
    protected final String formatRevertReason(final byte[] revertReason) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isZero(final byte[] bytes) {
        for (var b : bytes) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    private int findFirstNonZero(final byte[] bytes) {
        var index = 0;
        while (index < bytes.length && bytes[index] == 0) {
            index++;
        }
        return index;
    }

    private int toInt(final byte[] bytes, final int offset) {
        var result = 0;
        for (var i = offset; i < bytes.length; i++) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return result;
    }
}
