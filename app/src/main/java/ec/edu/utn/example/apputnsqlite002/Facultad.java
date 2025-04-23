package ec.edu.utn.example.apputnsqlite002;

public class Facultad {
    public  int Id;
    public  String Nombre;

    @Override
    public String toString()
    {
        if ( Id == 0)
            return  Nombre;
        else
            return Id + " - " + Nombre;
    }
}
