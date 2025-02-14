/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.jvm.toolchain.internal.operations;


/**
 * Details about the Java tool being used and the toolchain it belongs to.
 *
 * @since 7.6
 */
public interface JavaToolchainUsageProgressDetails {

    /**
     * Name of the tool from Java distribution such as {@code javac}, {@code java} or {@code javadoc}.
     */
    String getToolName();

    /**
     * Toolchain to which the tool belongs.
     */
    JavaToolchain getToolchain();

    interface JavaToolchain {

        /**
         * Returns Java language version such as {@code 11.0.15}.
         */
        String getJavaVersion();

        /**
         * Display name of the toolchain vendor such as {@code Eclipse Temurin}.
         */
        String getJavaVendor();

        String getRuntimeName();

        String getRuntimeVersion();

        String getJvmName();

        String getJvmVersion();

        String getJvmVendor();

        String getArchitecture();

    }

}
