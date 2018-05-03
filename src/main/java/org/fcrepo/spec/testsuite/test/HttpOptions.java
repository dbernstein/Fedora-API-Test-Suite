/*
 * Licensed to DuraSpace under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 *
 * DuraSpace licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fcrepo.spec.testsuite.test;

import static org.hamcrest.Matchers.containsString;

import java.io.FileNotFoundException;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.fcrepo.spec.testsuite.TestInfo;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author Jorge Abrego, Fernando Cardoza
 */
public class HttpOptions extends AbstractTest {

    /**
     * Authentication
     *
     * @param username
     * @param password
     */
    @Parameters({"param2", "param3"})
    public HttpOptions(final String username, final String password) {
        super(username, password);
    }

    /**
     * 3.4-A
     *
     * @param uri
     */
    @Test(groups = {"MUST"})
    @Parameters({"param1"})
    public void httpOptionsSupport(final String uri) throws FileNotFoundException {
        final TestInfo info = setupTest("3.4-A", "httpOptionsSupport",
                                        "Any LDPR must support OPTIONS per [LDP] 4.2.8. "
                                        + "4.2.8.1 LDP servers must support the HTTP OPTIONS method.",
                                        "https://fcrepo.github.io/fcrepo-specification/#http-options",
                                        ps);
        RestAssured.given()
                   .auth().basic(this.username, this.password)
                   .config(RestAssured.config().logConfig(new LogConfig().defaultStream(ps)))
                   .log().all()
                   .when()
                   .options(uri)
                   .then()
                   .log().all()
                   .statusCode(200);

    }

    /**
     * 3.4-B
     *
     * @param uri
     */
    @Test(groups = {"MUST"})
    @Parameters({"param1"})
    public void httpOptionsSupportAllow(final String uri) throws FileNotFoundException {
        final TestInfo info = setupTest("3.4-B", "httpOptionsSupportAllow",
                                        "Any LDPR must support OPTIONS per [LDP] 4.2.8. "
                                        + "LDP servers must indicate their support for HTTP Methods by responding to a"
                                        +
                                        " HTTP OPTIONS request on the LDPR’s URL with the HTTP Method tokens in the " +
                                        "HTTP response header Allow.",
                                        "https://fcrepo.github.io/fcrepo-specification/#http-options",
                                        ps);
        RestAssured.given()
                   .auth().basic(this.username, this.password)
                   .config(RestAssured.config().logConfig(new LogConfig().defaultStream(ps)))
                   .log().all()
                   .when()
                   .options(uri)
                   .then()
                   .log().all()
                   .statusCode(200).header("Allow", containsString("GET"));

    }
}
