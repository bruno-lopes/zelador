package br.ufscar.sin.zelador;

import java.util.Calendar;

import br.ufscar.sin.db.DBHandler;
import br.ufscar.sin.db.DBSingleton;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroOcorrenciaActivity extends Activity {

	private Button mRegistrarOcorrenciaButton;
	private Button mCancelarOcorrenciaButton;

	private EditText mNomeEditText;
	private EditText mDetalhamentoEditText;

	private Spinner mCategoriaDasOcorrenciasSpinner;

	private SeekBar mGravidadeSeekBar;
	private DBHandler mDBHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_ocorrencia);

		mDBHandler = DBSingleton.getDBHandler(RegistroOcorrenciaActivity.this);

		mNomeEditText = (EditText) findViewById(R.id.nomeDenuncianteEditText);
		mDetalhamentoEditText = (EditText) findViewById(R.id.detalhamentoEditText);

		mCategoriaDasOcorrenciasSpinner = (Spinner) findViewById(R.id.categoriasDasOcorrenciasSpinner);

		mGravidadeSeekBar = (SeekBar) findViewById(R.id.gravidadeSeekBar);

		mRegistrarOcorrenciaButton = (Button) findViewById(R.id.registrarOcorrenciaButton);
		mRegistrarOcorrenciaButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String categoria = mCategoriaDasOcorrenciasSpinner
						.getSelectedItem().toString();
				String detalhamento = mDetalhamentoEditText.getText()
						.toString();
				String nome = mNomeEditText.getText().toString();
				Integer gravidade = mGravidadeSeekBar.getProgress();
				
				Calendar data_hora = Calendar.getInstance();
				String status = "Aberta";
				
				Long ocorrenciaId = mDBHandler.inserirOcorrencia(categoria, detalhamento, nome,
						gravidade, data_hora.getTime(), status);
				
				if (ocorrenciaId==-1){
					Toast.makeText(RegistroOcorrenciaActivity.this, "N�o foi poss�vel inserir a ocorr�ncia no banco de dados", Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent registrarOcorrenciaIntent = new Intent(RegistroOcorrenciaActivity.this, RegistroOcorrenciaSucessoActivity.class);
				registrarOcorrenciaIntent.putExtra(RegistroOcorrenciaSucessoActivity.PARAMETRO_ID_OCORRENCIA, ocorrenciaId);
				startActivity(registrarOcorrenciaIntent);
			}
		});

		mCancelarOcorrenciaButton = (Button) findViewById(R.id.cancelarOcorrenciaButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro_ocorrencia, menu);
		return true;
	}

}
