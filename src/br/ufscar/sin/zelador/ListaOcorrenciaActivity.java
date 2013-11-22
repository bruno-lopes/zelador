package br.ufscar.sin.zelador;

import java.util.ArrayList;

import br.ufscar.sin.db.DBHandler;
import br.ufscar.sin.db.DBSingleton;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.app.ListActivity;

public class ListaOcorrenciaActivity extends ListActivity {

private ArrayList<String> lista = new ArrayList<String>();
//private ListView mlistaOcorrenciaListView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBHandler dbHandler = DBSingleton.getDBHandler(this);
		setContentView(R.layout.activity_lista_ocorrencia);

		//mlistaOcorrenciaListView = (ListView) findViewById(R.id.listaOcorrenciaListView);
		
        Cursor c = dbHandler.listaOcorrencias();

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String categoria = c.getString(0);
                    String detalhamento = c.getString(1);
                    lista.add(categoria + ", " + detalhamento);
                }while (c.moveToNext());
            } 
        }         
		
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lista));
        getListView().setTextFilterEnabled(true);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
