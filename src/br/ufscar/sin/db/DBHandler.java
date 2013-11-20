package br.ufscar.sin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler {

	private static final String DATABASE_NAME = "zelador.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABELA_OCORRENCIA = "ocorrencia";
	private static final String TABELA_CATEGORIA= "categoria";
	
	
	private Context context;
	private SQLiteDatabase db;

	public DBHandler(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
	}
	
	public void inserirOcorrencia(String categoria, String detalhamento, String nome, Integer gravidade) {
		db.execSQL("INSERT INTO " + TABELA_OCORRENCIA +
				" (categoria, detalhamento, nome, gravidade) VALUES (" +
				"'" + categoria + "'," +
				"'" + detalhamento + "'," +
				"'" + nome + "'," +
				gravidade + ");");
		
		//data
		//hora
		//status
		//foto
		//latitude
		//longitude
	}

	private class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("","Criando tabela de ocorrencias");
			criarTabelaOcorrencias(db);
			Log.i("","Tabela de ocorrencias criada");
		}

		private void criarTabelaOcorrencias(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABELA_OCORRENCIA +
					"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"categoria TEXT," +
					"detalhamento TEXT," +
					"nome TEXT," +
					"gravidade INTEGER);");
		}
		
		


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
}
