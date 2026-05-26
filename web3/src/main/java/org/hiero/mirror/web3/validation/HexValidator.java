// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.web3.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class HexValidator implements ConstraintValidator<Hex, String> {

    public static final String MESSAGE = "invalid hexadecimal string";

    public static final String HEX_PREFIX = "0x";

    private boolean allowEmpty;

    private long minLength;

    private Pattern pattern;

    @Override
    public void initialize(Hex hex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
