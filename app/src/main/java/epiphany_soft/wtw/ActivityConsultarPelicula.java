package epiphany_soft.wtw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
/**
 * Created by Camilo on 22/03/2016.
 */
public class ActivityConsultarPelicula extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_peliculas);
    }

    public void onClickBuscar(View v) {
        EditText txtBuscar = (EditText) findViewById(R.id.txtBuscar);
        String text = txtBuscar.getText().toString();
        DataBaseConnection db=new DataBaseConnection(this.getBaseContext());
        if (text.matches("[0-9]+"))
            txtBuscar.setText(db.pruebaInsercionGenero(Integer.parseInt(text)));

    }
}
