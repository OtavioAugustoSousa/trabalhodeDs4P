package br.ufc.quixada;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LeitorXMLSAX extends DefaultHandler {

	private String tempVal;
	private Aposentado tempAposentado;
	private List<Aposentado> myAposentados = new ArrayList<Aposentado>();

	public void parseDocument() {
		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			// parse the file and also register this class for call backs
			sp.parse("file.xml", this);
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	// Event Handlers
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// reset
		tempVal = "";
		if (qName.equalsIgnoreCase("Aposentado")) {
			// create a new instance of employee
			tempAposentado = new Aposentado();

		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		tempVal = new String(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("Aposentado")) {
			// add it to the list
			myAposentados.add(tempAposentado);
		} else if (qName.equalsIgnoreCase("Ano")) {
			tempAposentado.setAno(tempVal);
		} else if (qName.equalsIgnoreCase("Ano_de_servico_do_Segurado")) {
			tempAposentado.setAnos_Serviço_Segurado(tempVal);
		} else if (qName.equalsIgnoreCase("Clientela")) {
			tempAposentado.setClientela(tempVal);
		} else if (qName.equalsIgnoreCase("Espécie")) {
			tempAposentado.setEspecie(tempVal);
		} else if (qName.equalsIgnoreCase("Qtd_de_Beneficios_Concedidos")) {
			tempAposentado.setQte_Benefícios_Concedidos(tempVal);
		} else if (qName.equalsIgnoreCase("Sexo")) {
			tempAposentado.setSexo(tempVal);
		} else if (qName
				.equalsIgnoreCase("Valor_do_Beneficio_Concedido_MIL_RS")) {
			tempAposentado.setVlr_Benef_Concedidos(tempVal);
		} else if (qName.equalsIgnoreCase("Valor_do_Beneficio_Concedido")) {
			tempAposentado.setVlr_Benefícios_Concedidos(tempVal);
		}

	}

	public void serializar(String path, Object obj) throws Exception {
		FileOutputStream outFile = new FileOutputStream(path, true);
		ObjectOutputStream s = new ObjectOutputStream(outFile);
		s.writeObject(obj);
		s.close();

	}

	public Object deserializar(String path) throws Exception {
		FileInputStream inFile = new FileInputStream(path);
		ObjectInputStream d = new ObjectInputStream(inFile);
		Object o = d.readObject();
		d.close();
		return o;
	}

	public void salvarObjeto() {

		try {
			serializar("sainda.bin", myAposentados);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public List<Aposentado> recuperarObjeto() throws Exception {

		return (List<Aposentado>) this.deserializar("sainda.bin");
	}

	public static void main(String[] args) {
		LeitorXMLSAX leitorXMLSAX = new LeitorXMLSAX();
		leitorXMLSAX.parseDocument();
		leitorXMLSAX.salvarObjeto();
		
		  try { for(Aposentado ap:leitorXMLSAX.recuperarObjeto()){
		  System.out.println(ap.getEspecie()); } } catch (Exception e) { 
		  e.printStackTrace(); }
		 

	}
}
