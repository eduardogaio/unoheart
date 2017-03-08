package br.edu.unochapeco.unoheartserver.dao.connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import br.edu.unochapeco.unoheartserver.dao.connection.ArquivoPropriedades;
import java.util.Base64;
import java.util.Properties;

/**
 *
 * @author Eduardo Gaio
 */
public class ConnectionFactory {

    private final String dataBaseAddr;

    private final Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public String getDatabaseAddr() {
        return this.dataBaseAddr;
    }

    public ConnectionFactory() throws Exception {

        properties = new ArquivoPropriedades().carregarPropriedades();

        dataBaseAddr = "jdbc:postgresql://"
              + properties.getProperty("host") + ":"
              + Integer.valueOf(properties.getProperty("port")) + "/"
              + properties.getProperty("database");
         
        properties.setProperty("password", new String(Base64.getDecoder().decode(properties.getProperty("password"))));         

        Class.forName("org.postgresql.Driver").newInstance();
    }

}
