package br.ufscar.sin.entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;
import android.location.Location;
import android.widget.ImageView;

public class Ocorrencia {
	private Long id;
	private String detalhamento;
	private String denunciante;
	private Integer gravidade;
	private String categoria;
	private Date dataHora;
	private Location localizacao;
	private ImageView fotografia;

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
        id = c.getLong(c.getColumnIndex("id"));
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

	public Location getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Location localizacao) {
		this.localizacao = localizacao;
	}

	public ImageView getFotografia() {
		return fotografia;
	}

	public void setFotografia(ImageView fotografia) {
		this.fotografia = fotografia;
	}
	
	@Override
    public String toString() {
        return categoria + ", ---  " + detalhamento;
    }
}