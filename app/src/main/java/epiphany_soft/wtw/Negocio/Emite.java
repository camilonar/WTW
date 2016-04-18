package epiphany_soft.wtw.Negocio;

/**
 * Created by Camilo on 11/04/2016.
 */
public class Emite {

    private int id_Emite;
    private String nombre_canal;
    private int numero_canal;
    private String nombre_Emisora;

    public Emite(int id_Emite, String nombre_canal, int numero_canal, String nombre_Emisora) {
        this.id_Emite = id_Emite;
        this.nombre_canal = nombre_canal;
        this.numero_canal = numero_canal;
        this.nombre_Emisora = nombre_Emisora;
    }

    public int getId_Emite() {
        return id_Emite;
    }

    public String getNombre_canal() {
        return nombre_canal;
    }

    public int getNumero_canal() {
        return numero_canal;
    }

    public String getNombre_Emisora() {
        return nombre_Emisora;
    }








}