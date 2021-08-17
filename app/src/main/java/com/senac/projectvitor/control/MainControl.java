package com.senac.projectvitor.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import com.senac.projectvitor.model.dao.ProdutoDao;
import com.senac.projectvitor.model.dto.DolarDTO;
import com.senac.projectvitor.model.vo.Dolar;
import com.senac.projectvitor.model.vo.Produto;
import com.senac.projectvitor.view.MainActivity;

import java.sql.SQLException;
import java.util.List;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainControl {
    private MainActivity activity;
    // Deixei default 5.0 para não estourar erro
    //TODO Implementar API que busca valor do Dólar (verificar, já tem algo desenvolvido, precisa ajustar)
    private static Double cotacaoDolar = 5.0;
    private ArrayAdapter<Produto> adapterProdutos;
    private List<Produto> listProdutos;

    public Produto produto;

    // DAO's
    private ProdutoDao produtoDao;

    public MainControl(MainActivity activity){
        this.activity = activity;
        produtoDao = new ProdutoDao(this.activity);
        pesquisarCotacao();
        configListViewProdutos();

    }

    private void configListViewProdutos() {

        try {
            listProdutos = produtoDao.listar();
            adapterProdutos = new ArrayAdapter<>(
                    activity,
                    android.R.layout.simple_list_item_1,
                    listProdutos
            );
            activity.getLvProdutos().setAdapter(adapterProdutos);
        } catch (Exception e){
            e.printStackTrace();
        }

        addCliqueLongoLvProdutos();
        addCliqueCurtoLvProdutos();
    }

    private void addCliqueLongoLvProdutos(){
        activity.getLvProdutos().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = adapterProdutos.getItem(position);
                confirmarExclusaoAction(produto);
                return true;
            }
        });
    }

    public void addCliqueCurtoLvProdutos(){
        activity.getLvProdutos().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                produto = adapterProdutos.getItem(position);

                AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
                alerta.setTitle("Produto");
                alerta.setMessage(produto.toString());
                alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produto = null;
                    }
                });
                alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popularFormAction(produto);
                    }
                });
                alerta.show();
            }
        });
    }

    private Produto getProdutoForm(){
        Produto p = new Produto();
        p.setNome(activity.getEditNome().getText().toString());
        p.setValor(activity.getEditValor().getText().toString());
        p.setEstoque(activity.getEditEstoque().getText().toString());
        p.setValorDolar(activity.getEditValor().getText().toString());
        return p;
    }

    private void limparForm(){
        activity.getEditNome().setText("");
        activity.getEditValor().setText("");
        activity.getEditEstoque().setText("");
        activity.getEditNome().requestFocus();
    }


    public void popularFormAction(Produto p){
        activity.getEditNome().setText(p.getNome());
        activity.getEditValor().setText(String.valueOf(p.getValor()));
        activity.getEditEstoque().setText(String.valueOf(p.getEstoque()));

    }

    public void confirmarExclusaoAction(final Produto p){
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluindo produto");
        alerta.setMessage("Deseja realmente excluir o produto " + p.getNome()+"?");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                produto = null;
            }
        });
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    produtoDao.getDao().delete(p);
                    adapterProdutos.remove(p);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                produto = null;
            }
        });
        alerta.show();
    }

    private void cadastrar(){
        Produto produto = getProdutoForm();
        produto.setValorDolar(produto.getValorDolar()/cotacaoDolar);
        //pesquisarCotacao(produto);
        try {
            produtoDao.getDao().create(produto);
            adapterProdutos.add(produto);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void editar(Produto newProduto){
        produto.setNome(newProduto.getNome());
        produto.setValor(newProduto.getValor());
        produto.setEstoque(newProduto.getEstoque());
        produto.setValorDolar(newProduto.getValor() / cotacaoDolar);
        //pesquisarCotacao(produto);

        try {
            adapterProdutos.notifyDataSetChanged();
            produtoDao.getDao().update(produto);
            produto = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarAction(){
        if(produto==null){
            cadastrar();
        } else {
            editar(getProdutoForm());
        }
        limparForm();
    }


    public void pesquisarCotacao(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://economia.awesomeapi.com.br/all/USD-BRL", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                Gson gson = new Gson();
                DolarDTO dolarDTO = gson.fromJson(s, DolarDTO.class);

                Integer posicao = s.indexOf("bid");
                String bidPosicao = s.substring(posicao + 6, posicao + 10);


                //dolar = dolarDTO.getCotacaoDolar();

                //p.setValorDolar(Double.parseDouble(dolar.getCotacaoDolar()) * p.getValor());

                //p.setValorDolar(p.getValor() / Double.parseDouble(bidPosicao) );

                cotacaoDolar = Double.parseDouble(bidPosicao);
            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });


    }

}
