// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.ethereum;

import com.esaulpaugh.headlong.rlp.RLPDecoder;
import com.esaulpaugh.headlong.rlp.RLPEncoder;
import com.esaulpaugh.headlong.rlp.RLPItem;
import com.esaulpaugh.headlong.util.Integers;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import org.hiero.mirror.common.domain.transaction.Authorization;
import org.hiero.mirror.common.domain.transaction.EthereumTransaction;
import org.hiero.mirror.importer.exception.InvalidEthereumBytesException;
import org.hiero.mirror.importer.repository.FileDataRepository;
import org.hiero.mirror.importer.service.ContractBytecodeService;

@Named
final class Eip7702EthereumTransactionParser extends AbstractEthereumTransactionParser {

    public static final int EIP7702_TYPE_BYTE = 4;

    private static final byte[] EIP7702_TYPE_BYTES = Integers.toBytes(EIP7702_TYPE_BYTE);

    private static final String TRANSACTION_TYPE_NAME = "EIP7702";

    private static final int EIP7702_TYPE_RLP_ITEM_COUNT = 13;

    private static final int AUTHORIZATION_TUPLE_SIZE = 6;

    Eip7702EthereumTransactionParser(ContractBytecodeService contractBytecodeService, FileDataRepository fileDataRepository) {
        super(contractBytecodeService, fileDataRepository);
    }

    @Override
    public EthereumTransaction decode(byte[] transactionBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<Authorization> parseAuthorizationList(RLPItem authorizationListItem) {
        if (!authorizationListItem.isList()) {
            throw new InvalidEthereumBytesException(TRANSACTION_TYPE_NAME, "Authorization list is not a list");
        }
        var authorizationTuples = authorizationListItem.asRLPList().elements();
        var authorizations = new ArrayList<Authorization>();
        for (var tupleItem : authorizationTuples) {
            if (!tupleItem.isList()) {
                throw new InvalidEthereumBytesException(TRANSACTION_TYPE_NAME, "Authorization tuple is not a list");
            }
            var tuple = tupleItem.asRLPList().elements();
            if (tuple.size() != AUTHORIZATION_TUPLE_SIZE) {
                throw new InvalidEthereumBytesException(TRANSACTION_TYPE_NAME, String.format("Authorization tuple size was %d but expected %d", tuple.size(), AUTHORIZATION_TUPLE_SIZE));
            }
            var authorization = Authorization.builder().chainId(Hex.encodeHexString(tuple.get(0).data())).address(Hex.encodeHexString(tuple.get(1).data())).nonce(tuple.get(2).asLong()).yParity((int) tuple.get(3).asByte()).r(Hex.encodeHexString(tuple.get(4).data())).s(Hex.encodeHexString(tuple.get(5).data())).build();
            authorizations.add(authorization);
        }
        return authorizations;
    }

    @Override
    protected byte[] encode(EthereumTransaction ethereumTransaction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<List<byte[]>> encodeAuthorizationList(List<Authorization> authorizations) {
        if (authorizations == null || authorizations.isEmpty()) {
            return List.of();
        }
        var encodedList = new ArrayList<List<byte[]>>();
        for (var auth : authorizations) {
            encodedList.add(List.of(decodeHex(auth.getChainId()), decodeHex(auth.getAddress()), Integers.toBytes(auth.getNonce()), Integers.toBytes(auth.getYParity()), decodeHex(auth.getR()), decodeHex(auth.getS())));
        }
        return encodedList;
    }

    private byte[] decodeHex(String hex) {
        try {
            return Hex.decodeHex(hex);
        } catch (Exception e) {
            throw new InvalidEthereumBytesException(TRANSACTION_TYPE_NAME, "Invalid hex string: " + hex);
        }
    }
}
