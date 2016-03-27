package epiphany_soft.wtw.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import epiphany_soft.wtw.DataBase.DataBaseConnection;
import epiphany_soft.wtw.R;

/**
 * Created by Camilo on 27/03/2016.
 */
public class ActivityAgregarGenero extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_genero);
    }

    public void onClickAgregarGenero(View v){
        EditText txtAgregar = (EditText) findViewById(R.id.txtAgregarGenero);
        String text = txtAgregar.getText().toString();
        //TODO: Revisar si es mejor usar v.getContext()
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        boolean success=db.insertarGenero(text);
        if (success) createToast("Genero creado");
        else createToast("Ocurrió un error");
    }

    public void createToast(String message){
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
