/*
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 */ 
package org.apache.rat.analysis.license;

import org.apache.rat.api.Document;
import org.apache.rat.document.MockLocation;
import org.apache.rat.report.claim.impl.xml.MockClaimReporter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author hirsch
 * @version 2011-12-06, 23:48
 */
public class MITLicenseTest {
    private MockClaimReporter reporter;
    private Document subject;

    /**
     * To ease testing provide a map with a given license version and the string to test for.
     */
    private static Map<SimplePatternBasedLicense, String> licenseStringMap;

    @BeforeClass
    public static void initLicencesUnderTest() {
        licenseStringMap = new HashMap<SimplePatternBasedLicense, String>();
        licenseStringMap.put(new MITLicense(), MITLicense.AS_IS_LICENSE_LINE);
        licenseStringMap.put(new MITLicense(), MITLicense.MIDDLE_LICENSE_LINE);
        licenseStringMap.put(new MITLicense(), MITLicense.FIRST_LICENSE_LINE);

        assertEquals(3, licenseStringMap.entrySet().size());
    }


    @Before
    public final void initReporter() {
        this.reporter = new MockClaimReporter();
        this.subject = new MockLocation("subject");
    }

    @Test
    public void testNegativeMatches() throws Exception {
        for(Map.Entry<SimplePatternBasedLicense, String> licenceUnderTest : licenseStringMap.entrySet()) {
            assertFalse(licenceUnderTest.getKey().matches("'Behold, Telemachus! (nor fear the sight,)"));
            assertFalse(licenceUnderTest.getKey().match(subject, "'Behold, Telemachus! (nor fear the sight,)"));
        }
    }

    @Test
    public void testPositiveMatches() throws Exception {
        for(Map.Entry<SimplePatternBasedLicense, String> licenceUnderTest : licenseStringMap.entrySet()) {
            assertTrue(licenceUnderTest.getKey().matches("\t" + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches("     " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches(licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches(" * " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches(" // " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches(" /* " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches(" /** " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches("    " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches(" ## " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().matches(" ## " + licenceUnderTest.getValue() + " ##"));
        }
    }

    @Test
    public void testPositiveMatchInDocument() throws Exception {
        for(Map.Entry<SimplePatternBasedLicense, String> licenceUnderTest : licenseStringMap.entrySet()) {
            assertTrue(licenceUnderTest.getKey().match(subject, "\t" + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, "     " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, " * " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, " // " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, " /* " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, " /** " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, "    " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, " ## " + licenceUnderTest.getValue()));
            assertTrue(licenceUnderTest.getKey().match(subject, " ## " + licenceUnderTest.getValue() + " ##"));
        }
    }
    
    
}