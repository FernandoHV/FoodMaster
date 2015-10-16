package com.foodmaster.fernandoh.foodmaster;



import android.app.Activity;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Util extends Activity {

    public Util(){

    }

    public static final String url_cadastrar  = "http://foodmastercm.pe.hu/webservice/addusuario";
    public static final String url_login  = "http://foodmastercm.pe.hu/webservice/loginUsuario";
    public static final String url_detalheParceiro  = "http://foodmastercm.pe.hu/webservice/parceiroDetalhe";
    public static final String url_todosParceiros  = "http://foodmastercm.pe.hu/webservice/parceiros";



}
