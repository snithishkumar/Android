package co.in.mobilepay.dao.impl;

import android.content.Context;

import java.sql.SQLException;

import co.in.mobilepay.db.DatabaseHelper;

/**
 * Created by Nithish on 24-01-2016.
 */
public abstract class BaseDaoImpl {
    DatabaseHelper databaseHelper = null;


    public BaseDaoImpl(Context context)throws SQLException {
        this.databaseHelper = DatabaseHelper.getInstance(context);
        initDao();

    }

    protected abstract void initDao()throws SQLException;


}
