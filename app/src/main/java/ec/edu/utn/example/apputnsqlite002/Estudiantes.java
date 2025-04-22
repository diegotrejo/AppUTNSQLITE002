package ec.edu.utn.example.apputnsqlite002;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Estudiantes {
    private SqlAdmin sqlDb;

    public Estudiantes(SqlAdmin sqlAdm)
    {
        sqlDb = sqlAdm;
    }

    public Estudiante Create(int id, String apellidos, String nombres, String direccion, String nroCedula, int carreraId)
    {
        ContentValues r = new ContentValues();
        r.put("id", id);
        r.put("apellidos", apellidos);
        r.put("nombres", nombres);
        r.put("direccion", direccion);
        r.put("nroCedula", nroCedula);
        r.put("carreraId", carreraId);

        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        long qty = dbWriter.insert( "estudiantes",null, r);
        dbWriter.close();

        if(qty <=0 )
        {
            Log.e("miApp", "no se pudo insertar el registro");
            return null;
        }
        else
        {
            Estudiante e = new Estudiante();
            e.Id = id;
            e.Apellidos = apellidos;
            e.Nombres = nombres;
            e.Direccion = direccion;
            e.NroCedula = nroCedula;
            e.CarreraId = carreraId;
            return e;
        }
    }

    public Estudiante Read_ById(int id)
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, apellidos, nombres, direccion, nroCedula, carreraId FROM estudiantes WHERE id="+id, null);

        if( c.moveToFirst())
        {
            Estudiante e = new Estudiante();
            e.Id = c.getInt( 0);
            e.Apellidos = c.getString(1);
            e.Nombres = c.getString(2);
            e.Direccion = c.getString(3);
            e.NroCedula = c.getString(4);
            e.CarreraId = c.getInt(5);
            dbReader.close();
            return e;
        }
        else
        {
            Log.i("miApp", "no hay resultados de datos");
            dbReader.close();
            return null;
        }
    }

    public Estudiante[] Read_All()
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, apellidos, nombres, direccion, nroCedula, carreraId FROM estudiantes ORDER BY apellidos, nombres", null);

        if( c.getCount() > 0)
        {
            Estudiante[] res = new Estudiante[c.getCount()];
            int i=0;
            while (c.moveToNext())
            {
                Estudiante e = new Estudiante();
                e.Id = c.getInt(0);
                e.Apellidos = c.getString(1);
                e.Nombres = c.getString(2);
                e.Direccion = c.getString(3);
                e.NroCedula = c.getString(4);
                e.CarreraId = c.getInt(5);
                res[i++] = e;
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

    public Estudiante[] Read_ByCarreraId(int carreraId)
    {
        SQLiteDatabase dbReader = sqlDb.getReadableDatabase();
        Cursor c = dbReader.rawQuery("SELECT id, apellidos, nombres, direccion, nroCedula, carreraId FROM estudiantes WHERE carreraId=" + carreraId + " ORDER BY apellidos, nombres", null);

        if( c.getCount() > 0)
        {
            Estudiante[] res = new Estudiante[c.getCount()];
            int i=0;
            while (c.moveToNext())
            {
                Estudiante e = new Estudiante();
                e.Id = c.getInt(0);
                e.Apellidos = c.getString(1);
                e.Nombres = c.getString(2);
                e.Direccion = c.getString(3);
                e.NroCedula = c.getString(4);
                e.CarreraId = c.getInt(5);
                res[i++] = e;
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

    public boolean Update(Estudiante est)
    {
        ContentValues r = new ContentValues();
        r.put("id", est.Id);
        r.put("apellidos", est.Apellidos);
        r.put("nombres", est.Nombres);
        r.put("direccion", est.Direccion);
        r.put("nroCedula", est.NroCedula);
        r.put("carreraId", est.CarreraId);

        SQLiteDatabase dbWriter = sqlDb.getWritableDatabase();
        int qty = dbWriter.update("estudiantes", r, "id=" + est.Id, null);
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
        int qty = dbWriter.delete("estudiantes", "id="+id, null);
        dbWriter.close();
        if( qty <= 0)
        {
            Log.e("miApp", "no se pudo borrar el registro");
            return false;
        }
        return  true;
    }
}
