package com.example.registroestudiantesespol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
//Se establece la base de datos SQLite con un archivo java llamado AdminSQLiteOpenHelper
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        //Se crea la tabla estudiantes en la base de datos con los campos necesarios
        sqLiteDatabase.execSQL("create table estudiantes(id_estudiante int primary key,nombres text, apellidos text, edad int, id_carrera text, correo text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }


}
