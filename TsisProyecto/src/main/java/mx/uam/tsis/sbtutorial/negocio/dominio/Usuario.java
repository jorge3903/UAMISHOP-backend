package mx.uam.tsis.sbtutorial.negocio.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad Usuario del modelo de dominio
 * 
 */
@Entity
@Table(name="Usuario")
public class Usuario implements Serializable{
	@Id
	@Column(name="Id")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="nombreUsuario")
	private String nombre;
	@Column(name="correo")
	private String correo;
	@Column(name="telefono")
	private String telefono;
	@ElementCollection
	@Column(name="calificcion")
	private Collection<String> calificacion;
	@ManyToOne
	@JoinColumn(name="idArchivo")
	private Archivo fotoPerfil;
	//@OneToMany( targetEntity=Producto.class )
	//private Collection<Producto> favoritos;
	//@OneToMany( targetEntity=Producto.class )
	//private Collection<Producto> productos;
	@ElementCollection
	@Column(name="productos")
	private Collection<Long> productos;
	@ElementCollection
	@Column(name="favoritos")
	private Collection<Long> favoritos;
	
	/**
	 * Constructor por Default;
	 */
	public Usuario() {
		
	}
	
	/**
	 * Constructor para el usuario
	 * @param nombre
	 * @param correo
	 */
	public Usuario(String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	}

	/**
	 * Recupera el ID del usuario
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Actualiza el ID del usuario
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Recupera el Nombre del usuario
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Actualiza el Nombre del usuario
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	/**
	 * Recupera el Correo del usuario
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}
	
	/**
	 * Actualiza el Correo del usuario
	 * @param correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 * Recupera el Telefono del usuario
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	
	/**
	 * Actualiza el Telefono del usuario
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	/**
	 * Recupera el Telefono del usuario
	 * @return telefono
	 */
	public Collection<String> getCalificacion() {
		return calificacion;
	}
	
	/**
	 * Actualiza el Telefono del usuario
	 * @param telefono
	 */
	public void setCalificacion(Collection<String> calificacion) {
		this.calificacion = calificacion;
	}
	
	/**
	 * Recupera la foto de perfil del usuario
	 * @return productos
	 */
	public Archivo getArchivo() {
		return fotoPerfil;
	}
    
	/**
	 * Actualiza la foto de perfil del usuario
	 * @param productos
	 */
	public void setArchivo(Archivo fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}
	
	/**
	 * Recupera los Productos favoritos del usuario
	 * @return productos
	 */
	public Collection<Long> getFavoritos() {
		return favoritos;
	}

	/**
	 * Actualiza los Productos favoritos del usuario
	 * @param productos
	 */
	public void setFavoritos(Collection<Long> favoritos) {
		this.favoritos = favoritos;
	}
	
	/**
	 * Recupera los Productos del usuario
	 * @return productos
	 */
	public Collection<Long> getProductos() {
		return productos;
	}

	/**
	 * Actualiza los Productos del usuario
	 * @param productos
	 */
	public void setProductos(Collection<Long> productos) {
		this.productos = productos;
	}

}
