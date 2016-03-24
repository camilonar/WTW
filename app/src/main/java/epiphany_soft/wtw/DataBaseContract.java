package epiphany_soft.wtw;

import android.provider.BaseColumns;

/**
 * Created by Camilo on 23/03/2016.
 */
/*Clase contrato que guarda las definiciones de la base de datos*/
public final class DataBaseContract {
    public DataBaseContract(){}

    public static abstract class ProgramaContract implements BaseColumns {
        public static final String TABLE_NAME = "programa";
        public static final String COLUMN_NAME_PELICULA_ID = "pro_identificador";
        public static final String COLUMN_NAME_GENERO_ID = "gen_identificador";
    }

    public static abstract class PeliculaContract implements BaseColumns {
        public static final String TABLE_NAME = "pelicula";
        public static final String COLUMN_NAME_PELICULA_ID = "pro_identificador";
        public static final String COLUMN_NAME_PELICULA_NOMBRE = "pro_nombre";
        public static final String COLUMN_NAME_PELICULA_SINOPSIS = "pro_sinopsis";
    }

    public static abstract class SerieContract implements BaseColumns {
        public static final String TABLE_NAME = "serie";
        public static final String COLUMN_NAME_SERIE_ID = "pro_identificador";
        public static final String COLUMN_NAME_SERIE_NOMBRE = "pro_nombre";
        public static final String COLUMN_NAME_SERIE_SINOPSIS = "pro_sinopsis";
    }

    public static abstract class AgendaContract implements BaseColumns {
        public static final String TABLE_NAME = "agendar";
        public static final String COLUMN_NAME_USUARIO_ID = "usu_identificador";
        public static final String COLUMN_NAME_PROGRAMA_ID = "pro_identificador";
    }

    public static abstract class RecordatorioContract implements BaseColumns {
        public static final String TABLE_NAME = "recordatorio";
        public static final String COLUMN_NAME_USUARIO_ID = "usu_identificador";
        public static final String COLUMN_NAME_PROGRAMA_ID = "pro_identificador";
        public static final String COLUMN_NAME_RECORDATORIO_ID = "rec_identiicador";
        public static final String COLUMN_NAME_RECORDATORIO_FECHA = "rec_fecha";
        public static final String COLUMN_NAME_RECORDATORIO_HORA = "rec_hora";
    }

    public static abstract class CalificacionContract implements BaseColumns {
        public static final String TABLE_NAME = "calificacion";
        public static final String COLUMN_NAME_USUARIO_ID = "usu_identificador";
        public static final String COLUMN_NAME_PROGRAMA_ID = "pro_identificador";
        public static final String COLUMN_NAME_VALOR_CALIFICACION = "cal_valor";
    }

    public static abstract class CanalContract implements BaseColumns {
        public static final String TABLE_NAME = "canal";
        public static final String COLUMN_NAME_CANAL_ID = "can_numero";
        public static final String COLUMN_NAME_CANAL_NOMBRE = "can_nombre";
    }

    public static abstract class CapituloContract implements BaseColumns {
        public static final String TABLE_NAME = "capitulo";
        public static final String COLUMN_NAME_PROGRAMA_ID = "pro_identificador";
        public static final String COLUMN_NAME_TEMPORADA_ID = "tem_identificador";
        public static final String COLUMN_NAME_CAPITULO_ID = "cap_identificador";
        public static final String COLUMN_NAME_CAPITULO_NOMBRE = "cap_nombre";
    }

    public static abstract class GeneroContract implements BaseColumns {
        public static final String TABLE_NAME = "genero";
        public static final String COLUMN_NAME_GENERO_ID = "gen_identificador";
        public static final String COLUMN_NAME_GENERO_NOMBRE = "gen_nombre";
    }

    public static abstract class CanalProgramaContract implements BaseColumns {
        public static final String TABLE_NAME = "relacioncanalprograma1";
        public static final String COLUMN_NAME_RELACION_ID = "rel_identificador";
        public static final String COLUMN_NAME_PROGRAMA_ID = "pro_identificador";
        public static final String COLUMN_NAME_CANAL_ID = "can_numero";
        public static final String COLUMN_NAME_RELACION_FECHA = "rel_fecha";
        public static final String COLUMN_NAME_RELACION_HORA = "rel_hora";
    }

    public static abstract class TemporadaContract implements BaseColumns {
        public static final String TABLE_NAME = "temporada";
        public static final String COLUMN_NAME_PROGRAMA_ID = "pro_identificador";
        public static final String COLUMN_NAME_TEMPORADA_ID = "tem_identificador";
        public static final String COLUMN_NAME_TEMPORADA_NUMERO = "tem_nombre";
    }

    public static abstract class UsuarioContract implements BaseColumns {
        public static final String TABLE_NAME = "usuario";
        public static final String COLUMN_NAME_USUARIO_ID = "usu_identificador";
        public static final String COLUMN_NAME_USUARIO_NOMBRE = "usu_nombre";
        public static final String COLUMN_NAME_USUARIO_PASSWORD = "usu_contrasenia";
    }
}
