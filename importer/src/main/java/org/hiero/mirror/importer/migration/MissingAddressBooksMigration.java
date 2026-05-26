// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.migration;

import jakarta.inject.Named;
import org.flywaydb.core.api.configuration.Configuration;
import org.hiero.mirror.importer.ImporterProperties;
import org.hiero.mirror.importer.addressbook.AddressBookService;
import org.hiero.mirror.importer.repository.AddressBookServiceEndpointRepository;
import org.springframework.beans.factory.ObjectProvider;

@Named
public class MissingAddressBooksMigration extends RepeatableMigration {

    private final ObjectProvider<AddressBookService> addressBookServiceProvider;

    private final ObjectProvider<AddressBookServiceEndpointRepository> addressBookServiceEndpointRepositoryProvider;

    public MissingAddressBooksMigration(ObjectProvider<AddressBookService> addressBookServiceProvider, ObjectProvider<AddressBookServiceEndpointRepository> addressBookServiceEndpointRepositoryProvider, ImporterProperties importerProperties) {
        super(importerProperties.getMigration());
        this.addressBookServiceProvider = addressBookServiceProvider;
        this.addressBookServiceEndpointRepositoryProvider = addressBookServiceEndpointRepositoryProvider;
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean skipMigration(Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doMigrate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
