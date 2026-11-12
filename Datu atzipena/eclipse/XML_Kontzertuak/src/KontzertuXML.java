import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML fitxategiaren gaineko eragiketa guztiak (DOM erabiliz).
 * Fitxategia DOM zuhaitz gisa kargatzen da, zuhaitza aldatzen da eta
 * (aldaketarik badago) fitxategira gordetzen da. Ez da ArrayList-ik erabiltzen.
 *
 * Fitxategiaren egitura:
 *   <Kontzertuak>
 *     <kontzertua>
 *       <id>1</id> <taldea>..</taldea> <dia>2019-02-05</dia>
 *       <hora>21:00</hora> <taquilla>7.0</taquilla> <precio>10.0</precio>
 *     </kontzertua>
 *     ...
 *   </Kontzertuak>
 *
 * Metodoek salbuespenak botatzen dituzte eta Main-ek kudeatzen ditu.
 */
public class KontzertuXML {

    public static final String ERROA = "Kontzertuak";   // erro-elementuaren izena
    public static final String NODOA = "kontzertua";    // erregistro bakoitzaren nodoa

    // XML-ko etiketen izenak. Fitxategiak dituen izen berberak dira, ez ditut aldatu
    private static final String KODEA = "id";
    private static final String TALDEA = "taldea";
    private static final String EGUNA = "dia";
    private static final String ORDUA = "hora";
    private static final String LEIHATILA = "taquilla";
    private static final String PREZIOA = "precio";

    // zutabeen formatua (zerrendatzeko eta bilatzeko)
    private static final String FORMATUA = "%-5s | %-48s | %-10s | %-5s | %9s | %8s";

    // ===================== DOM LAGUNTZAILEAK =====================

    /** XML fitxategia DOM zuhaitz gisa kargatzen du. */
    static Document kargatu(File f) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder eraikitzailea = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dokumentua = eraikitzailea.parse(f);
        dokumentua.getDocumentElement().normalize();
        // indentatuta dagoenean zuriune-nodoak agertzen dira, kendu egiten ditut
        // gordetzean lerro hutsak ez ateratzeko
        garbitu(dokumentua.getDocumentElement());
        return dokumentua;
    }

    /** Zuriuneak soilik dituzten testu-nodoak ezabatzen ditu (errekurtsiboki). */
    private static void garbitu(Node nodoa) {
        NodeList semeak = nodoa.getChildNodes();
        // atzetik aurrera noa, ezabatzean indizeak ez nahasteko
        for (int i = semeak.getLength() - 1; i >= 0; i--) {
            Node semea = semeak.item(i);
            if (semea.getNodeType() == Node.TEXT_NODE && semea.getTextContent().trim().isEmpty()) {
                nodoa.removeChild(semea);
            } else if (semea.getNodeType() == Node.ELEMENT_NODE) {
                garbitu(semea);
            }
        }
    }

    /** DOM zuhaitza fitxategian gordetzen du (indentatuta). */
    static void gorde(Document dokumentua, File f) throws TransformerException {
        dokumentua.setXmlStandalone(true);
        Transformer transformatzailea = TransformerFactory.newInstance().newTransformer();
        transformatzailea.setOutputProperty(OutputKeys.INDENT, "yes");
        transformatzailea.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformatzailea.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformatzailea.transform(new DOMSource(dokumentua), new StreamResult(f));
    }

    /** Elementu bat sortzen du testuarekin eta gurasoari gehitzen dio. */
    static void sortuElementua(String izena, String balioa, Element gurasoa, Document dokumentua) {
        Element elementua = dokumentua.createElement(izena);
        elementua.appendChild(dokumentua.createTextNode(balioa));
        gurasoa.appendChild(elementua);
    }

    /** Nodo baten semearen testua itzultzen du (hutsik, semea ez badago). */
    static String testua(Element nodoa, String etiketa) {
        NodeList zerrenda = nodoa.getElementsByTagName(etiketa);
        return zerrenda.getLength() == 0 ? "" : zerrenda.item(0).getTextContent().trim();
    }

    /** Nodo baten kodea itzultzen du; -1 zenbaki zuzena ez bada. */
    private static int kodeaLortu(Element nodoa) {
        try {
            return Integer.parseInt(testua(nodoa, KODEA));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Erregistro baten lerro tabulatua. */
    public static String formatua(Element nodoa) {
        return String.format(FORMATUA, testua(nodoa, KODEA), testua(nodoa, TALDEA),
                testua(nodoa, EGUNA), testua(nodoa, ORDUA),
                testua(nodoa, LEIHATILA), testua(nodoa, PREZIOA));
    }

    /** Taularen goiburua (zutabeen izenak). */
    private static void goiburuaInprimatu() {
        String goiburua = String.format(FORMATUA, "KODEA", "TALDEA", "EGUNA", "ORDUA", "LEIHATILA", "PREZIOA");
        System.out.println(goiburua);
        // marra goiburuaren zabalera berekoa
        System.out.println("-".repeat(goiburua.length()));
    }

    // ===================== FITXATEGIAREN EGIAZTAPENA =====================

    /** Fitxategia XML baliozkoa den eta erroa ERROA den egiaztatzen du. */
    public static boolean zuzenaDa(File f) throws IOException, SAXException, ParserConfigurationException {
        return ERROA.equals(kargatu(f).getDocumentElement().getNodeName());
    }

    /** Fitxategi hutsa sortzen du (erro-elementua bakarrik). */
    public static void sortuHutsik(File f) throws ParserConfigurationException, TransformerException {
        Document dokumentua = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        dokumentua.appendChild(dokumentua.createElement(ERROA));
        gorde(dokumentua, f);
    }

    // ===================== ERAGIKETAK =====================

    /** 1. Fitxategia taulako datuekin betetzen du. Aurreko edukia galdu egiten da. */
    public static void bete(File f, Kontzertua[] taula) throws ParserConfigurationException, TransformerException {
        Document dokumentua = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element erroa = dokumentua.createElement(ERROA);
        dokumentua.appendChild(erroa);
        // taulako kontzertu bakoitzeko nodo bat sortzen dut
        for (Kontzertua k : taula) {
            Element nodoa = dokumentua.createElement(NODOA);
            erroa.appendChild(nodoa);
            sortuElementua(KODEA, String.valueOf(k.getKodea()), nodoa, dokumentua);
            sortuElementua(TALDEA, k.getTaldea(), nodoa, dokumentua);
            sortuElementua(EGUNA, k.getEguna().toString(), nodoa, dokumentua);
            sortuElementua(ORDUA, k.getOrdua().toString(), nodoa, dokumentua);
            sortuElementua(LEIHATILA, String.valueOf(k.getLeihatila()), nodoa, dokumentua);
            sortuElementua(PREZIOA, String.valueOf(k.getPrezioa()), nodoa, dokumentua);
        }
        gorde(dokumentua, f);
    }

    /** 2. Erregistro guztiak formatu tabulatuan erakusten ditu. @return erregistro-kopurua */
    public static int zerrendatu(File f) throws IOException, SAXException, ParserConfigurationException {
        NodeList nodoak = kargatu(f).getElementsByTagName(NODOA);
        // goiburua erregistroren bat badago bakarrik
        if (nodoak.getLength() > 0) {
            goiburuaInprimatu();
        }
        for (int i = 0; i < nodoak.getLength(); i++) {
            Node nodoa = nodoak.item(i);
            if (nodoa.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(formatua((Element) nodoa));
            }
        }
        return nodoak.getLength();
    }

    /** 3. Kodea emanda erregistroa bilatzen du. @return nodoa, edo null ez badago */
    public static Element bilatu(File f, int kodea) throws IOException, SAXException, ParserConfigurationException {
        NodeList nodoak = kargatu(f).getElementsByTagName(NODOA);
        Element aurkitua = null;
        // aurkitu arte begiratzen dut
        for (int i = 0; aurkitua == null && i < nodoak.getLength(); i++) {
            Element nodoa = (Element) nodoak.item(i);
            if (kodeaLortu(nodoa) == kodea) {
                aurkitua = nodoa;
            }
        }
        return aurkitua;
    }

    /**
     * 4. Taldearen izenean hitza duten erregistroak erakusten ditu
     * (maiuskulak/minuskulak kontuan hartu gabe). @return aurkitutako kopurua
     */
    public static int zerrendatuHitzarekin(File f, String hitza)
            throws IOException, SAXException, ParserConfigurationException {
        NodeList nodoak = kargatu(f).getElementsByTagName(NODOA);
        int kop = 0;
        for (int i = 0; i < nodoak.getLength(); i++) {
            Element nodoa = (Element) nodoak.item(i);
            // biak minuskulara pasatzen ditut maiuskulak ez kontuan hartzeko
            if (testua(nodoa, TALDEA).toLowerCase().contains(hitza.toLowerCase())) {
                if (kop == 0) {
                    goiburuaInprimatu(); // lehen emaitzaren aurretik bakarrik
                }
                System.out.println(formatua(nodoa));
                kop++;
            }
        }
        return kop;
    }

    /** 5. Kodea duen nodoa ezabatzen du. @return false kodea ez badago */
    public static boolean ezabatu(File f, int kodea)
            throws IOException, SAXException, ParserConfigurationException, TransformerException {
        Document dokumentua = kargatu(f);
        NodeList nodoak = dokumentua.getElementsByTagName(NODOA);
        Element aurkitua = null;
        for (int i = 0; aurkitua == null && i < nodoak.getLength(); i++) {
            Element nodoa = (Element) nodoak.item(i);
            if (kodeaLortu(nodoa) == kodea) {
                aurkitua = nodoa;
            }
        }
        if (aurkitua == null) {
            return false;
        }
        // nodoa bere gurasotik kentzen dut eta zuhaitza gorde
        aurkitua.getParentNode().removeChild(aurkitua);
        gorde(dokumentua, f);
        return true;
    }
}