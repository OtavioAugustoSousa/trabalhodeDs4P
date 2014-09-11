package br.ufc.quixada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class GeradorDOM {

	public List<Aposentado> loadData() {
		InputStream is;
		List<Aposentado> myData = new ArrayList<>();
		try {
			is = new FileInputStream("CON06.csv");
			Scanner entrada = new Scanner(is);
			Aposentado aposentado;
			String[] a;
			entrada.nextLine();

			String dados;

			while (entrada.hasNextLine()) {
				dados = entrada.nextLine();
				a = dados.split("\",\"");
				if (a.length >= 8) {

					aposentado = new Aposentado();
					aposentado.setAno(a[0].replace("\"", ""));
					aposentado.setEspecie(a[1].replace("\"", ""));
					aposentado.setAnos_Serviço_Segurado(a[2].replace("\"", ""));
					aposentado.setSexo(a[3].replace("\"", ""));
					aposentado.setClientela(a[4].replace("\"", ""));
					aposentado.setQte_Benefícios_Concedidos(a[5].replace("\"",
							""));
					aposentado.setVlr_Benefícios_Concedidos(a[6].replace("\"",
							""));
					aposentado.setVlr_Benef_Concedidos(a[7].replace("\"", ""));
					myData.add(aposentado);
				}
			}
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myData;
	}

	public Document factoryDocument() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Aposentados");
		doc.appendChild(rootElement);
		return doc;
	}

	public Document preencherDOM(Document doc, Aposentado aposentado) {

		Element elemento = doc.createElement("Aposentado");
		doc.getFirstChild().appendChild(elemento);

		Element anoEle = doc.createElement("Ano");
		Text anoText = doc.createTextNode(aposentado.getAno());
		anoEle.appendChild(anoText);
		elemento.appendChild(anoEle);

		Element anoSEle = doc.createElement("Ano_de_servico_do_Segurado");
		Text anoSText = doc.createTextNode(aposentado
				.getAnos_Serviço_Segurado());
		anoSEle.appendChild(anoSText);
		elemento.appendChild(anoSEle);

		Element cliEle = doc.createElement("Clientela");
		Text cliText = doc.createTextNode(aposentado.getClientela());
		cliEle.appendChild(cliText);
		elemento.appendChild(cliEle);

		Element epEle = doc.createElement("Espécie");
		Text epText = doc.createTextNode(aposentado.getEspecie());
		epEle.appendChild(epText);
		elemento.appendChild(epEle);

		Element qtdEle = doc.createElement("Qtd_de_Beneficios_Concedidos");
		Text qtdText = doc.createTextNode(aposentado
				.getQte_Benefícios_Concedidos());
		qtdEle.appendChild(qtdText);
		elemento.appendChild(qtdEle);

		Element sxEle = doc.createElement("Sexo");
		Text sxText = doc.createTextNode(aposentado.getSexo());
		sxEle.appendChild(sxText);
		elemento.appendChild(sxEle);

		Element valorEle = doc
				.createElement("Valor_do_Beneficio_Concedido_MIL_RS");
		Text valorText = doc.createTextNode(aposentado
				.getVlr_Benef_Concedidos());
		valorEle.appendChild(valorText);
		elemento.appendChild(valorEle);

		Element valorREle = doc.createElement("Valor_do_Beneficio_Concedido");
		Text valorRText = doc.createTextNode(aposentado
				.getVlr_Benefícios_Concedidos());
		valorREle.appendChild(valorRText);
		elemento.appendChild(valorREle);

		return doc;

	}

	public void escreveXMLemArquivo(Document doc) throws TransformerException {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		File file = new File("file.xml");

		StreamResult result = new StreamResult(file);

		transformer.transform(source, result);
		// Output to console for testing
		StreamResult result2 = new StreamResult(System.out);
		transformer.transform(source, result2);

		System.out.println("File saved!");
	}

	public static void main(String argv[]) {
		GeradorDOM ex = new GeradorDOM();
		Document doc = null;
		try {
			doc = ex.factoryDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Aposentado aposentado : ex.loadData()) {

			doc = ex.preencherDOM(doc, aposentado);

		}
		try {
			ex.escreveXMLemArquivo(doc);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
