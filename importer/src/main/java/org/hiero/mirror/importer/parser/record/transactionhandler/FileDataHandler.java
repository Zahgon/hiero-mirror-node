// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.parser.record.transactionhandler;

import static org.hiero.mirror.common.util.DomainUtils.toBytes;
import com.google.protobuf.ByteString;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.hiero.mirror.common.domain.file.FileData;
import org.hiero.mirror.common.domain.transaction.Transaction;
import org.hiero.mirror.common.util.DomainUtils;
import org.hiero.mirror.importer.addressbook.AddressBookService;
import org.hiero.mirror.importer.parser.record.entity.EntityListener;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;

@Named
@RequiredArgsConstructor
final class FileDataHandler {

    private final AddressBookService addressBookService;

    private final EntityListener entityListener;

    private final EntityProperties entityProperties;

    void handle(Transaction transaction, ByteString contents) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
