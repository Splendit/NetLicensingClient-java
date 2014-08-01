/* Licensed under the Apache License, Version 2.0 (the "License");
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
package com.labs64.netlicensing.domain.vo;

/**
 * Enumerates possible security modes for accessing the NetLicensing API.
 * <p>
 * See {@link https://www.labs64.de/confluence/x/pwCo} for details.
 * </p>
 */
public enum SecurityMode {

    BASIC_AUTHENTICATION,
    APIKEY_IDENTIFICATION

}
