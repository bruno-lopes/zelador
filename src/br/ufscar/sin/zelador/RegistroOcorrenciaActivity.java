package br.ufscar.sin.zelador;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RegistroOcorrenciaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_ocorrencia);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro_ocorrencia, menu);
		return true;
	}

}
