package epiphany_soft.wtw.Negocio;


public class Capitulo {
    private int numeroCapitilo;
    private String nombreCapitilo;
    private boolean capitulo_visto;





    public Capitulo(int numeroCapitilo, String nombreCapitilo) {
        this.numeroCapitilo = numeroCapitilo;
        this.nombreCapitilo = nombreCapitilo;

        capitulo_visto=false;
    }

    public int getNumeroCapitilo() {
        return numeroCapitilo;
    }

    public void setNumeroCapitilo(int numeroCapitilo) {
        this.numeroCapitilo = numeroCapitilo;
    }

    public String getNombreCapitilo() {
        return nombreCapitilo;
    }

    public void setNombreCapitilo(String nombreCapitilo) {
        this.nombreCapitilo = nombreCapitilo;
    }


    public boolean isCapitulo_visto() {
        return capitulo_visto;
    }

    public void setCapitulo_visto(boolean capitulo_visto) {
        this.capitulo_visto = capitulo_visto;
    }


}
