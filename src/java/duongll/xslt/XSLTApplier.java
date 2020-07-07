/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duongll.xslt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author duong
 */
public class XSLTApplier {       
    
    private Transformer getTransformer(String stylesheet) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            StreamSource xsltFile = new StreamSource(stylesheet);
            Templates templates = tf.newTemplates(xsltFile);
            return templates.newTransformer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String applyStylesheet(String stylesheet, InputStream xmlStream) {
        try {
            Transformer transformer = getTransformer(stylesheet);
            StreamSource xmlFile = new StreamSource(xmlStream);
            StreamResult result = new StreamResult(new ByteArrayOutputStream());
            transformer.transform(xmlFile, result);
            return new String(((ByteArrayOutputStream) result.getOutputStream()).toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String applyStylesheet(String stylesheet, String xmlContent) {
        return applyStylesheet(stylesheet, new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));
    }
}