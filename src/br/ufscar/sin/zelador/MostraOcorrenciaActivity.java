package br.ufscar.sin.zelador;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import br.ufscar.sin.db.DBHandler;
import br.ufscar.sin.db.DBSingleton;
import br.ufscar.sin.entidades.Ocorrencia;

public class MostraOcorrenciaActivity extends Activity {

	public static final String PARAMETRO_ID_OCORRENCIA = "id_ocorrencia";
	private DBHandler mDBHandler;
	private TextView mMostraCategoriaTextView;
	private TextView mMostraDetalhamentoTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostra_ocorrencia);

		mDBHandler = DBSingleton.getDBHandler(MostraOcorrenciaActivity.this);

		mMostraCategoriaTextView = (TextView) findViewById(R.id.mostraCategoriaTextView);
		mMostraDetalhamentoTextView = (TextView) findViewById(R.id.mostraDetalhamentoTextView);
		
		Intent intent = getIntent();
		  
		Bundle params = intent.getExtras();  
		   
		if(params!=null) {   
//		   Long idOcorrencia = params.getLong(PARAMETRO_ID_OCORRENCIA);
		   
//		   Cursor c = mDBHandler.recuperaOcorrencia(idOcorrencia);

//			if (c != null) {
//				if (c.moveToFirst()) {
//						Ocorrencia ocorrencia = new Ocorrencia(c);
		   Ocorrencia ocorrencia = (Ocorrencia)params.getSerializable(PARAMETRO_ID_OCORRENCIA);
						mMostraCategoriaTextView.setText(ocorrencia.getCategoria());
						mMostraDetalhamentoTextView.setText(ocorrencia.getDetalhamento());
						
						
						
						
						Bitmap bmp = BitmapFactory.decodeByteArray(ocorrencia.getFotografia(), 0, ocorrencia.getFotografia().length);
						ImageView image = (ImageView) findViewById(R.id.imageView1);

						image.setImageBitmap(bmp);
						
						
						
						
						
//				}
//			}
		   
		   //idTextView.setText("10000");
		   //setContentView(idTextView);
		}
	}

}
