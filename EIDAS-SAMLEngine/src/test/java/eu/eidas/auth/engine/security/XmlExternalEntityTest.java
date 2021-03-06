/*
 * Copyright (c) 2017 by European Commission
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/page/eupl-text-11-12
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

package eu.eidas.auth.engine.security;

import eu.eidas.auth.commons.xml.DocumentBuilderFactoryUtil;
import eu.eidas.auth.commons.xml.opensaml.OpenSamlHelper;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Test class for addressing XXE vulnerability/exploitation.
 */
public class XmlExternalEntityTest {

    public final String PATH_LINUX ="/home/rvaz/Desktop/test";//linux path to system file e.g. text file with "important info" content
    public final String PATH_WINDOWS ="file:///c:/temp/test/test.txt";//windows (see AbstractRequestModifier in validator repository) path to system file e.g. text file with "important info" content

    public final String path = PATH_LINUX;//change this accordingly

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public final String XML = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"+
            " <!DOCTYPE foo [  \n"+
            "  <!ELEMENT foo ANY >\n"+
            "  <!ENTITY xxe SYSTEM \"" + path + "\" >]><foo>&xxe;</foo>";


    @BeforeClass
    public static void setUp(){
            OpenSamlHelper.initialize();
    }


    /**
     * Test method to check the possibility of XXE in {@link DocumentBuilderFactoryUtil#marshall(Node, boolean)}.
     * If XXE is possible it should result in an xml in which appears the content of the system file
     * referred by {@link XmlExternalEntityTest#path}
     *
     * It should succeed if XXE is possible.
     *
     * Note This method is not to be run for now in the build but to be used for POC and for that has been set with Ignore.
     *
     * @throws Exception
     *
     */
    @Ignore
    @Test
    public void testMarshallShowXXE() throws Exception {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = df.newDocumentBuilder();
        final InputSource is = new InputSource(new StringReader(XML));
        Document newDocument = builder.parse(is);

        final byte[] marshall = DocumentBuilderFactoryUtil.marshall(newDocument, false);
        final String xmlOut = new String(marshall);

        Assert.assertTrue(xmlOut.contains("important info"));
    }


    /**
     * Test method to check the possibility of XXE while using {@link DocumentBuilderFactoryUtil#parse(String)}.
     *
     * It should fail.
     *
     * Note This method is not to be run for now in the build but to be used for POC and for that has been set with Ignore.
     *
     * @throws Exception
     *
     */
    @Ignore
    @Test
    public void testMarshallPreventXXE() throws Exception {
        exception.expect(SAXParseException.class);
        exception.expectMessage("DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.");
        DocumentBuilderFactoryUtil.parse(XML);
    }
}