package br.ufscar.sin.zelador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

public class RegistroOcorrenciaSucessoActivity extends Activity {

	private Button mVoltarInicioButton;
	private Button mTirarFotoButton;
	private Button mEnviarFotoButton;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private String nomeArquivoFoto;
	private Bitmap mImageBitmap;
	private ImageView mImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_ocorrencia_sucesso);
		mImageView = (ImageView) findViewById(R.id.fotoOcorrenciaImageView);
		mVoltarInicioButton = (Button) findViewById(R.id.voltarInicioButton);
		
		mVoltarInicioButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent voltarInicioIntent = new Intent(RegistroOcorrenciaSucessoActivity.this, MainActivity.class);
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
						Log.i(RegistroOcorrenciaSucessoActivity.class.toString(),
								"Conseguiu abrir o arquivo!!");

						InputStream is;
						try {
							is = new FileInputStream(file);
							mImageBitmap = BitmapFactory.decodeStream(is);
						} catch (FileNotFoundException e) {
							Log.i(RegistroOcorrenciaSucessoActivity.class.toString(),
									e.getLocalizedMessage());
						}
					}
					mImageView.setImageBitmap(mImageBitmap);
					Log.i(RegistroOcorrenciaSucessoActivity.class.toString(),
							"Não conseguiu abrir o arquivo!!");

				}
			} else if (resultCode == RESULT_CANCELED) {
			} else {
			}
		}

	}

}