package epiphany_soft.wtw.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.DataBase.DataBaseContract;
import epiphany_soft.wtw.Negocio.Genero;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 27/03/2016.
 */
public class ActivityEliminarGenero extends AppCompatActivity{
    private Spinner spnGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_genero);
        setTitle("ELIMINAR GÉNERO");
        crearSpinnerGenerosNoUsados();
        if (spnGenero.getCount()<1){
            Button b = (Button) findViewById(R.id.btnEliminarGenero);
            b.setEnabled(false);
        }
    }

    public void onClickBtnEliminarGenero(View v){
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogoConfirmacion dialogo = new DialogoConfirmacion();
        dialogo.show(fragmentManager, "tagConfirmacion");
    }

    public void createToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void crearSpinnerGenerosNoUsados(){
        spnGenero = (Spinner) findViewById(R.id.spnGeneroElim);
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        Cursor c=db.getGenerosNoUsados();
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

    @SuppressLint("ValidFragment")
    public class DialogoConfirmacion extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());

            builder.setMessage("¿Confirma la acción seleccionada?")
                    .setTitle("Confirmacion")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                        public void onClick(DialogInterface dialog, int id) {
                            int idGen = ((Genero) spnGenero.getSelectedItem()).getId();
                            DataBaseConnection db = new DataBaseConnection(getBaseContext());
                            boolean success = db.eliminarGenero(idGen);
                            if (success) {
                                createToast("Genero eliminado");
                                crearSpinnerGenerosNoUsados();
                                if (spnGenero.getCount() < 1) {
                                    Button b = (Button) findViewById(R.id.btnEliminarGenero);
                                    b.setEnabled(false);
                                }
                            } else createToast("Ocurrió un error");
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            createToast("Acción cancelada");
                            dialog.cancel();
                        }
                    });

            return builder.create();
        }
    }
}
