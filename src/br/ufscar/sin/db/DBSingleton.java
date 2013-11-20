package br.ufscar.sin.db;

import android.content.Context;

public class DBSingleton {

	private static DBHandler mDBHandler;
	
	public static DBHandler getDBHandler(Context context) {
		if (mDBHandler==null){
			mDBHandler = new DBHandler(context);
		}
		
		return mDBHandler;
	}
	
}
