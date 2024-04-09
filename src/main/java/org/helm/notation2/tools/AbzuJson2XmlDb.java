package org.helm.notation2.tools;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AbzuJson2XmlDb {
    public static void main(String[] args) {
        try {
            // Check if filename argument is provided
            if (args.length == 0) {
                System.out.println("Please provide a JSON file as an argument.");
                return;
            }

            // Load JSON input from file
            File inputFile = new File(args[0]);
            String jsonInput = FileUtils.readFileToString(inputFile, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonInput);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.newDocument();
            org.w3c.dom.Element rootElement = doc.createElement("MonomerDB");
            rootElement.setAttribute("xmlns", "lmr");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

                    doc.appendChild(rootElement);
            org.w3c.dom.Element polymerList = doc.createElement("PolymerList");
            rootElement.appendChild(polymerList);
            org.w3c.dom.Element polymer = doc.createElement("Polymer");
            polymer.setAttribute("polymerType", "RNA");
            polymerList.appendChild(polymer);

            HashMap<String, org.w3c.dom.Element> attachmentSet = new HashMap<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject monomer = jsonArray.getJSONObject(i);
                String polymerType = monomer.getString("polymerType");
                if (!Objects.equals(polymerType, "RNA")) {
                    continue;
                }

                org.w3c.dom.Element monomerElement = doc.createElement("Monomer");
                polymer.appendChild(monomerElement);

                monomerElement.appendChild(createElement(doc, "MonomerID", monomer.getString("symbol")));
                monomerElement.appendChild(createElement(doc, "MonomerSmiles", monomer.getString("smiles")));
                String encodedMolFile = MolfileEncoder.encode(monomer.getString("molfile"));
                monomerElement.appendChild(createElement(doc, "MonomerMolFile", encodedMolFile));
                monomerElement.appendChild(createElement(doc, "MonomerType", monomer.getString("monomerType")));
                monomerElement.appendChild(createElement(doc, "PolymerType", monomer.getString("polymerType")));
                monomerElement.appendChild(createElement(doc, "NaturalAnalog", monomer.getString("naturalAnalog")));
                monomerElement.appendChild(createElement(doc, "MonomerName", monomer.getString("name")));

                JSONArray rgroups = monomer.getJSONArray("rgroups");
                org.w3c.dom.Element attachments = doc.createElement("Attachments");
                monomerElement.appendChild(attachments);
                for (int j = 0; j < rgroups.length(); j++) {
                    JSONObject rgroup = rgroups.getJSONObject(j);
                    org.w3c.dom.Element attachment = doc.createElement("Attachment");
                    attachments.appendChild(attachment);

                    attachment.appendChild(createNullableElement(doc, "AttachmentID", rgroup, "alternateId"));
                    attachment.appendChild(createNullableElement(doc, "AttachmentLabel", rgroup, "label"));
                    attachment.appendChild(createNullableElement(doc, "CapGroupName", rgroup, "capGroupName"));
                    attachment.appendChild(createNullableElement(doc, "CapGroupSmiles", rgroup, "capGroupSmiles"));
                    attachmentSet.put(rgroup.getString("alternateId"), (org.w3c.dom.Element) attachment.cloneNode(true));
                }


            }

            // Add AttachmentList
            org.w3c.dom.Element attachmentList = doc.createElement("AttachmentList");
            rootElement.appendChild(attachmentList);

            for (String id : attachmentSet.keySet()) {
                org.w3c.dom.Element attachElement = attachmentSet.get(id);
                attachmentList.appendChild(attachElement);
            }

            // Convert the Document to XML String
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File outputFile = new File("out.xml");
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);
            System.out.println("XML Output saved to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static org.w3c.dom.Element createElement(org.w3c.dom.Document doc, String name, String value) {
        org.w3c.dom.Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }

    private static org.w3c.dom.Element createNullableElement(org.w3c.dom.Document doc, String name, JSONObject json, String key) {
        org.w3c.dom.Element element = doc.createElement(name);
        // Check if the JSONObject has the key
        if (json.has(key)) {
            // If the key exists, get its value and append it
            element.appendChild(doc.createTextNode(json.getString(key)));
        } else {
            // If the key doesn't exist, you can set a default value or leave it empty
            element.appendChild(doc.createTextNode("")); // Here, it leaves the text empty if the key is not found
        }
        return element;
    }
}

