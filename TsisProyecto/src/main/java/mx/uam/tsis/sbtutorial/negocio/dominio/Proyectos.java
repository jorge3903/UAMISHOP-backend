package mx.uam.tsis.sbtutorial.negocio.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ProyectosyServicios")
public class Proyectos extends Producto{
	
	@Column(name="representante")
	private String representante;
	@Column(name="requisitos")
	private String requisitos;

	public Proyectos() {
		
	}
	public Proyectos(String nombre, Double precio, String descripcion,String representante, String requisitos, Archivo archivo) {
		super(nombre,precio,descripcion,archivo);
		this.representante = representante;
		this.requisitos = requisitos;
	}
	
	/**
	 * Recupera el represenante del Proyecto
	 * @return nombre
	 */
	public String getRepresentante() {
		return representante;
	}

	/**
	 * Actualiza el Representante del Proyecto
	 * @param nombre
	 */
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	
	/**
	 * Recupera el Nombre del Producto
	 * @return nombre
	 */
	public String getRequisitos() {
		return requisitos;
	}

	/**
	 * Actualiza el Nombre del Producto
	 * @param nombre
	 */
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos ;
	}
}
