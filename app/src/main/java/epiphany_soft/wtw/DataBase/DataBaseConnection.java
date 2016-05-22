package epiphany_soft.wtw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import epiphany_soft.wtw.DataBase.DataBaseContract.AgendaContract;
import epiphany_soft.wtw.DataBase.DataBaseContract.HorarioContract;
import epiphany_soft.wtw.Negocio.Horario;

import static epiphany_soft.wtw.DataBase.DataBaseContract.CalificacionContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.CanalContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.DiaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.DiaHorarioContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.EmisoraContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.EmiteContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.GeneroContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.PeliculaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.ProgramaContract;
import static epiphany_soft.wtw.DataBase.DataBaseContract.SerieContract;


/**
 * Created by Camilo on 23/03/2016.
 */
public class DataBaseConnection {

    DataBaseHelper miDBHelper;
    DataBaseGenero DBGenero;
    DataBaseAgenda DBAgenda;
    DataBaseProgramaPelicula DBProgramaPelicula;
    DataBaseCalificacion DBCalificacion;
    DataBaseCanal DBCanal;
    DataBaseHorario DBHorario;
    DataBaseSerieCapitulo  DBSerieCapitulo;
    DataBaseUsuarioInicioSesion DBUsuarioInicioSesion;
    DataBaseEmisoraEmite DBEmisoraEmite;


    public DataBaseConnection(Context context){

        miDBHelper = new DataBaseHelper(context);
        DBGenero= new DataBaseGenero(context);
        DBAgenda= new DataBaseAgenda(context);
        DBProgramaPelicula= new DataBaseProgramaPelicula(context);
        DBCalificacion= new DataBaseCalificacion(context);
        DBCanal= new DataBaseCanal(context);
        DBHorario= new DataBaseHorario(context);
        DBSerieCapitulo= new DataBaseSerieCapitulo(context);
        DBUsuarioInicioSesion= new DataBaseUsuarioInicioSesion(context);
        DBEmisoraEmite= new DataBaseEmisoraEmite(context);

    }

    public Cursor consultarAllGeneros(){
        return  DBGenero.consultarAllGeneros();


    }

    public boolean insertarGenero(String nombre){
       return  DBGenero.insertarGenero(nombre);

    }

    public Cursor getGenerosNoUsados(){
       return DBGenero.getGenerosNoUsados();

    }

    public boolean eliminarGenero(int id){
      return  DBGenero.eliminarGenero(id);
    }

    public Cursor consultarPeliculaLikeNombre(String nombre){
       return DBProgramaPelicula.consultarPeliculaLikeNombre(nombre);

    }
    /**@param nombre es el nombre con el que se realizará la búsqueda de la película
    * @return la pelicula cuyo nombre es nombre*/
    public Cursor consultarPeliculaPorNombre(String nombre){
        return DBProgramaPelicula.consultarPeliculaPorNombre(nombre);

    }

    public int consultarId_Programa (String nombre) {
        return DBProgramaPelicula.consultarId_Programa(nombre);

    }

    public boolean insertarPrograma( int id_gen, String nombre, String sinopsis, int anio, String pais) {
        return DBProgramaPelicula.insertarPrograma(id_gen, nombre, sinopsis, anio, pais);

    }

    public boolean insertarPelicula(int  id) {
        return DBProgramaPelicula.insertarPelicula(id);

    }

    public boolean insertarSerie(int  id) {
       return DBSerieCapitulo.insertarSerie(id);
    }

    public boolean actualizarPrograma(int id_gen, String nombre, String sinopsis, int anio, String pais){
        return DBProgramaPelicula.actualizarPrograma(id_gen, nombre, sinopsis, anio, pais);
    }

    public Cursor consultarSerieLikeNombre(String nombre){
     return  DBSerieCapitulo.consultarSerieLikeNombre(nombre);
    }

    public Cursor consultarSeriePorNombre(String nombre){
        return  DBSerieCapitulo.consultarSeriePorNombre(nombre);
    }

    public boolean insertarCapitulo(int id_cap, String nombreCap, int id_temp, int id_ser){
        return  DBSerieCapitulo.insertarCapitulo(id_cap, nombreCap, id_temp, id_ser);
    }


    public Cursor getTemporadasDeSerie(int idSerie){
        return  DBSerieCapitulo.getTemporadasDeSerie(idSerie);
    }


    public boolean actualizarCapitulo(int id_cap_old, int id_cap_new, String nombreCap, int id_temp, int id_ser){
        return  DBSerieCapitulo.actualizarCapitulo(id_cap_old, id_cap_new, nombreCap, id_temp, id_ser);
    }

    public Cursor consultarCapitulosPorTemporada(int idTemporada, int idSerie){
        return  DBSerieCapitulo.consultarCapitulosPorTemporada(idTemporada, idSerie);
    }

    public Cursor consultarInfoCapitulo(int idTemporada, int idSerie, int idCap){
        return  DBSerieCapitulo.consultarInfoCapitulo(idTemporada, idSerie, idCap);
    }

    public boolean insertarTemporada(int id_serie, int  idTemporada){
        return  DBSerieCapitulo.insertarTemporada(id_serie, idTemporada);
    }

    public Cursor consultarSesion(String nombreUsu, String password){
     return DBUsuarioInicioSesion.consultarSesion(nombreUsu, password);
    }

    public boolean AgregarUsuario(String nombre, String contrasenia){
        return DBUsuarioInicioSesion.AgregarUsuario(nombre, contrasenia);
    }


    public boolean actualizarUsuario(String nombre, String contrasenia){
        return DBUsuarioInicioSesion.actualizarUsuario(nombre, contrasenia);
    }

    public Cursor consultarCalificacion(int idUsuario, int idPrograma){
        return DBCalificacion.consultarCalificacion(idUsuario, idPrograma);

    }

    public boolean insertarCalificacion(int idUsuario, int idPrograma, float calificacion){
        return DBCalificacion.insertarCalificacion(idUsuario, idPrograma, calificacion);
    }

    public boolean actualizarCalificacion(int idUsuario, int idPrograma, float calificacion){
        return DBCalificacion.insertarCalificacion(idUsuario, idPrograma, calificacion);
    }

    public Cursor consultarAllEmisoras(){
        return DBEmisoraEmite.consultarAllEmisoras();
    }

    public boolean insertarCanal(String nombreCanal){
        return DBCanal.insertarCanal(nombreCanal);
    }

    public boolean insertarEmite(String nombreCanal,int idEmisor, Integer numCanal){
     return DBEmisoraEmite.insertarEmite(nombreCanal, idEmisor, numCanal);
    }

    public Cursor consultarCanalesDePrograma(int idPrograma){
    return  DBCanal.consultarCanalesDePrograma(idPrograma);
    }

    public boolean insertarHorario(Horario h){
    return  DBHorario.insertarHorario(h);
    }

    public int getHorarioId(int idPrograma, String idCanal){
        return  DBHorario.getHorarioId(idPrograma, idCanal);
    }

    public boolean eliminarHorario(int idHorario){
        return  DBHorario.eliminarHorario(idHorario);

    }

    public Cursor getHorariosPrograma(int idPrograma){
        return  DBHorario.getHorariosPrograma(idPrograma);
    }

    public Cursor consultarCanalLikeNombre(String nombre){
      return  DBCanal.consultarCanalLikeNombre(nombre);
    }


    public boolean eliminarCanal(String  NombreCanal){
        return  DBCanal.eliminarCanal(NombreCanal);
    }

    public boolean actualizarCanal(String  NombreCanal_old, String NombreCanal_new){
        return  DBCanal.actualizarCanal(NombreCanal_old, NombreCanal_new);
    }

    // consultas de emisora -emite


    public Cursor consultarNombreEmisoras(String nombre_canal){
      return DBEmisoraEmite.consultarNombreEmisoras(nombre_canal);
    }

    public Cursor consultarEmisorasDeCanal(String  nombre_canal){
     return DBEmisoraEmite.consultarEmisorasDeCanal(nombre_canal);
    }



    public boolean eliminarEmite(String  nombreCanal, int idEmisora){
      return  DBEmisoraEmite.eliminarEmite(nombreCanal, idEmisora);
    }


    public boolean actualizarEmite(String nombrecanal_old, String nombrecanal_new, int idEmisor, Integer numCanal){
        return  DBEmisoraEmite.actualizarEmite(nombrecanal_old, nombrecanal_new, idEmisor, numCanal);
    }
 // lo de la agenda-favoritos
    public boolean insertarFavorito(int idUsuario, int idPrograma){
    return  DBAgenda.insertarFavorito(idUsuario, idPrograma);
    }

    public boolean eliminarFavorito(int idUsuario, int idPrograma){
        return  DBAgenda.eliminarFavorito(idUsuario, idPrograma);
    }

    public Cursor consultarPeliculasAndFavoritos(String nombre, int idUsuario){
        return  DBAgenda.consultarPeliculasAndFavoritos(nombre, idUsuario);
    }

    public Cursor consultarSeriesAndFavoritos(String nombre, int idUsuario){
        return  DBAgenda.consultarSeriesAndFavoritos(nombre, idUsuario);
    }


    public Cursor consultarPeliculasDeAgenda(String nombre, int idUsuario){
        return  DBAgenda.consultarPeliculasDeAgenda(nombre, idUsuario);
    }

    public Cursor consultarSeriesDeAgenda(String nombre, int idUsuario){
        return  DBAgenda.consultarSeriesDeAgenda(nombre, idUsuario);
    }

    public boolean insertarRelacionDiaHorario(int rel_id, int dia){
      return  DBHorario.insertarRelacionDiaHorario(rel_id, dia);
    }
 // actualizar Horario , por ahora no lo voy a hacer.

    public boolean actualizarHorario(String nombrecanal_old, String nombrecanal_new, int idEmisor, Integer numCanal){
        return  DBHorario.actualizarHorario(nombrecanal_old, nombrecanal_new, idEmisor, numCanal);
    }

    public Cursor getDia(){
        return  DBHorario.getDia();
    }


    public Cursor consultarHorarioDia(int idHorario){
        return  DBHorario.consultarHorarioDia(idHorario);
    }

    public float consultarCalificacionPromedio(int idPrograma){
       return  DBCalificacion.consultarCalificacionPromedio(idPrograma);
    }

    public Cursor consultarPeliculaPorGenero(String genero){
    return  DBGenero.consultarPeliculaPorGenero(genero);
    }

    public Cursor consultarPeliculasAndFavoritosPorGenero(String genero, int idUsuario){

        return  DBGenero.consultarPeliculasAndFavoritosPorGenero(genero, idUsuario);
    }

    public Cursor consultarSeriePorGenero(String genero){
        return  DBGenero.consultarSeriePorGenero(genero);
    }

    public Cursor consultarSeriesAndFavoritosPorGenero(String genero, int idUsuario){
      return  DBGenero.consultarSeriesAndFavoritosPorGenero(genero, idUsuario);
    }

    public Cursor consultarPeliculasDeAgendaPorGenero(String genero, int idUsuario){
       return DBAgenda.consultarPeliculasDeAgendaPorGenero(genero, idUsuario);
    }

    public Cursor consultarSeriesDeAgendaPorGenero(String genero, int idUsuario){
        return DBAgenda.consultarSeriesDeAgendaPorGenero(genero, idUsuario);
    }

    public Cursor consultarPeliculaPorDia(int idDia){
   return  DBProgramaPelicula.consultarPeliculaPorDia(idDia);
    }
    public Cursor consultarSeriePorDia(int idDia){
        return DBSerieCapitulo.consultarSeriePorDia(idDia);

    }

    public Cursor consultarPeliculaPorCanal(String canal){
    return  DBCanal.consultarPeliculaPorCanal(canal);
    }

    public Cursor consultarPeliculasAndFavoritosPorCanal(String canal, int idUsuario){
        return  DBCanal.consultarPeliculasAndFavoritosPorCanal(canal, idUsuario);
    }

    public Cursor consultarSeriePorCanal(String canal){
        return  DBCanal.consultarSeriePorCanal(canal);
    }
    public Cursor consultarSeriesAndFavoritosPorCanal(String canal, int idUsuario){
        return  DBCanal.consultarSeriesAndFavoritosPorCanal(canal, idUsuario);
    }

    public Cursor consultarPeliculasDeAgendaPorCanal(String canal, int idUsuario){
   return  DBAgenda.consultarPeliculasDeAgendaPorCanal(canal, idUsuario);
    }

    public Cursor consultarSeriesDeAgendaPorCanal(String canal, int idUsuario){
    return  DBAgenda.consultarPeliculasDeAgendaPorCanal(canal, idUsuario);
    }

}
