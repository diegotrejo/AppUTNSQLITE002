package ec.edu.utn.example.apputnsqlite002;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlAdmin extends SQLiteOpenHelper {

    public SqlAdmin(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS facultades ( id INT PRIMARY KEY, nombre TEXT(30) )");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS carreras ( id INT PRIMARY KEY, nombre TEXT(30), facultadId INT, CONSTRAINT fk_facultades FOREIGN KEY (facultadId) REFERENCES facultades(id) )");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS estudiantes( id INT PRIMARY KEY, apellidos TEXT(30), nombres TEXT(30), direccion TEXT(30), nroCedula TEXT(10), carreraId INT, CONSTRAINT fk_carreras FOREIGN KEY (carreraId) REFERENCES carreras(id) )" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
