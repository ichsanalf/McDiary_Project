package com.tubes.mcdiary;

import android.content.Context;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//store user data ke XML file
public class Database implements IDatabase
{
    private String fpath;
    private Context cont;

    public Database(Context cont)
    {
        this.cont = cont;
        fpath = cont.getFilesDir().getPath() + "/mcdiary.xml";
        init();
    }

    // fungsi membuat XML file dengan root element jika data tidak ada/valid
    private void init()
    {
        try
        {
            File fl = new File(fpath);
            if(!fl.exists())
            {
                fl.createNewFile();

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("records");
                doc.appendChild(rootElement);

                saveXMLToFile(doc);
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    // fungsi untuk menyimpan dokumen ke XML file
    /*  throws :
        throws digunakan pada waktu mendeklarasikan suatu method untuk memberitahu
        bahwa method yang bersangkutan dapat melempar eksepsi dengan tipe yang
        dideklarasikan oleh keyword throws tersebut.
     */
    private void saveXMLToFile(Document doc) throws Exception
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fpath));
        transformer.transform(source, result);
    }

    //return rooot document dari XML user data file
    private Document getXMLDoc() throws Exception
    {
        File fXmlFile = new File(fpath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        return dBuilder.parse(fXmlFile);
    }

    // menyimpan data ke dalam file
    @Override
    public void addData(UserData data) throws Exception
    {
        try
        {
            Document doc = getXMLDoc();
            Element rootElement = doc.getDocumentElement();

            Attr attr = doc.createAttribute("date");
            attr.setValue(data.getId());

            Element record = doc.createElement("record");

            record.setAttributeNode(attr);
            Element text = (Element)record.appendChild(doc.createElement("text"));
            text.appendChild(doc.createTextNode(data.getText()));

            rootElement.appendChild(record);

            saveXMLToFile(doc);
        }
        catch(Exception e)
        {
            System.err.println("Unable to add data" + e.getMessage());
        }
    }

    // menghapus data dari file berdasarkan ID data yaitu data.getID()
    @Override
    public void removeData(UserData data) throws Exception
    {
        Document doc = getXMLDoc();
        Element rootElement = doc.getDocumentElement();
        NodeList list = doc.getElementsByTagName("record");

        for(int i = 0; i < list.getLength(); i++)
        {
            Node node = list.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if(data.getId().equals(element.getAttribute("date")))
                {
                    rootElement.removeChild(element);
                    saveXMLToFile(doc);
                    break;
                }
            }
            else
            {
                System.err.println("Unable to delete data");
            }
        }
    }

    // mengedit data (berdasarkan id) text dalam file dengan text yang baru
    @Override
    public void editData(UserData data) throws Exception
    {
        removeData(data);
        addData(data);
    }

    // men-load ArrayList dengan data dari file dan return arrayList tsb (it).
    @Override
    public ArrayList<UserData> getContent() throws Exception
    {
        ArrayList<UserData> it = new ArrayList<UserData>();

        Document doc = getXMLDoc();

        NodeList list = doc.getElementsByTagName("record");
        for(int i = 0; i < list.getLength(); i++)
        {
            Node node = list.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                String date = element.getAttribute("date");
                String text = element.getElementsByTagName("text").item(0).getTextContent();
                it.add(new UserData(text, date));
            }
            else
            {
                System.err.println("Unable to load data");
            }
        }

        return it;
    }
}