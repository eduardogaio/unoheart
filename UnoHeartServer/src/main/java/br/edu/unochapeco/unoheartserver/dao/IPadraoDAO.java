/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public interface IPadraoDAO<T> {
    
    public T getByCodigo(int codigo) throws Exception;
    public T insert(T value) throws Exception;
    public void update(T value) throws Exception;
    public List<T> getAll() throws Exception;
    
}
