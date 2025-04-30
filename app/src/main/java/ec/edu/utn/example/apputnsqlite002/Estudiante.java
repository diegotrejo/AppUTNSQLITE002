package ec.edu.utn.example.apputnsqlite002;

public class Estudiante {

    public  int Id ;
    public  String Apellidos;
    public  String Nombres;
    public  String NroCedula;
    public  String Direccion;

    public  int CarreraId;

    @Override
    public String toString()
    {
        if ( Id == 0)
            return  Apellidos;
        else
            return Id + " - " + Apellidos + " " + Nombres;
    }
}
