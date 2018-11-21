package mx.uam.tsis.sbtutorial.negocio.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Archivo")
public class Archivo implements Serializable{
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="nombreArchivo")
    private String nombreArchivo;
	@Column(name="url")
    private String url;
	@Column(name="linkDescarga")
    private String linkDescarga;
	@Column(name="tipoArchivo")
    private String tipoArchivo;
	@Column(name="tamaño")
    private long tamaño;
	
	public Archivo() {
		
	}

    public Archivo(String nombreArchivo, String url, String linkDescarga, String tipoArchivo, long tamaño) {
        this.nombreArchivo = nombreArchivo;
        this.url = url;
        this.linkDescarga = linkDescarga;
        this.tipoArchivo = tipoArchivo;
        this.tamaño = tamaño;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLinkDescarga() {
		return linkDescarga;
	}

	public void setLinkDescarga(String linkDescarga) {
		this.linkDescarga = linkDescarga;
	}

	public String getTipoArchivo() {
		return tipoArchivo;
	}

	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public long getTamaño() {
		return tamaño;
	}

	public void setTamaño(long tamaño) {
		this.tamaño = tamaño;
	}
    
}