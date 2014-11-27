package com.epam.prokopov.shop.service;

import com.epam.prokopov.shop.model.UserRoles;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

/**
 * Parses security XML files.
 * Expected file structure:
 * <security>
 *  <constraint>
 *      <url-pattern>{pattern}</url-pattern>
 *      <role>{role}</role >
 *      <role>{role}</role >
 *  </constraint>
 * </security>
 *
 * @throws RuntimeException, if structure is incorrect or file is invalid.
 */
public class DOMParser {

    private File securityXML;

    public DOMParser(String path) {
        this.securityXML = new File(path);
    }

    public Map<String, List<UserRoles>> parse() {
        Map<String, List<UserRoles>> res = new HashMap<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(securityXML);
            doc.getDocumentElement().normalize();

            NodeList root = doc.getElementsByTagName("security");

            for (int i = 0; i < root.getLength(); i++) {
                Node node = root.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element constraintElement = (Element) node;
                    String urlPattern = constraintElement.getElementsByTagName("url-pattern")
                            .item(0).getTextContent();

                    List<UserRoles> roles = new LinkedList<>();
                    NodeList roleNodeList = constraintElement.getElementsByTagName("role");

                    for (int j = 0; j < roleNodeList.getLength(); j++) {
                        Element roleElement = (Element) roleNodeList.item(j);
                        UserRoles role = UserRoles.valueOf(roleElement.getTextContent());
                        roles.add(role);
                    }
                    res.put(urlPattern, roles);
                }
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
        return res;
    }

}
