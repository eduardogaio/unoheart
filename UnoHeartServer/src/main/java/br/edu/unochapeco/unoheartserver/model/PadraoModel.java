/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model;

import java.io.InputStream;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Eduardo
 * @param <T>
 */
public interface PadraoModel<T>  {
    
    @JsonIgnore
    public String toJSONString();
    @JsonIgnore
    public T parse(String JSONString) ;
    @JsonIgnore
    public T parse(InputStream in) throws Exception;
    
    
}
