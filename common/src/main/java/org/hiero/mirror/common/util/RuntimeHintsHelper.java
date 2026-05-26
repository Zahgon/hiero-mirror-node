// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.common.util;

import java.lang.annotation.Annotation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.TypeReference;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NullMarked
public final class RuntimeHintsHelper {

    public static final MemberCategory[] NONE = {};

    public static final MemberCategory[] UNSAFE_ALLOCATED = { MemberCategory.UNSAFE_ALLOCATED };

    public static final MemberCategory[] METHODS_ONLY = { MemberCategory.INVOKE_DECLARED_METHODS };

    public static final MemberCategory[] CONSTRUCTORS_AND_METHODS = { MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS };

    public static final MemberCategory[] CONSTRUCTORS_ONLY = { MemberCategory.INVOKE_DECLARED_CONSTRUCTORS };

    public static final MemberCategory[] CONSTRUCTORS_AND_FIELDS = { MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.ACCESS_DECLARED_FIELDS };

    private static final MemberCategory[] DEFAULT_CATEGORIES = { MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.ACCESS_DECLARED_FIELDS };

    public static void registerReflectionTypes(RuntimeHints hints, String... classNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerReflectionTypes(RuntimeHints hints, MemberCategory[] memberCategories, String... classNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerReflectionTypes(RuntimeHints hints, MemberCategory[] memberCategories, Class<?>... types) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerReflectionType(RuntimeHints hints, Class<?> type, MemberCategory... memberCategories) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerReflectionType(RuntimeHints hints, String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerReflectionType(RuntimeHints hints, String className, MemberCategory... memberCategories) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerAnnotatedPackage(RuntimeHints hints, ClassLoader loader, String basePackage, Class<? extends Annotation> annotationType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerAnnotatedPackage(RuntimeHints hints, ClassLoader loader, String basePackage, Class<? extends Annotation> annotationType, MemberCategory... memberCategories) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerPackage(RuntimeHints hints, ClassLoader loader, String basePackage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerPackage(RuntimeHints hints, ClassLoader loader, String basePackage, MemberCategory... memberCategories) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void registerResourcePatterns(RuntimeHints hints, String... patterns) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void registerType(RuntimeHints hints, String className, MemberCategory... memberCategories) {
        final var type = TypeReference.of(className);
        hints.reflection().registerType(type, memberCategories);
    }

    private static void registerPackageMatching(RuntimeHints hints, ClassLoader loader, String basePackage, TypeFilter includeFilter, MemberCategory... memberCategories) {
        final var scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(includeFilter);
        for (final var candidate : scanner.findCandidateComponents(basePackage)) {
            final var className = candidate.getBeanClassName();
            if (className == null) {
                continue;
            }
            registerLoadedClass(hints, loader, className, memberCategories);
        }
    }

    private static void registerLoadedClass(RuntimeHints hints, ClassLoader loader, String className, MemberCategory... memberCategories) {
        try {
            final var type = Class.forName(className, false, loader);
            hints.reflection().registerType(type, memberCategories);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to register runtime hints for " + className, e);
        }
    }
}
