// SPDX-License-Identifier: Apache-2.0
package org.hiero.mirror.importer.config;

import static org.hiero.mirror.common.util.RuntimeHintsHelper.CONSTRUCTORS_AND_METHODS;
import static org.hiero.mirror.common.util.RuntimeHintsHelper.registerPackage;
import static org.hiero.mirror.common.util.RuntimeHintsHelper.registerReflectionTypes;
import static org.hiero.mirror.common.util.RuntimeHintsHelper.registerResourcePatterns;
import java.time.Duration;
import org.apache.velocity.runtime.ParserPoolImpl;
import org.apache.velocity.runtime.directive.Foreach;
import org.apache.velocity.runtime.parser.StandardParser;
import org.apache.velocity.runtime.resource.ResourceCacheImpl;
import org.apache.velocity.runtime.resource.ResourceManagerImpl;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.util.introspection.TypeConversionHandlerImpl;
import org.apache.velocity.util.introspection.UberspectImpl;
import org.hiero.mirror.importer.parser.record.entity.EntityProperties;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(RuntimeHintsConfiguration.CustomRuntimeHints.class)
@NullMarked
final class RuntimeHintsConfiguration {

    static final class CustomRuntimeHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void registerVelocityHints(RuntimeHints hints, ClassLoader loader) {
            registerPackage(hints, loader, Foreach.class.getPackageName(), CONSTRUCTORS_AND_METHODS);
            registerReflectionTypes(hints, CONSTRUCTORS_AND_METHODS, ResourceManagerImpl.class, ResourceCacheImpl.class, ClasspathResourceLoader.class, StandardParser.class, ParserPoolImpl.class, UberspectImpl.class, TypeConversionHandlerImpl.class, Duration.class);
            registerResourcePatterns(hints, "org/apache/velocity/runtime/defaults/*", "db/template/**");
        }
    }
}
