package duongll.utils;

import duongll.wellformer.HTMLWellformer;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLUtils implements Serializable {

    private static final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    public static Document parseHTMLSourceToXMLDOM(String source) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static XPath createXPath() {
        XPath xPath = null;
        try {
            XPathFactory xpf = XPathFactory.newInstance();
            xPath = xpf.newXPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xPath;
    }

    public static <T> T unmarshal(String source, String schemaPath, Class<T> clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            if (!schemaPath.equals("")) {
                Validator validator = (Validator) schemaFactory.newSchema(new File(schemaPath)).newValidator();
                validator.validate(new SAXSource(new InputSource(new StringReader(source))));
            }
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XMLInputFactory xMLInputFactory = XMLInputFactory.newInstance();
            unmarshaller.setProperty(xMLInputFactory.IS_COALESCING, true);
            return (T) unmarshaller.unmarshal(new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static <T> String marshal(T object) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(object, stringWriter);
            return stringWriter.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getHTMLSourceFromUrl(String url) {
        String sourceResult = "";
        try {
            URL homeUrl = new URL(url);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) homeUrl.openConnection();

            httpsURLConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");

            InputStreamReader isr = new InputStreamReader(httpsURLConnection.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            sourceResult = br.lines().collect(Collectors.joining());

            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HTMLWellformer.makeWellformed(sourceResult);
    }

    public static boolean validateXMLUsingDtd(ByteArrayInputStream inputStream) throws Exception {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void error(SAXParseException exception) throws SAXException {
                // do something more useful in each of these handlers
                exception.printStackTrace();
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                exception.printStackTrace();
            }

            @Override
            public void warning(SAXParseException exception) throws SAXException {
                exception.printStackTrace();
            }

        });
        builder.parse(inputStream);
        return true;
    }
    
    
}
