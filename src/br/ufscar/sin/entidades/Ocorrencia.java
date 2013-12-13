package br.ufscar.sin.entidades;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;

public class Ocorrencia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String detalhamento;
	private String denunciante;
	private Integer gravidade;
	private String categoria;
	private Date dataHora;
	private Double latitude;
	private Double longitude;
	private byte[] fotografia;
	private String status;

	public Ocorrencia() {
	}

	public Ocorrencia(Cursor c) {
		categoria = c.getString(1);
		detalhamento = c.getString(2);
		denunciante = c.getString(3);
		gravidade = c.getInt(4);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			dataHora = sdf.parse(c.getString(5));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		status = c.getString(6);
		latitude = c.getDouble(7);
		longitude = c.getDouble(8);
		fotografia = c.getBlob(9);
		id = c.getLong(c.getColumnIndex("id"));
	}

	public Ocorrencia(JSONObject ocorrenciaJSON) {
		try {
			categoria = ocorrenciaJSON.getString("categoria");

			detalhamento = ocorrenciaJSON.getString("detalhamento");
			denunciante = ocorrenciaJSON.getString("denunciante");
			gravidade = ocorrenciaJSON.getInt("gravidade");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

			try {
				dataHora = sdf.parse(ocorrenciaJSON.getString("dataHora"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			status = ocorrenciaJSON.getString("status");
			latitude = ocorrenciaJSON.getDouble("latitude");
			longitude = ocorrenciaJSON.getDouble("longitude");
			byte[] array = new byte[ocorrenciaJSON.getJSONArray("fotografia")
					.length()];

			for (int i = 0; i < ocorrenciaJSON.getJSONArray("fotografia")
					.length(); i++) {
				array[i] = (byte) ocorrenciaJSON.getJSONArray("fotografia")
						.getInt(i);
			}
			fotografia = array;
			id = ocorrenciaJSON.getLong("id");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public String getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}

	public String getDenunciante() {
		return denunciante;
	}

	public void setDenunciante(String denunciante) {
		this.denunciante = denunciante;
	}

	public Integer getGravidade() {
		return gravidade;
	}

	public void setGravidade(Integer gravidade) {
		this.gravidade = gravidade;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;

	}

	// public Location getLocalizacao() {
	// return localizacao;
	// }

	// public void setLocalizacao(Location localizacao) {
	// this.localizacao = localizacao;
	// }

	public byte[] getFotografia() {
		return fotografia;
	}

	public void setFotografia(byte[] fotografia) {
		this.fotografia = fotografia;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return categoria + ", ---  " + detalhamento;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("id",id);
		map.put("detalhamento", detalhamento);
		map.put("denunciante", denunciante);
		map.put("gravidade", gravidade);
		map.put("categoria", categoria);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		map.put("dataHora", sdf.format(dataHora));
//		Log.i("", Arrays.toString(fotografia));
		
//		map.put("fotografia", /*fotografia);*/Base64.encodeToString(fotografia, Base64.DEFAULT));//Arrays.toString(fotografia));
//		map.put("fotografia", new String (fotografia, Charset.forNamemap.put("fotografia", ("UTF-8")));
		map.put("fotografia",  Base64.encodeToString(fotografia, Base64.DEFAULT));
//		map.put("fotografia",  Base64.encode(fotografia, Base64.DEFAULT));
		map.put("latitude", latitude.toString());
		map.put("longitude", longitude.toString());
		map.put("status", status);
		return map;
	}
}