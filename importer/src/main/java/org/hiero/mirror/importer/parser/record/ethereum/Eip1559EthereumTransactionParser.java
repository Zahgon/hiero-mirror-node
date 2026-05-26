// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.ethereum;

import com.esaulpaugh.headlong.rlp.RLPDecoder;
import com.esaulpaugh.headlong.rlp.RLPEncoder;
import com.esaulpaugh.headlong.util.Integers;
import jakarta.inject.Named;
import java.util.List;
import org.hiero.mirror.common.domain.transaction.EthereumTransaction;
import org.hiero.mirror.importer.exception.InvalidEthereumBytesException;
import org.hiero.mirror.importer.repository.FileDataRepository;
import org.hiero.mirror.importer.service.ContractBytecodeService;

@Named
public final class Eip1559EthereumTransactionParser extends AbstractEthereumTransactionParser {

    public static final int EIP1559_TYPE_BYTE = 2;

    private static final byte[] EIP1559_TYPE_BYTES = Integers.toBytes(EIP1559_TYPE_BYTE);

    private static final String TRANSACTION_TYPE_NAME = "EIP1559";

    private static final int EIP1559_TYPE_RLP_ITEM_COUNT = 12;

    public Eip1559EthereumTransactionParser(ContractBytecodeService contractBytecodeService, FileDataRepository fileDataRepository) {
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
