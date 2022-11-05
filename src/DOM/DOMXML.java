package DOM;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class DOMXML {

    static final String CLASS_NAME = DOMXML.class.getSimpleName();
    static final Logger LOG = Logger.getLogger(CLASS_NAME);

    public static void main(String argv[]) {
        if (argv.length != 1) {
            LOG.severe("Falta archivo XML como argumento.");
            System.exit(1);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            //dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            //dbf.setFeature(XMLConstants.ACCESS_EXTERNAL_DTD, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(argv[0]));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();

            LOG.info("Root: " + root.getNodeName());

            NodeList nList = root.getChildNodes();
            LOG.info("Child nodes: " + nList.getLength());
            visitChildNodes(nList);

        } catch (ParserConfigurationException e) {
            LOG.severe(e.getMessage());
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        } catch (SAXException e) {
            LOG.severe(e.getMessage());
        }
    }

    // Esta función se llama recursivamente
    private static void visitChildNodes(NodeList nList) {
        int x1 = 0; //posición x1
        int y1 = 0; //posición y1
        int x2 = 0; //posición x1
        int y2 = 0; //posición y1
        int height = 0; //alto
        int width = 0; //ancho
        double dist = 0; //distancia entre x y
        final double pi = 3.14159; //constante pi
        int r = 0; //radio del circulo
        double area = 0, perimetro = 0;
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("Node Name = " + node.getNodeName() //Nombre del nodo
                        + "; Value = " + node.getTextContent());
                // Comprobar todos los atributos
                if (node.hasAttributes()) {
                    // obtener nombres y valores de atributos
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node tempNode = nodeMap.item(i);
                        System.out.println("Attr name : " + tempNode.getNodeName() //atributo
                                + "; Value = " + tempNode.getNodeValue()/*valor del atributo*/);
                        if (node.getNodeName() == "rect") { //guardar los valores de los atributos si corresponde a una recta
                            if (tempNode.getNodeName() == "height") //Guardar altura
                                height = Integer.parseInt(tempNode.getNodeValue());
                            if (tempNode.getNodeName() == "width") //guardar ancho
                                width = Integer.parseInt(tempNode.getNodeValue());
                        } else if (node.getNodeName() == "circle") { //guardar atributos si corresponde a un circulo
                            if (tempNode.getNodeName() == "r") //Guardar radio
                                r = Integer.parseInt(tempNode.getNodeValue());
                        } else if (node.getNodeName() == "line") { //guardar valores de la linea
                            if (tempNode.getNodeName() == "x1") //guardar x1
                                x1 = Integer.parseInt(tempNode.getNodeValue());
                            if (tempNode.getNodeName() == "x2") //guardar x2
                                x2 = Integer.parseInt(tempNode.getNodeValue());
                            if (tempNode.getNodeName() == "y1") //guardar y1
                                y1 = Integer.parseInt(tempNode.getNodeValue());
                            if (tempNode.getNodeName() == "y2") //guardar y2
                                y2 = Integer.parseInt(tempNode.getNodeValue());
                        }
                    }
                    if (node.hasChildNodes()) {
                        // Tenemos más hijos; Visitémoslos también.
                        visitChildNodes(node.getChildNodes());

                    }
                }
                if (node.getNodeName() == "rect") { //calcular valores recta
                    perimetro = (2 * width) + (2 * height);
                    System.out.println("->Perimetro: " + perimetro);
                    area = width * height;
                    System.out.println("->Area: " + area);

                } else if (node.getNodeName() == "circle") {
                    perimetro = 2 * pi * r;
                    System.out.println("->Perimetro: " + perimetro);
                    area = pi * Math.pow(r, 2);
                    System.out.println("->Area: " + area);
                } else if (node.getNodeName() == "line") {
                    dist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                    System.out.println("->Distancia: " + dist);
                }
            }
        }
    }
}
