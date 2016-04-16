package epiphany_soft.wtw.Negocio;

/**
 * Created by Camilo on 11/04/2016.
 */
public class Horario {

    private Integer id;

    private String nombreCanal;
    private Integer idPrograma;

    public Horario(Integer id, String nombre, Integer idPrograma){
        this.id=id;
        this.nombreCanal=nombre;
        this.idPrograma=idPrograma;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }
    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Integer getId(){
        return id;
    }

    public Integer getIdPrograma(){
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
