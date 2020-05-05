package com.lbd.batis.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lbd.batis.constants.Constant;
import com.lbd.batis.constants.SqlType;
import com.lbd.batis.mapping.MappedStatement;
import com.lbd.batis.session.Configuration;
import com.lbd.batis.utils.CommonUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlBuilder extends BaseBuilder {

    private List<String> xmlPaths = new ArrayList<>();

    public XmlBuilder(Configuration configuration) {
        super(configuration);
    }

    @Override
    public void parse() throws ClassNotFoundException {
        List<String> mapperPaths = configuration.getXmlMapperPaths();

        for (String path : mapperPaths) {
            scanXmls(path);
        }

        for (String xmlPath : xmlPaths) {
            readMapperXml(new File(xmlPath));
        }
    }

    @SuppressWarnings("rawtypes")
    private void readMapperXml(File xmlPath) throws ClassNotFoundException {
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding(Constant.DEFAULT_CHARSET);

        Document document = null;

        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            System.err.println("Document read Error");
            e.printStackTrace();
        }

        Element rootElement = document.getRootElement();

        if (!rootElement.getName().equals(Constant.XML_ROOT_LABEL)) {
            System.err.println("The Root Element of xml is not " + Constant.XML_ROOT_LABEL);
            return;
        }

        String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);

        for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext();) {
            Element element = (Element) iterator.next();

            MappedStatement statement = new MappedStatement();
            statement.setSqlCommandType(SqlType.getByValue(element.getName()));
            statement.setResultType(element.attributeValue(Constant.XML_SELECT_RESULTTYPE));

            String sqlId = namespace + "." + element.attributeValue(Constant.XML_ELEMENT_ID);
            statement.setSqlId(sqlId);
            statement.setNamespace(namespace);
            statement.setSql(CommonUtils.stringTrim(element.getStringValue()));

            configuration.addMappedStatement(sqlId, statement);
        }

        configuration.addMapper(Class.forName(namespace));
    }

    private void scanXmls(String xmlPath) {
        String classPath = XmlBuilder.class.getResource("/").getPath();
        String mainPath = classPath + xmlPath;
        doPath(new File(mainPath));
    }

    private void doPath(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                doPath(f);
            }
        } else if (file.getName().endsWith(".xml")) {
            xmlPaths.add(file.getPath());
        }
    }
}
