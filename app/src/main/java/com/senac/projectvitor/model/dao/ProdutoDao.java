package com.senac.projectvitor.model.dao;

import android.content.Context;

import com.senac.projectvitor.model.helper.DaoHelper;
import com.senac.projectvitor.model.vo.Produto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao extends DaoHelper<Produto> {

    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }

    public List<Produto> listar(){
        try {
            return getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

/*
    public List<Produto> listaPorNome(String nome){
        try {
            return getDao().queryBuilder().where().eq("nome", nome).query();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
*/
}
