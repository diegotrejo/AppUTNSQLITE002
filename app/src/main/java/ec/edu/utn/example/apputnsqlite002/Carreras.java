package ec.edu.utn.example.apputnsqlite002;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Carreras {
    private SqlAdmin sqlDb;

    public Carreras( SqlAdmin sqlAdm)
    {
        sqlDb = sqlAdm;
    }

    public Carrera Create( int id, String nombre, int facultadId)
    {
        ContentValues r = new ContentValues();
        r.put("id", id);
        r.put("nombre", nombre);
        r.put("facultadId", facultadId);

        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        long qty = dbWriter.insert( "carreras",null, r);
        dbWriter.close();

        if(qty <=0 )
        {
            Log.e("miApp", "no se pudo insertar el registro");
            return null;
        }
        else
        {
            Carrera c = new Carrera();
            c.Id = id;
            c.Nombre = nombre;
            c.FacultadId = facultadId;
            return  c;
        }
    }

    public Carrera Read_ById(int id)
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, nombre, facultadId FROM carreras WHERE id="+id, null);

        if( c.moveToFirst())
        {
            Carrera carr = new Carrera();
            carr.Id = c.getInt( 0);
            carr.Nombre = c.getString(1);
            carr.FacultadId = c.getInt(2);
            dbReader.close();
            return carr;
        }
        else
        {
            Log.i("miApp", "no hay resultados de datos");
            dbReader.close();
            return null;
        }
    }

    public  List<Carrera> Read_All(String item0, int facultadId)
    {
        Carrera c = new Carrera();
        c.Id = 0;
        c.Nombre = item0;
        List<Carrera> res = new ArrayList<>();
        res.add(c);
        res.addAll(this.Read_ByFacultadId(facultadId));
        return  res;
    }

    public List<Carrera> Read_All()
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, nombre, facultadId FROM carreras ORDER BY nombre", null);
        List<Carrera> res = new ArrayList<>();

        if( c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                Carrera carr = new Carrera();
                carr.Id = c.getInt(0);
                carr.Nombre = c.getString(1);
                carr.FacultadId = c.getInt(2);
                res.add(carr);
            }
        }
        else
        {
            Log.i("miApp", "no hay resultados de datos");
        }

        dbReader.close();
        return res;
    }

    public List<Carrera> Read_ByFacultadId(int facultadId)
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, nombre, facultadId FROM carreras WHERE facultadId=" + facultadId + " ORDER BY nombre", null);
        List<Carrera> res = new ArrayList<>();

        if( c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                Carrera carr = new Carrera();
                carr.Id = c.getInt(0);
                carr.Nombre = c.getString(1);
                carr.FacultadId = c.getInt(2);
                res.add(carr);
            }
        }
        else
        {
            Log.i("miApp", "no hay resultados de datos");
        }

        dbReader.close();
        return res;
    }

    public boolean Update( Carrera carr)
    {
        ContentValues r = new ContentValues();
        r.put("id", carr.Id);
        r.put("nombre", carr.Nombre);
        r.put("facultadId", carr.FacultadId);

        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        int qty = dbWriter.update("carreras", r, "id=" + carr.Id, null);
        dbWriter.close();

        if (qty <= 0)
        {
            Log.e("miApp", "no se pudo actualizar el registro");
            return false;
        }
        return true;
    }

    public  boolean Delete(int id)
    {
        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        int qty = dbWriter.delete("carreras", "id="+id, null);
        dbWriter.close();
        if( qty <= 0)
        {
            Log.e("miApp", "no se pudo borrar el registro");
            return false;
        }
        return  true;
    }
}
