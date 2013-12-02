package br.ufscar.sin.zelador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import br.ufscar.sin.entidades.Ocorrencia;

public class RegistroOcorrenciaSucessoActivity extends Activity {

	private Button mVoltarInicioButton;
	private Button mTirarFotoButton;
	private Button mEnviarFotoButton;

	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private static final String ESTADO_FOTO = "Fotografia";
	private static final String ESTADO_LOCALIZACAO = "Localizacao";
	private static final String ESTADO_OCORRENCIA_ID = "OcorrenciaID";

	public static final String PARAMETRO_ID_OCORRENCIA = "Id da Ocorrencias";

	private String nomeArquivoFoto;
	private Bitmap mImageBitmap;
	private ImageView mImageView;
	private Long mOcorrenciaId;

	private Location mLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mImageBitmap = savedInstanceState.getParcelable(ESTADO_FOTO);
			mLocation = savedInstanceState.getParcelable(ESTADO_LOCALIZACAO);
			if (mLocation != null) {
				Log.i(RegistroOcorrenciaSucessoActivity.class.toString(),
						"Latitude: " + mLocation.getLatitude() + " Longitude: "
								+ mLocation.getLongitude());
			}
			// mOcorrenciaId = savedInstanceState.getLong(ESTADO_OCORRENCIA_ID);
			// if (mOcorrenciaId == null) {
			// Toast.makeText(RegistroOcorrenciaSucessoActivity.this,
			// "Ocorrencia n�o informada", Toast.LENGTH_LONG).show();
			// Intent voltarInicioIntent = new Intent(
			// RegistroOcorrenciaSucessoActivity.this,
			// MainActivity.class);
			// startActivity(voltarInicioIntent);
			// }
		}

		setContentView(R.layout.activity_registro_ocorrencia_sucesso);
		mImageView = (ImageView) findViewById(R.id.fotoOcorrenciaImageView);
		if (mImageBitmap != null) {
			mImageView.setImageBitmap(mImageBitmap);
		}

		mVoltarInicioButton = (Button) findViewById(R.id.voltarInicioButton);

		mVoltarInicioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent voltarInicioIntent = new Intent(
						RegistroOcorrenciaSucessoActivity.this,
						MainActivity.class);
				startActivity(voltarInicioIntent);
			}
		});

		mTirarFotoButton = (Button) findViewById(R.id.tirarFotoButton);

		mTirarFotoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent cameraIntent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				Uri fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				// Log.i(DispositivosActivity.class.toString(),
				// fileUri.toString());
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				cameraIntent.putExtra("return-data", true);
				startActivityForResult(cameraIntent,
						CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});

		mEnviarFotoButton = (Button) findViewById(R.id.enviarFotoButton);
		mEnviarFotoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (mImageBitmap == null) {
				// Toast.makeText(
				// RegistroOcorrenciaSucessoActivity.this,
				// "N�o � poss�vel enviar a ocorr�ncia sem tirar foto",
				// Toast.LENGTH_LONG).show();
				// return;
				// }
				Consulta1 consulta = new Consulta1();
				// consulta.execute("London,uk");
				consulta.execute("json");

			}
		});
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				atualizaLocalizacao(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
	}

	private void atualizaLocalizacao(Location location) {
		mLocation = location;
		Log.i(RegistroOcorrenciaSucessoActivity.class.toString(),
				"Latitude: " + location.getLatitude() + " Longitude: "
						+ location.getLongitude());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro_ocorrencia_sucesso, menu);
		return true;
	}

	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private File getOutputMediaFile(int type) {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
			nomeArquivoFoto = mediaFile.getAbsolutePath();
		} else {
			return null;
		}

		return mediaFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				if (data != null) {
					Toast.makeText(this, "Image saved to:\n" + data.getData(),
							Toast.LENGTH_LONG).show();
				} else {
					File file = new File(nomeArquivoFoto);

					if (file.exists()) {
						Log.i(RegistroOcorrenciaSucessoActivity.class
								.toString(), "Conseguiu abrir o arquivo!!");

						InputStream is;
						try {
							is = new FileInputStream(file);
							mImageBitmap = BitmapFactory.decodeStream(is);
						} catch (FileNotFoundException e) {
							Log.i(RegistroOcorrenciaSucessoActivity.class
									.toString(), e.getLocalizedMessage());
						}
					}
					mImageView.setImageBitmap(mImageBitmap);
					Log.i(RegistroOcorrenciaSucessoActivity.class.toString(),
							"Nao conseguiu abrir o arquivo!!");

				}
			} else if (resultCode == RESULT_CANCELED) {
			} else {
			}
		}

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save the user's current game state
		savedInstanceState.putParcelable(ESTADO_FOTO, mImageBitmap);
		savedInstanceState.putParcelable(ESTADO_LOCALIZACAO, mLocation);
		// savedInstanceState.putLong(ESTADO_OCORRENCIA_ID, mOcorrenciaId);
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	private void imprimeOcorrencias(List<Ocorrencia> listaOcorrencias) {

		// List<Ocorrencia> listaOcorrencias = consulta.getOcorrencias();
		Log.i("tag", String.valueOf(listaOcorrencias.size()));
		for (Ocorrencia ocorrencia : listaOcorrencias) {
			Log.i("tag", ocorrencia.getCategoria());
			Log.i("tag", ocorrencia.getDenunciante());
		}

	}

	public class Consulta1 extends AsyncTask<String, Void, Boolean> {

		List<Ocorrencia> listaOcorrencias = new ArrayList<Ocorrencia>();

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(
					RegistroOcorrenciaSucessoActivity.this);

			progressDialog.setMessage("Aguarde...");
			progressDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {

			String linha = "";
			Boolean erro = true;

			// String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
			// String URL =
			// "http://localhost:8080/chameozelador/ocorrencia/index?format=";
			String URL = "http://chameozelador.herokuapp.com/ocorrencia/index?format=";
			String result = "";
			String deviceId = "xxxxx";
			final String tag = "Your Logcat tag: ";

			if (params.length > 0)
				try {
					HttpClient client = new DefaultHttpClient();
					HttpGet requisicao = new HttpGet();
					requisicao.setHeader("Content-Type",
							"text/plain; charset=utf-8");
					requisicao.setURI(new URI(URL + params[0]));
					HttpResponse resposta = client.execute(requisicao);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(resposta.getEntity()
									.getContent()));
					StringBuffer sb = new StringBuffer("");

					while ((linha = br.readLine()) != null) {
						sb.append(linha);
					}

					br.close();
					Log.i(tag, sb.toString());
					setOcorrencias(sb.toString());
					erro = false;

				} catch (Exception e) {
					Log.e(Consulta1.class.toString(),
							"Erro no parsing do JSON!!!", e);
					erro = true;
				}

			return erro;
		}

		private void setOcorrencias(String jsonString) {
			try {
				JSONArray listaOcorrenciasJSON = new JSONArray(jsonString);
				JSONObject ocorrenciaJSON;

				for (int i = 0; i < listaOcorrenciasJSON.length(); i++) {
					ocorrenciaJSON = new JSONObject(
							listaOcorrenciasJSON.getString(i));
					Ocorrencia ocorrencia = new Ocorrencia();
					ocorrencia.setCategoria(ocorrenciaJSON
							.getString("categoria"));
					ocorrencia.setDenunciante(ocorrenciaJSON
							.getString("denunciante"));

					listaOcorrencias.add(ocorrencia);
					Log.i(RegistroOcorrenciaSucessoActivity.class.toString(),
							"Adicionando ocorrencia");
				}
			} catch (JSONException e) {
				Log.e(RegistroOcorrenciaSucessoActivity.class.toString(),
						"Erro no parsing do JSON", e);
			}

		}

		public List<Ocorrencia> getOcorrencias() {
			return listaOcorrencias;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			RegistroOcorrenciaSucessoActivity.this
					.imprimeOcorrencias(listaOcorrencias);
		}

	}

}
