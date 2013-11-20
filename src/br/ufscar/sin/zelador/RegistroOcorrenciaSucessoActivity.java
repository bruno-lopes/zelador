package br.ufscar.sin.zelador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegistroOcorrenciaSucessoActivity extends Activity {

	private Button mVoltarInicioButton;
	private Button mTirarFotoButton;
	private Button mEnviarFotoButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_ocorrencia_sucesso);
		
		mVoltarInicioButton = (Button) findViewById(R.id.voltarInicioButton);
		
		mVoltarInicioButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent voltarInicioIntent = new Intent(RegistroOcorrenciaSucessoActivity.this, MainActivity.class);
				startActivity(voltarInicioIntent);
			}
		});		
		
		mTirarFotoButton = (Button) findViewById(R.id.tirarFotoButton);
		mEnviarFotoButton = (Button) findViewById(R.id.enviarFotoButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro_ocorrencia_sucesso, menu);
		return true;
	}

}
