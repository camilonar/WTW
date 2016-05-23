package epiphany_soft.wtw.Negocio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Camilo on 11/04/2016.
 */
public class Horario {

    private Integer id;
    private String nombreCanal;
    private Integer idPrograma;
    private String Hora;
    private String fecha;

    public Horario(){

    }

    public void setFecha(int dia, int mes, int anio){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        date.setDate(dia);
        date.setMonth(mes-1);
        date.setYear(anio-1900);
        fecha = dateFormat.format(date);
    }

    public String getFecha(){
        return fecha;
    }

    public Horario(Integer id, String nombre, Integer idPrograma,String hora){
        this.id=id;
        this.nombreCanal=nombre;
        this.idPrograma=idPrograma;
          this.Hora=hora;
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

    public String getHora() {return Hora;}

    public void setHora(String hora) {Hora = hora;}

}
