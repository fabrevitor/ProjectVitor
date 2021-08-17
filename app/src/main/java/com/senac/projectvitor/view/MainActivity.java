package com.senac.projectvitor.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.senac.projectvitor.R;
import com.senac.projectvitor.control.MainControl;

public class MainActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editValor;
    private EditText editEstoque;
    private Button btnSalvar;
    private ListView lvProdutos;

    private MainControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        control = new MainControl(this);
    }

    private void initialize(){
        editNome = findViewById(R.id.editNome);
        editValor = findViewById(R.id.editValor);
        editEstoque = findViewById(R.id.editEstoque);
        lvProdutos = findViewById(R.id.lvProdutos);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.salvarAction();
            }
        });
    }

    public EditText getEditNome() {
        return editNome;
    }

    public EditText getEditValor() {
        return editValor;
    }

    public EditText getEditEstoque() {
        return editEstoque;
    }

    public ListView getLvProdutos() {
        return lvProdutos;
    }

}
