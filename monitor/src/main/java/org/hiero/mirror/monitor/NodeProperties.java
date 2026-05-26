// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.monitor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.net.InetAddresses;
import com.google.protobuf.ByteString;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.proto.AccountID;
import com.hedera.hashgraph.sdk.proto.NodeAddress;
import com.hedera.hashgraph.sdk.proto.ServiceEndpoint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class NodeProperties {

    @NotBlank
    private String accountId;

    @Getter(lazy = true)
    @JsonIgnore
    @ToString.Exclude
    private final List<AccountId> accountIds = List.of(AccountId.fromString(getAccountId()));

    private String certHash;

    @NotBlank
    private String host;

    private Long nodeId;

    @Min(0)
    @Max(65535)
    private int port = 50211;

    public NodeProperties(String accountId, String host) {
        this.accountId = accountId;
        this.host = host;
    }

    public String getEndpoint() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getNodeId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SneakyThrows
    public NodeAddress toNodeAddress() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ByteString toIpAddressV4() throws UnknownHostException {
        if (!InetAddresses.isInetAddress(host)) {
            return ByteString.EMPTY;
        }
        var address = InetAddress.getByName(host).getAddress();
        return ByteString.copyFrom(address);
    }
}
