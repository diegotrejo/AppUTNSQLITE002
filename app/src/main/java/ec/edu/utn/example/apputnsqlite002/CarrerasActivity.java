package ec.edu.utn.example.apputnsqlite002;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CarrerasActivity extends AppCompatActivity {

    private Carrera carrera;
    private SqlAdmin sqlDb;
    private Carreras carreras;
    private Facultades facultades;
    EditText txtCarreraId, txtCarreraNombre;
    Spinner spnCarreraFacultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carreras);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqlDb = new SqlAdmin( this, "utn.db", null, 1);
        carreras = new Carreras(sqlDb);
        facultades = new Facultades(sqlDb);

        ArrayAdapter<Facultad> adapterFacultades = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                facultades.Read_All("-- Elija Facultad --")
        );

        txtCarreraId = findViewById(R.id.txtCarreraId);
        txtCarreraNombre = findViewById(R.id.txtCarreraNombre);
        spnCarreraFacultad = findViewById(R.id.spnCarreraFacultad);
        spnCarreraFacultad.setAdapter(adapterFacultades);
    }

    private int getId()
    {
        return  Integer.parseInt( txtCarreraId.getText().toString() );
    }

    private void setId(int id)
    {
        if (id == 0)
            txtCarreraId.setText("");
        else
            txtCarreraId.setText( "" + id);
    }

    private String getNombre()
    {
        return txtCarreraNombre.getText().toString();
    }

    private void setNombre(String nombre)
    {
        txtCarreraNombre.setText( nombre);
    }

    private Facultad getFacultad()
    {
        return (Facultad) spnCarreraFacultad.getSelectedItem();
    }

    private Facultad getFacultad(int i)
    {
        return  (Facultad) spnCarreraFacultad.getAdapter().getItem(i);
    }

    private void setFacultad( int id)
    {
        for(int i=0; i < spnCarreraFacultad.getAdapter().getCount(); i++)
        {
            if( getFacultad(i).Id == id)
            {
                spnCarreraFacultad.setSelection(i);
                break;
            }
        }
    }

    public void cmdCarreraCreate_onClick(View v){
        carrera = carreras.Create( getId(), getNombre(), getFacultad().Id );
        if( carrera != null)
            Toast.makeText(this, "REGISTRO INSERTADO CORRECTAMENTE ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "ERROR AL INSERTAR REGISTRO !!! ", Toast.LENGTH_LONG).show();
    }

    public void cmdCarreraReadById_onClick(View v)
    {
        carrera = carreras.Read_ById( getId() );
        if( carrera != null)
        {
            setNombre( carrera.Nombre );
            setFacultad( carrera.FacultadId);
        }
        else
        {
            setNombre("");
            setFacultad(0);
            Toast.makeText(this, "REGISTRO NO ENCONTRADO !!! ", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdCarreraUpdate_onClick(View v)
    {
        carrera.Id = getId();
        carrera.Nombre = getNombre();
        carrera.FacultadId = getFacultad().Id;

        boolean resultado = carreras.Update( carrera );
        if( resultado)
            Toast.makeText(this, "REGISTRO ACTUALZIADO OK ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "ERROR: NO SE PUDO ACTUALIZAR EL REGISTRO!!! ", Toast.LENGTH_LONG).show();
    }

    public void cmdCarreraDelete_onClick(View v)
    {
        boolean resultado = carreras.Delete( getId() );
        if(resultado)
        {
            setId(0);
            setNombre("");
            setFacultad(0);
            Toast.makeText(this, "REGISTRO BORRADO OK", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "ERROR: REGISTRO NO BORRADO !!! ", Toast.LENGTH_LONG).show();
    }

    public void cmdCarrerasGoBack_onClick(View v)
    {
        finish();
    }
}