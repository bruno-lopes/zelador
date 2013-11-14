package br.ufscar.sin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler {

	private static final String DATABASE_NAME = "zelador22.db";
	private static final int DATABASE_VERSION = 1;

	private Context context;
	private SQLiteDatabase db;

	public DBHandler(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
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
			db.execSQL("CREATE TABLE ocorrencia (" + "detalhamento TEXT,"
					+ "categoria TEXT" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
}
