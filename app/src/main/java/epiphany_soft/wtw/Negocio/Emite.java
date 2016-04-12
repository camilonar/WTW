package epiphany_soft.wtw.Negocio;

/**
 * Created by Camilo on 11/04/2016.
 */
public class Emite {
    private int id;
    private String nombreCanal;
    private Integer numCanal;

    public Emite(int id, String nombre, Integer numCanal){
        this.id=id;
        this.nombreCanal=nombre;
        this.numCanal=numCanal;
    }

    public int getId(){
        return id;
    }
    public String getNombreCanal(){
        return nombreCanal;
    }
    public Integer getNumCanal(){
        return numCanal;
    }
}
