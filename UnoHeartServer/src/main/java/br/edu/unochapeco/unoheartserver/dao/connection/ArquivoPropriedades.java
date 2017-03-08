/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Properties;

/**
 *
 * @author Eduardo
 */
public class ArquivoPropriedades {

    private final String ARQUIVO_CONFIGURACAO = "unoheart.conf";
    private static Properties props;

    public Properties carregarPropriedades() throws Exception {

        if (props == null) {
            File arquivoConf = null;

            File arquivoUsuario = new File(System.getProperty("user.dir")
                    .concat(File.separator)
                    .concat(ARQUIVO_CONFIGURACAO));
            File arquivoLocal = new File("." + File.separator + ARQUIVO_CONFIGURACAO);

            if (arquivoUsuario.exists()) {
                arquivoConf = arquivoUsuario;
            } else if (arquivoLocal.exists()) {
                arquivoConf = arquivoLocal;
            } else {
//                props = new Properties();
//                props.put("host", "localhost");
//                props.put("port", "5432");
//                props.put("password", Base64.getEncoder().encodeToString("kate24".getBytes()));
//                props.put("database", "unoheart");
//                props.put("user", "postgres");
//                props.store(new FileWriter(arquivoUsuario), null);

                throw new Exception("Arquivo de configuração não localizado em: "
                        .concat(arquivoUsuario.getPath())
                        .concat(" ou ")
                        .concat(arquivoLocal.getPath()));
            }

            //System.out.println("Abrindo arquivo: " + arquivoConf.getPath());

            props = new Properties();

            props.load(new FileInputStream(arquivoConf));
        }

        return props;
    }

}
