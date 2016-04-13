package epiphany_soft.wtw.Negocio;

/**
 * Created by Camilo on 11/04/2016.
 */
public class Horario {
    private int id;
    private String nombreCanal;
    private int idPrograma;

    public Horario(int id, String nombre, int idPrograma){
        this.id=id;
        this.nombreCanal=nombre;
        this.idPrograma=idPrograma;
    }

    public int getId(){
        return id;
    }

    public int getIdPrograma(){
        return idPrograma;
    }

    public String getNombreCanal(){
        return nombreCanal;
    }
    @Override
    public String toString(){
        return nombreCanal;
    }
}
