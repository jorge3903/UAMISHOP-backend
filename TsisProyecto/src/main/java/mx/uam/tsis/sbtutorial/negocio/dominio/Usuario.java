package mx.uam.tsis.sbtutorial.negocio.dominio;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entidad del modelo de dominio
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
	@Column(name="contraseña")
	private String contra;
	@Column(name="correo")
	private String correo;
	@Column(name="telefono")
	private String telefono;
	@Column(name="calificcion")
	private double calificacion;
	@ManyToOne
	@JoinColumn(name="idArchivo")
	private Archivo fotoPerfil;
	@OneToMany( targetEntity=Producto.class )
	private Collection<Producto> favoritos;
	@OneToMany( targetEntity=Producto.class )
	private Collection<Producto> productos;
	
	/**
	 * Constructor por Default;
	 */
	public Usuario() {
		
	}
	
	/**
	 * Constructor para el usuario
	 * @param nombre
	 * @param contraseña
	 * @param correo
	 * @param telefono
	 * @param productos
	 */
	public Usuario(String nombre, String contra, String correo, String telefono, Collection<Producto> productos) {
		this.nombre = nombre;
		this.contra = contra;
		this.correo = correo;
		this.telefono = telefono;
		this.productos = productos;
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
	 * Recupera la Contrasela del usuario
	 * @return contraseña
	 */
	public String getContra() {
		return contra;
	}
	
	/**
	 * Actualiza la Contraseña del usuario
	 * @param contraseña
	 */
	public void setContraseña(String contra) {
		this.contra = contra;
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
	public double getCalificacion() {
		return calificacion;
	}
	
	/**
	 * Actualiza el Telefono del usuario
	 * @param telefono
	 */
	public void setCalificacion(double calificacion) {
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
	public Collection<Producto> getFavoritos() {
		return favoritos;
	}

	/**
	 * Actualiza los Productos favoritos del usuario
	 * @param productos
	 */
	public void setFavoritos(Collection<Producto> favoritos) {
		this.favoritos = favoritos;
	}
	
	/**
	 * Recupera los Productos del usuario
	 * @return productos
	 */
	public Collection<Producto> getProductos() {
		return productos;
	}

	/**
	 * Actualiza los Productos del usuario
	 * @param productos
	 */
	public void setProductos(Collection<Producto> productos) {
		this.productos = productos;
	}
	

}
