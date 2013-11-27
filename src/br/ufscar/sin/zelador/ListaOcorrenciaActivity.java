package br.ufscar.sin.zelador;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.ufscar.sin.db.DBHandler;
import br.ufscar.sin.db.DBSingleton;
import br.ufscar.sin.entidades.Ocorrencia;

public class ListaOcorrenciaActivity extends ListActivity {

	// private ArrayList<String> lista = new ArrayList<String>();
	private List<Ocorrencia> listaOcorrencias = new ArrayList<Ocorrencia>();
	private ArrayAdapter<Ocorrencia> mAdapter;
	// private ListView mlistaOcorrenciaListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DBHandler dbHandler = DBSingleton.getDBHandler(this);
		setContentView(R.layout.activity_lista_ocorrencia);

		// mlistaOcorrenciaListView = (ListView)
		// findViewById(R.id.listaOcorrenciaListView);

		Cursor c = dbHandler.listaOcorrencias();

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					Ocorrencia ocorrencia = new Ocorrencia(c);
					// String categoria = c.getString(0);
					// String detalhamento = c.getString(1);
					// lista.add(categoria + ", " + detalhamento);

					listaOcorrencias.add(ocorrencia);
				} while (c.moveToNext());
			}
		}

		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, lista));
		mAdapter = new ArrayAdapter<Ocorrencia>(this,
				android.R.layout.simple_list_item_1, listaOcorrencias);
		setListAdapter(mAdapter);
		getListView().setTextFilterEnabled(true);
		getListView().setOnItemClickListener(
				new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Toast.makeText(
								ListaOcorrenciaActivity.this,
								String.valueOf(mAdapter.getItem(position)
										.toString() + " id: " + mAdapter.getItem(position).getId()), Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// @Override
	// public void onListItemClick(ListView l, View v, int position, long id) {
	// // Do something when a list item is clicked
	// }

}
