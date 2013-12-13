package br.ufscar.sin.zelador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import br.ufscar.sin.db.DBHandler;
import br.ufscar.sin.db.DBSingleton;
import br.ufscar.sin.entidades.Ocorrencia;

public class ListaMinhasOcorrenciasActivity extends ListActivity {

	// private ArrayList<String> lista = new ArrayList<String>();
	// private List<Ocorrencia> listaOcorrencias = new ArrayList<Ocorrencia>();

	private Context mContext = ListaMinhasOcorrenciasActivity.this;
	private List<Ocorrencia> listaOcorrencias = new ArrayList<Ocorrencia>();
	private OcorrenciaAdapter mAdapter;
	private final String mTag = ListaMinhasOcorrenciasActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DBHandler dbHandler = DBSingleton.getDBHandler(this);
		setContentView(R.layout.activity_lista_ocorrencia);
		Cursor c = dbHandler.listaOcorrencias();
//		List<Long> idOcorrencias = new ArrayList<Long>();
		Map idOcorrencias = new HashMap<String, Long>();
		if (c != null) {
			int i = 1;
			if (c.moveToFirst()) {
				do {
					Ocorrencia ocorrencia = new Ocorrencia(c);
					idOcorrencias.put("id" + i,ocorrencia.getIdServidor());
					i++;
					//listaOcorrencias.add(ocorrencia);
				} while (c.moveToNext());
			}
		}
		c.close();

		// mAdapter = new ArrayAdapter<Ocorrencia>(this,
		// android.R.layout.simple_list_item_1, listaOcorrencias);

//		Map mapa = new HashMap<String, String>();
//		mapa.put("id", "1");
		JSONObject ocorrenciaJSON = new JSONObject(idOcorrencias);
		TarefaListarMinhasOcorrencias consulta = new TarefaListarMinhasOcorrencias(
				ocorrenciaJSON);
		// consulta.execute("json");
		consulta.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class TarefaListarMinhasOcorrencias extends
			AsyncTask<String, Void, Boolean> {

		List<Ocorrencia> listaOcorrencias = new ArrayList<Ocorrencia>();

		private JSONObject jsonObject;

		private JSONObject resposta;

		private final String mTag = TarefaListarMinhasOcorrencias.class
				.getName();

		public TarefaListarMinhasOcorrencias(JSONObject jsonObject) {
			this.jsonObject = jsonObject;
		}

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(mContext);

			progressDialog.setMessage("Aguarde...");
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			String linha = "";
			Boolean erro = true;
			// "http://localhost:8080/chameozelador/ocorrencia/index?format=";
			// String URL =
			// "http://chameozelador.herokuapp.com/ocorrencia/listaOcorrencias";
//			String URL = "http://192.168.0.182:8080/chameozelador/ocorrencia/listaSuasOcorrencias";
			String URL = "http://chameozelador.herokuapp.com/chameozelador/ocorrencia/listaSuasOcorrencias";
			final String tag = "Your Logcat tag: ";

			// if (params.length > 0) {
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(URL);
				StringEntity se = new StringEntity(jsonObject.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				post.setEntity(se);
				HttpResponse resposta = client.execute(post);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						resposta.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");

				while ((linha = br.readLine()) != null) {
					sb.append(linha);
				}

				br.close();
				// Log.i(tag, sb.toString());
				setOcorrencias(sb.toString());
				// setResposta(sb.toString());
				erro = false;

			} catch (Exception e) {
				Log.e(mTag, "Erro no parsing do JSON!!!", e);
				erro = true;
			}
			// }

			return erro;
		}

		private void setResposta(String jsonString) {
			jsonString.replaceAll(" ", "");
			try {
				// new
				// JSONArray("['mensagem':'Ocorrencia inserida com sucesso!', 'ocorrencia':{\"class\":\"br.ufscar.chameozelador.Ocorrencia\",\"id\":4,\"categoria\":\"value1\",\"denunciante\":\"afff\"}]");
				// {
				// "employees": [
				// { "firstName":"John" , "lastName":"Doe" },
				// { "firstName":"Anna" , "lastName":"Smith" },
				// { "firstName":"Peter" , "lastName":"Jones" }
				// ]
				// }
				resposta = new JSONObject(jsonString);
			} catch (JSONException e) {
				Log.e(mTag, e.getLocalizedMessage());
				resposta = null;
			}

		}

		// public JSONArray getResposta() {
		// return resposta;
		// }

		private void setOcorrencias(String jsonString) {
			try {
				Log.i(mTag, jsonString);
				
				JSONArray listaOcorrenciasJSON = new JSONArray(jsonString);
				JSONObject ocorrenciaJSON;

				for (int i = 0; i < listaOcorrenciasJSON.length(); i++) {
					ocorrenciaJSON = new JSONObject(
							listaOcorrenciasJSON.getString(i));
					Ocorrencia ocorrencia = new Ocorrencia(ocorrenciaJSON);
					listaOcorrencias.add(ocorrencia);
					Log.i(mTag, "Adicionando ocorrencia");
				}
			} catch (JSONException e) {
				Log.e(mTag, "Erro no parsing do JSON", e);
			}

		}

		public List<Ocorrencia> getOcorrencias() {
			return listaOcorrencias;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			// RegistroOcorrenciaSucessoActivity.this
			// .imprimeOcorrencias(listaOcorrencias);
			ListaMinhasOcorrenciasActivity.this
					.trataRespostaInsercao(listaOcorrencias);
		}

	}

	void trataRespostaInsercao(List<Ocorrencia> resposta) {
		// Log.i(mTag, "PASSOU PELA TRATA REPOSTAS");
		listaOcorrencias = resposta;
		/*
		 * if (resposta == null) { Toast.makeText(mContext,
		 * "Nao foi possivel conectar ao servidor. Tente novamente.",
		 * Toast.LENGTH_LONG).show(); return; }
		 * 
		 * JSONObject ocorrenciaJSON; try { Log.i(mTag, resposta.toString()); //
		 * ocorrenciaJSON = resposta.getJSONObject("ocorrencia"); Long
		 * ocorrenciaIDServidor = resposta.getLong("id"); Log.i(mTag,
		 * ocorrenciaIDServidor.toString()); ContentValues contentValues = new
		 * ContentValues(); contentValues.put("id_servidor",
		 * ocorrenciaIDServidor); contentValues.put("status", "Enviada"); //
		 * DBSingleton.getDBHandler(mContext).atualizarOcorrenciaPorId( //
		 * contentValues, mOcorrenciaId); // Toast.makeText(mContext, //
		 * "Ocorrencia cadastrada com sucesso no servidor", //
		 * Toast.LENGTH_LONG).show(); // Intent intentMain = new
		 * Intent(mContext, MainActivity.class); // startActivity(intentMain);
		 * 
		 * } catch (JSONException e) { Log.i(mTag, e.getLocalizedMessage()); //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (Exception e) { Log.i(mTag, e.getLocalizedMessage()); }
		 */

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

//						Toast.makeText(
//								ListaMinhasOcorrenciasActivity.this,
//								String.valueOf(ocorrenciaSelecionada.toString()
//										+ " id: "
//										+ ocorrenciaSelecionada.getId()),
//								Toast.LENGTH_SHORT).show();

						if (ocorrenciaSelecionada.getStatus().equals("Criada")) {
							Intent intentRegistroOcorrenciaSucesso = new Intent(
									ListaMinhasOcorrenciasActivity.this,
									RegistroOcorrenciaSucessoActivity.class);
							intentRegistroOcorrenciaSucesso
									.putExtra(
											RegistroOcorrenciaSucessoActivity.PARAMETRO_ID_OCORRENCIA,
											ocorrenciaSelecionada.getId());
							startActivity(intentRegistroOcorrenciaSucesso);
						} else {
							Intent intentMostraOcorrencia = new Intent(
									ListaMinhasOcorrenciasActivity.this,
									MostraOcorrenciaActivity.class);
							// intentMostraOcorrencia
							// .putExtra(
							// MostraOcorrenciaActivity.PARAMETRO_ID_OCORRENCIA,
							// ocorrenciaSelecionada.getId());
							intentMostraOcorrencia
									.putExtra(
											MostraOcorrenciaActivity.PARAMETRO_ID_OCORRENCIA,
											ocorrenciaSelecionada);
							startActivity(intentMostraOcorrencia);
						}
					}
				});

	}

}
