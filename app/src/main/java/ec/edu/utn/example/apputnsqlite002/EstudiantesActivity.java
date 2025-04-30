package ec.edu.utn.example.apputnsqlite002;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EstudiantesActivity extends AppCompatActivity {

    private SqlAdmin sqlDb;
    private Estudiante estudiante;
    private Estudiantes estudiantes;
    private Carreras carreras;
    private Facultades facultades;

    EditText txtEstudianteId, txtEstudianteApellidos, txtEstudianteNombres,
             txtEstudainteNroCedula, txtEstudianteDireccion;
    Spinner  spnEstudianteFacultad, spnEstudianteCarrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_estudiantes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqlDb = new SqlAdmin(this, "utn.db", null, 1);
        estudiantes = new Estudiantes(sqlDb);
        facultades = new Facultades(sqlDb);
        carreras = new Carreras(sqlDb);

        txtEstudianteId = findViewById(R.id.txtEstudianteId);
        txtEstudianteApellidos = findViewById(R.id.txtEstudianteApellidos);
        txtEstudianteNombres = findViewById(R.id.txtEstudianteNombres);
        txtEstudainteNroCedula = findViewById(R.id.txtEstudianteNroCedula);
        txtEstudianteDireccion = findViewById(R.id.txtEstudianteDireccion);
        spnEstudianteFacultad = findViewById(R.id.spnEstudianteFacukltad);
        spnEstudianteCarrera = findViewById(R.id.spnEstudianteCarrera);

        ArrayAdapter<Facultad> adapterFacultades = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                facultades.Read_All("-- Elija Facultad --")
        );
        spnEstudianteFacultad.setAdapter(adapterFacultades);
        spnEstudianteFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Facultad f = getFacultad();
                ArrayAdapter<Carrera> adp = new ArrayAdapter<>(
                        EstudiantesActivity.this,
                        android.R.layout.simple_list_item_1,
                        carreras.Read_All("-- Elija valor --", f.Id)
                );
                spnEstudianteCarrera.setAdapter(adp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private int getId()
    {
        return Integer.parseInt(txtEstudianteId.getText().toString() );
    }
    private void setId(int id)
    {
        if( id == 0)
            txtEstudianteId.setText("");
        else
            txtEstudianteId.setText(""+id);
    }
    private String getApellidos()
    {
        return txtEstudianteApellidos.getText().toString();
    }
    private void setApellidos(String apellidos)
    {
        txtEstudianteApellidos.setText(apellidos);
    }

    private String getNombres()
    {
        return txtEstudianteNombres.getText().toString();
    }
    private void setNombres(String nombres)
    {
        txtEstudianteNombres.setText(nombres);
    }

    private String getNroCedula()
    {
        return txtEstudainteNroCedula.getText().toString();
    }
    private void setNroCedula(String nroCedula)
    {
        txtEstudainteNroCedula.setText(nroCedula);
    }

    private String getDireccion()
    {
        return  txtEstudianteDireccion.getText().toString();
    }
    private void setDireccion(String direccion)
    {
        txtEstudianteDireccion.setText(direccion);
    }

    private Facultad getFacultad()
    {
        return (Facultad) spnEstudianteFacultad.getSelectedItem();
    }
    private Facultad getFacultad(int i)
    {
        return (Facultad) spnEstudianteFacultad.getAdapter().getItem(i);
    }
    private void setFacultad(int id)
    {
        for(int i=0; i < spnEstudianteFacultad.getCount(); i++)
        {
            if( getFacultad(i).Id == id)
            {
                spnEstudianteFacultad.setSelection(i);
                break;
            }
        }
    }

    private Carrera getCarrera()
    {
        return (Carrera) spnEstudianteCarrera.getSelectedItem();
    }
    private Carrera getCarrera(int i)
    {
        return (Carrera) spnEstudianteCarrera.getAdapter().getItem(i);
    }
    private void setCarrera(int id)
    {
        if(spnEstudianteCarrera.getCount() >1) {
            for (int i = 0; i < spnEstudianteCarrera.getCount(); i++) {
                if (getCarrera(i).Id == id) {
                    spnEstudianteCarrera.setSelection(i);
                    break;
                }
            }
        }
    }

    public void cmdEstudianteCreate_onClick(View v)
    {
        estudiante = estudiantes.Create( getId(), getApellidos(), getNombres(), getDireccion(), getNroCedula(), getCarrera().Id);
        if( estudiante != null)
            Toast.makeText(this, "REGISTRO CREADO OK!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "ERROR AL CREAR REGISTRO!", Toast.LENGTH_LONG).show();
    }

    public void cmdEstudianteUpdate_onClick(View v)
    {
        estudiante.Id = getId();
        estudiante.Apellidos = getApellidos();
        estudiante.Nombres = getNombres();
        estudiante.NroCedula = getNroCedula();
        estudiante.Direccion = getDireccion();
        estudiante.CarreraId = getCarrera().Id;

        boolean res = estudiantes.Update(estudiante);
        if(res)
        {
            Toast.makeText(this, "DATOS GUARDADOS OK", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "ERROR AL GUARDAR DATOS", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdEstudianteDelete_onClick(View v)
    {
        boolean res = estudiantes.Delete( getId() );
        if( res)
        {
            setId(0);
            setApellidos("");
            setNombres("");
            setNroCedula("");
            setDireccion("");
            setFacultad(0);
            setCarrera(0);
            Toast.makeText(this, "DATOS BORRADOS OK", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "ERROR AL BORRAR DATOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdEstudainteGetById_onClick(View v)
    {
        estudiante = estudiantes.Read_ById( getId() );
        if(estudiante != null)
        {
            Carrera c = carreras.Read_ById( estudiante.CarreraId );

            setApellidos( estudiante.Apellidos );
            setNombres( estudiante.Nombres );
            setNroCedula( estudiante.NroCedula );
            setDireccion( estudiante.Direccion );
            setFacultad( c.FacultadId );
            setCarrera( estudiante.CarreraId );
        }
        else
        {
            setApellidos("");
            setNombres("");
            setNroCedula("");
            setDireccion("");
            setFacultad(0);
            setCarrera(0);
            Toast.makeText(this, "ERROR: NO HAY DATOS", Toast.LENGTH_SHORT).show();
        }
    }
}