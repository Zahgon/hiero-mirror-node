// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.ethereum;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_BYTE_ARRAY;
import com.esaulpaugh.headlong.util.Integers;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.file.FileData;
import org.hiero.mirror.common.domain.transaction.EthereumTransaction;
import org.hiero.mirror.importer.repository.FileDataRepository;
import org.hiero.mirror.importer.service.ContractBytecodeService;
import org.hiero.mirror.importer.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
abstract class AbstractEthereumTransactionParser implements EthereumTransactionParser {

    private final ContractBytecodeService contractBytecodeService;

    private final FileDataRepository fileDataRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public final byte[] getHash(byte[] callData, EntityId callDataId, long consensusTimestamp, byte[] transactionBytes, boolean useCurrentState) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract byte[] encode(EthereumTransaction ethereumTransaction);

    protected static byte[] getValue(EthereumTransaction ethereumTransaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static byte[] getHash(byte[] rawBytes) {
        return new Keccak.Digest256().digest(rawBytes);
    }

    private byte[] getCallData(EntityId callDataId, long consensusTimestamp, boolean useCurrentState) {
        return useCurrentState ? contractBytecodeService.get(callDataId) : fileDataRepository.getFileAtTimestamp(callDataId.getId(), consensusTimestamp).map(FileData::getFileData).map(Utility::decodeBytecode).orElse(null);
    }
}
