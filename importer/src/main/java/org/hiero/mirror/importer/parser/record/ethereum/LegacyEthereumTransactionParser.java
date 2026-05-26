// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.ethereum;

import com.esaulpaugh.headlong.rlp.RLPDecoder;
import com.esaulpaugh.headlong.rlp.RLPEncoder;
import com.esaulpaugh.headlong.util.Integers;
import jakarta.inject.Named;
import java.math.BigInteger;
import org.hiero.mirror.common.domain.transaction.EthereumTransaction;
import org.hiero.mirror.importer.exception.InvalidEthereumBytesException;
import org.hiero.mirror.importer.repository.FileDataRepository;
import org.hiero.mirror.importer.service.ContractBytecodeService;

@Named
public final class LegacyEthereumTransactionParser extends AbstractEthereumTransactionParser {

    public static final int LEGACY_TYPE_BYTE = 0;

    private static final int LEGACY_TYPE_RLP_ITEM_COUNT = 9;

    private static final String TRANSACTION_TYPE_NAME = "Legacy";

    public LegacyEthereumTransactionParser(ContractBytecodeService contractBytecodeService, FileDataRepository fileDataRepository) {
        super(contractBytecodeService, fileDataRepository);
    }

    @Override
    public EthereumTransaction decode(byte[] transactionBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected byte[] encode(EthereumTransaction ethereumTransaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
