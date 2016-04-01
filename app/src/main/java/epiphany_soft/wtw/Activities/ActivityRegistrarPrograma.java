package epiphany_soft.wtw.Activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Negocio.Genero;
import epiphany_soft.wtw.R;
import epiphany_soft.wtw.SpecialFont;

public class ActivityRegistrarPrograma extends AppCompatActivity{
    private EditText name,sinopsis;
    private Spinner spnGenero;
    private RadioButton pel,ser;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reg_pel_ser);
            setTitle("AGREGAR PROGRAMA");
            pel=(RadioButton)findViewById(R.id.but_pel);
            ser=(RadioButton)findViewById(R.id.but_ser);
            name=(EditText)findViewById(R.id.name_programa);
            sinopsis=(EditText)findViewById(R.id.sin_programa);
            crearSpinnerGeneros();
            setSpecialFonts();
        }

        private void setSpecialFonts(){
            TextView tipo=(TextView) findViewById(R.id.lblTipoPrograma);
            tipo.setTypeface(SpecialFont.getInstance(this).getTypeFace());
            TextView nombre=(TextView) findViewById(R.id.lblNombrePrograma);
            nombre.setTypeface(SpecialFont.getInstance(this).getTypeFace());
            TextView sinopsis=(TextView) findViewById(R.id.lblSinopsis);
            sinopsis.setTypeface(SpecialFont.getInstance(this).getTypeFace());
            TextView genero=(TextView) findViewById(R.id.lblGenero);
            genero.setTypeface(SpecialFont.getInstance(this).getTypeFace());
        }

        public void onClickRegistrar(View v)
        {
            String nombre=name.getText().toString();
            String sinopsisS=sinopsis.getText().toString();
            int idGen=((Genero)spnGenero.getSelectedItem()).getId();
            if (pel.isChecked()==true) {
                DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
                boolean success=db.insertarPrograma(idGen,nombre,sinopsisS);
                if (success){
                    int id=db.consultarId_Programa(nombre);
                    success=db.insertarPelicula(id);
                    if (success) createToast("Película creada");
                    else createToast("Ocurrió un error");
                }
                else createToast("Ocurrió un error");
            }
            else if (ser.isChecked()==true) {
                //TODO:realizar el código para insertar una serie
                createToast("Operación no soportada aún");
            }
        }

        public void crearSpinnerGeneros(){
            spnGenero = (Spinner) findViewById(R.id.spnGenero);
            DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
            Cursor c=db.consultarAllGeneros();
            ArrayList<Genero> generos = new ArrayList<Genero>();
            while (c.moveToNext()){
                Genero g=
                        new Genero(c.getInt(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_ID)),
                                c.getString(c.getColumnIndex(DataBaseContract.GeneroContract.COLUMN_NAME_GENERO_NOMBRE)));
                generos.add(g);
            }
            ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,generos);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spnGenero.setAdapter(adapter);
        }

    public void createToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
