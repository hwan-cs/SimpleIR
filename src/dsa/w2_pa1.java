package dsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.helper.Validate;
import org.jsoup.select.Elements;

public class w2_pa1 
{
	public static int count = 0;
	public static void main(String[] args) throws ParserConfigurationException, FileNotFoundException, TransformerException 
	{
		
		// TODO Auto-generated method stub
		String [] listhtmml = {"떡.html", "라면.html", "아이스크림.html", "초밥.html","파스타.html"};;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		Document doc = HTMLTOXML(listhtmml);
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("/Users/19juhpark/Desktop/result.xml")));
		
		transformer.transform(source, result);
		
	}
	
	public static Document HTMLTOXML(String [] html) throws ParserConfigurationException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		for (int i =0;i <html.length;i++)
		{
			Element docId = doc.createElement("doc");
			docs.appendChild(docId);
			
			docId.setAttribute("id", Integer.toString(i));
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(html[i].substring(0, html[i].indexOf("."))));
			docId.appendChild(title);
			
			Element body = doc.createElement("body");
			
			String content = "";
			try 
			{
			    BufferedReader in = new BufferedReader(new FileReader(html[i]));
			    String str;
			    while ((str = in.readLine()) != null) 
				{
				    	content +=str;
				}
			    in.close();
			} catch (IOException e) {
			}
			org.jsoup.nodes.Document document = Jsoup.parse(content);
			org.jsoup.nodes.Element elements = document.select("p").first();  
			String str = document.body().text(); 
			body.appendChild(doc.createTextNode(str));
			docId.appendChild(body);
		}
		return doc;
	}
}
