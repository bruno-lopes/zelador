package br.ufscar.sin.zelador;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import br.ufscar.sin.db.DBHandler;
import br.ufscar.sin.db.DBSingleton;
import br.ufscar.sin.entidades.Ocorrencia;

public class ListaOcorrenciaActivity extends ListActivity {

	// private ArrayList<String> lista = new ArrayList<String>();
	//private List<Ocorrencia> listaOcorrencias = new ArrayList<Ocorrencia>();
	private ArrayList<Ocorrencia> listaOcorrencias = new ArrayList<Ocorrencia>();
	private OcorrenciaAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DBHandler dbHandler = DBSingleton.getDBHandler(this);
		setContentView(R.layout.activity_lista_ocorrencia);
		Cursor c = dbHandler.listaOcorrencias();

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					Ocorrencia ocorrencia = new Ocorrencia(c);
					listaOcorrencias.add(ocorrencia);
				} while (c.moveToNext());
			}
		}
		//mAdapter = new ArrayAdapter<Ocorrencia>(this,
		//		android.R.layout.simple_list_item_1, listaOcorrencias);
		mAdapter = new OcorrenciaAdapter(this, 0, listaOcorrencias);

		setListAdapter(mAdapter);
		getListView().setTextFilterEnabled(true);
		getListView().setOnItemClickListener(
				new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Ocorrencia ocorrenciaSelecionada = mAdapter
								.getItem(position);

						Toast.makeText(
								ListaOcorrenciaActivity.this,
								String.valueOf(ocorrenciaSelecionada.toString()
										+ " id: "
										+ ocorrenciaSelecionada.getId()),
								Toast.LENGTH_SHORT).show();

						if (ocorrenciaSelecionada.getStatus().equals("Criada")) {
							Intent intentRegistroOcorrenciaSucesso = new Intent(
									ListaOcorrenciaActivity.this,
									RegistroOcorrenciaSucessoActivity.class);
							intentRegistroOcorrenciaSucesso
									.putExtra(
											RegistroOcorrenciaSucessoActivity.PARAMETRO_ID_OCORRENCIA,
											ocorrenciaSelecionada.getId());
							startActivity(intentRegistroOcorrenciaSucesso);
						} else if (ocorrenciaSelecionada.getStatus().equals(
								"Enviada")) {

						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
