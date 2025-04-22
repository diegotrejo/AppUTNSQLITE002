package ec.edu.utn.example.apputnsqlite002;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    EditText txtCarreraId, txtCarreraNombre;

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

        txtCarreraId = findViewById(R.id.txtCarreraId);
        txtCarreraNombre = findViewById(R.id.txtCarreraNombre);
    }

    public void cmdCarreraCreate_onClick(View v){
        carrera = carreras.Create( Integer.parseInt( txtCarreraId.getText().toString() ), txtCarreraNombre.getText().toString(), 1 );
        if( carrera != null)
            Toast.makeText(this, "REGISTRO INSERTADO CORRECTAMENTE ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "ERROR AL INSERTAR REGISTRO !!! ", Toast.LENGTH_LONG).show();
    }

    public void cmdCarreraReadById_onClick(View v)
    {
        carrera = carreras.Read_ById( Integer.parseInt( txtCarreraId.getText().toString()) );
        if( carrera != null)
        {
            txtCarreraNombre.setText( carrera.Nombre );
        }
        else
        {
            txtCarreraNombre.setText( "" );
            Toast.makeText(this, "REGISTRO NO ENCONTRADO !!! ", Toast.LENGTH_LONG).show();
        }
    }

    public void cmdCarreraUpdate_onClick(View v)
    {
        carrera.Id = Integer.parseInt( txtCarreraId.getText().toString() );
        carrera.Nombre = txtCarreraNombre.getText().toString();
        boolean resultado = carreras.Update( carrera );
        if( resultado)
            Toast.makeText(this, "REGISTRO ACTUALZIADO OK ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "ERROR: NO SE PUDO ACTUALIZAR EL REGISTRO!!! ", Toast.LENGTH_LONG).show();
    }

    public void cmdCarreraDelete_onClick(View v)
    {
        boolean resultado = carreras.Delete( Integer.parseInt( txtCarreraId.getText().toString() ));
        if(resultado)
        {
            txtCarreraId.setText("");
            txtCarreraNombre.setText("");
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