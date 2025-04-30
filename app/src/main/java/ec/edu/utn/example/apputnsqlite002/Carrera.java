package ec.edu.utn.example.apputnsqlite002;

public class Carrera {
    public  int Id;
    public  String Nombre;
    public  int FacultadId;

    @Override
    public String toString()
    {
        if ( Id == 0)
            return  Nombre;
        else
            return Id + " - " + Nombre;
    }
}
