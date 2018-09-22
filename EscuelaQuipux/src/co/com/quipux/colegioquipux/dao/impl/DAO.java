/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.quipux.colegioquipux.dao.impl;

import java.util.List;

/**
 *
 * @author hp
 */
public interface DAO <T,K> {
  void insertar(T a) throws DAOException;
    void modificar(T a)throws DAOException;
    void eliminar(T a)throws DAOException;
    List<T> obtenerTodos()throws DAOException;
    T obtener(K id)throws DAOException;
}