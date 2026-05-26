// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.service;

import com.google.common.primitives.Bytes;
import com.hedera.services.stream.proto.ContractBytecode;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.common.domain.transaction.RecordItem;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.util.Utility;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Named
@NullMarked
@RequiredArgsConstructor
public final class ContractInitcodeServiceImpl implements ContractInitcodeService {

    private final ContractBytecodeService contractBytecodeService;

    @Override
    public byte @Nullable [] get(@Nullable ContractBytecode contractBytecode, RecordItem recordItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
