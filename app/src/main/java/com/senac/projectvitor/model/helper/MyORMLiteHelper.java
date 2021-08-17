package com.senac.projectvitor.model.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.senac.projectvitor.model.vo.Produto;

import java.sql.SQLException;

public class MyORMLiteHelper extends OrmLiteSqliteOpenHelper {
    //Config banc
    private static final String DATABASE_NAME = "projetoVitor.db";
    private static final int DATABASE_VERSION = 4;

    public MyORMLiteHelper(Context c){ super(c, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Produto.class);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName());
        }

    }
/*
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1){
        try {
            TableUtils.dropTable(connectionSource, Produto.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Produto.class, true);
            this.onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n"
                    + e.getCause() + "\n"
                    + e.getNextException() + "\n"
                    + e.getClass().getSimpleName()
            );
        }
    }

}
