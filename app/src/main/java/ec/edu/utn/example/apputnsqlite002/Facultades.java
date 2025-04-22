package ec.edu.utn.example.apputnsqlite002;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Facultades {
    private SqlAdmin sqlDb;

    public  Facultades( SqlAdmin sqlAdmin)
    {
        sqlDb = sqlAdmin;
    }

    public  Facultad Create( int id, String nombre)
    {
        ContentValues r = new ContentValues();
        r.put("id", id);
        r.put("nombre", nombre);

        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        long qty = dbWriter.insert( "facultades",null, r);
        dbWriter.close();

        if(qty <=0 )
        {
            Log.e("miApp", "no se pudo insertar el registro");
            return null;
        }
        else
        {
            Facultad f = new Facultad();
            f.Id = id;
            f.Nombre = nombre;
            return  f;
        }
    }

    public  Facultad[] Read_All()
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, nombre FROM facultades ORDER BY nombre", null);

        if( c.getCount() > 0)
        {
            Facultad[] res = new Facultad[c.getCount()];
            int i=0;
            while (c.moveToNext())
            {
                Facultad f = new Facultad();
                f.Id = c.getInt(0);
                f.Nombre = c.getString(1);
                res[i++] = f;
            }
            dbReader.close();
            return res;
        }
        else
        {
            Log.i("miApp", "no hay resultados de datos");
            dbReader.close();
            return null;
        }
    }

    public  Facultad Read_ById(int id)
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, nombre FROM facultades WHERE id="+id, null);

        if( c.moveToFirst())
        {
            Facultad f = new Facultad();
            f.Id = c.getInt( 0);
            f.Nombre = c.getString(1);
            dbReader.close();
            return f;
        }
        else
        {
            Log.i("miApp", "no hay resultados de datos");
            dbReader.close();
            return null;
        }
    }

    public boolean Update(int id, String nombre)
    {
        ContentValues r = new ContentValues();
        r.put("id", id);
        r.put("nombre", nombre);

        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        int qty = dbWriter.update("facultades", r, "id=" + id, null);
        dbWriter.close();

        if (qty <= 0)
        {
            Log.e("miApp", "no se pudo actualizar el registro");
            return false;
        }
            return true;
    }

    public boolean Delete(int id)
    {
        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        int qty = dbWriter.delete("facultades", "id="+id, null);
        dbWriter.close();
        if( qty <= 0)
        {
            Log.e("miApp", "no se pudo borrar el registro");
            return false;
        }
            return  true;
    }
}
