package epiphany_soft.wtw.Negocio;

/**
 * Created by Camilo on 8/04/2016.
 */
public class Sesion {
    private static Sesion ourInstance = new Sesion();
    private String nombreUsuario;
    private int idUsuario;
    private boolean isActiva;//Indica si la sesión está activa

    public static Sesion getInstance() {
        return ourInstance;
    }

    private Sesion() {
        nombreUsuario=null;
        idUsuario=-1;
        isActiva=false;
    }

    public void setNombreUsuario(String nombre){
        this.ourInstance.nombreUsuario=nombre;
    }
    public void setIdUsuario(int id){
        this.ourInstance.idUsuario=id;
    }
    public void setActiva(boolean activa){
        this.ourInstance.isActiva=activa;
    }
}
