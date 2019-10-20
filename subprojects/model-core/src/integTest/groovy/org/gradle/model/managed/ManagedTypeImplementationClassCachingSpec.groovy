/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.model.managed

import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.FailsWithInstantExecution

class ManagedTypeImplementationClassCachingSpec extends AbstractIntegrationSpec {

    @FailsWithInstantExecution
    def "managed type implementation class is generated once for each type and reused"() {
        when:
        buildScript '''
            @Managed
            interface Named {
                String getName()
                void setName(String name)
            }

            class RulePlugin extends RuleSource {
                @Model
                void first(Named first) {
                }

                @Model
                void second(Named second) {
                }

                @Mutate
                void addCompareImplementationClassesTask(ModelMap<Task> tasks, @Path("first") Named first, @Path("second") Named second) {
                    tasks.create("compareImplementationClasses") {
                        it.doLast {
                            println "implementation class is reused: ${first.getClass().is(second.getClass())}"
                        }
                    }
                }
            }

            apply type: RulePlugin
        '''

        then:
        succeeds "compareImplementationClasses"

        and:
        output.contains("implementation class is reused: true")
    }
}
