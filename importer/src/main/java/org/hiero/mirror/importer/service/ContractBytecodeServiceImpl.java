// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.service;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.entity.EntityId;
import org.hiero.mirror.importer.util.Utility;

@Named
@RequiredArgsConstructor
class ContractBytecodeServiceImpl implements ContractBytecodeService {

    private final FileDataService fileDataService;

    @Override
    public byte[] get(EntityId fileId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
