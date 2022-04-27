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

package org.gradle.jvm.toolchain

import com.google.common.base.Charsets
import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.AvailableJavaHomes
import org.gradle.internal.jvm.Jvm
import org.gradle.util.internal.TextUtil

class MavenToolchainsIntegrationTest extends AbstractIntegrationSpec {

    def "test123"() {
        buildFile << """
            plugins {
                id 'java'
            }

            println System.getProperty('java.version')

            println project.property("org.gradle.java.installations.maven-toolchains-file")

//            java {
//                toolchain {
//                    languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
//                }
//            }
        """

        String jvmPath = AvailableJavaHomes.differentJdk.javaHome.absolutePath
        String escapedJvmPath = TextUtil.escapeString(jvmPath)

        def toolchainsFile = file("toolchians.xml")
        executer.withArgument("-Porg.gradle.java.installations.maven-toolchains-file=${TextUtil.escapeString(toolchainsFile.absolutePath)}")
        executer.withToolchainDetectionEnabled()
        executer.withDefaultCharacterEncoding(Charsets.US_ASCII.toString())
        toolchainsFile << """<?xml version="1.0" encoding="UTF8"?>
            <toolchains>
            <toolchain>
            <type>jdk🎉</type>
            <configuration>
            <jdkHome>${escapedJvmPath}</jdkHome>
            </configuration>
            </toolchain>
            </toolchains>"""

        when:
        succeeds 'javaToolchains', '--info'

        then:
        outputContains 'asdsdfadfsadfsadfasdf'
    }
}
