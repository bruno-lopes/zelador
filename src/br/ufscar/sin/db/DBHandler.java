package br.ufscar.sin.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class DBHandler {

	private static final String DATABASE_NAME = "zelador1.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABELA_OCORRENCIA = "ocorrencia";
	private static final String TABELA_CATEGORIA= "categoria";
	
	
	private Context context;
	private SQLiteDatabase db;

	private static HashMap<String, String> mProjection;
	
	public DBHandler(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
	}
	
	public void inserirOcorrencia(String categoria, String detalhamento, String nome, Integer gravidade, Date data_hora, String status) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		db.execSQL("INSERT INTO " + TABELA_OCORRENCIA +
				" (categoria, detalhamento, nome, gravidade, data_hora, status) VALUES (" +
				"'" + categoria + "'," +
				"'" + detalhamento + "'," +
				"'" + nome + "'," +
				gravidade + ", '" +
				simpleDateFormat.format(data_hora) + "', '" +
				status + "');");
		
		//data
		//hora
		//status
		//foto
		//latitude
		//longitude
	}

	public Cursor listaOcorrencias() {
	        return db.rawQuery("SELECT * FROM " +
	                TABELA_OCORRENCIA +
	                " order by id DESC", null);
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
					" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"categoria TEXT, " +
					"detalhamento TEXT, " +
					"nome TEXT, " +
					"gravidade INTEGER, " +
					"data_hora TEXT, " + 
					"status TEXT);");
		}
		
		


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
	}
}
