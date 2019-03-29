package mx.uam.tsis.sbtutorial.servicios;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.uam.tsis.sbtutorial.negocio.ProductoService;
import mx.uam.tsis.sbtutorial.negocio.UsuarioService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"}) 
@RestController
public class UsuarioRestController {

	@Autowired
	private ProductoService servicioProductos;
	@Autowired
	private UsuarioService servicioUsuarios;
	
	//mapeos para los json de la web nuevo
	
	/**
	 * Metodo para agregar un usuario a la API
	 * @param nombre
	 * @param correo
	 * @return el usuario agregado, si no un error de peticion http
	 */
	@PostMapping(value = "/usuarios")
	public ResponseEntity<Usuario> agregarUsuario(@RequestParam String nombre,@RequestParam String correo){
		String nombreUser = nombre.trim();
		String correoUser = correo.trim();
		if(nombreUser.length() !=0 && correoUser.length() != 0) {
			Usuario nuevoUsuario = servicioUsuarios.agregarUsuario(new Usuario(nombreUser,correoUser));
			if(nuevoUsuario!=null) {
				//return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.CREATED);
				return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.OK);
			} else {
				nuevoUsuario = new Usuario("no","no");
				nuevoUsuario.setId((long) -1);
				//return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.BAD_REQUEST);
				return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.BAD_REQUEST);
			}
		} else {
			Usuario user = new Usuario("no","no");
			user.setId((long) -1);
			return new ResponseEntity<Usuario>(user, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo para validar el Login del Usuario
	 * @param correo
	 * @param contraseña
	 * @return el usuario si existe en la base de datos, null si no existe
	 */
	/**@RequestMapping(value = "/usuarios/{correo}/{contraseña}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> logearUsuario(@PathVariable String correo , @PathVariable String contraseña){
		Usuario nuevoUsuario=servicioUsuarios.validaUsuario(correo,contraseña);
		if(nuevoUsuario!=null) {
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.CREATED);
		}
		
		else {
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.BAD_REQUEST);
		}
	}*/
	
	/**
	 * Metodo para obtener todos los usuarios de la API
	 * @return una coleccion de usuarios
	 */
	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public Collection<Usuario> dameUsuarios(){
		//return servicioUsuarios.dameUsuarios();
		return servicioUsuarios.dameUsuarios();
	}
	
	/**
	 * Metodo para actualizar un producto de un usuario de la API
	 * @param usuario
	 * @return regresa la entidad actualizada
	 */
	/**@RequestMapping(value = "/usuarios/{idUsuario}/productos/{idProducto}", method = RequestMethod.PUT)
	public Usuario actulizarUsuario(@PathVariable Long idUsuario, @PathVariable Long idProducto){
		
		Usuario usuario = servicioUsuarios.usuarioById(idUsuario);
		Producto producto = servicioProductos.ProductoById(idProducto);
		
		usuario.getProductos().add(idproducto);
		
		return servicioUsuarios.actualizarUsuario(usuario);
	}*/
	
	/**
	 * Método para borrar a un usuario de la BD
	 * @param usuario
	 * @return true si se elimino correctamente, false si no
	 */
	@RequestMapping(value = "/usuarios/{idUsuario}", method = RequestMethod.DELETE)
	public boolean eliminarUsuario(@PathVariable Long idUsuario){
		if(servicioUsuarios.eliminarUsuario(idUsuario)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo para editar a un usuario de la BD
	 * @param 
	 * @return regresa al usuario si se edito correctamente, null si no
	 */
	@RequestMapping(value = "/usuarios", method = RequestMethod.PUT)
	public Usuario editaUsuario(@RequestParam Long idUsuario,@RequestParam String telefono){
		Usuario usuario=servicioUsuarios.editaUsuario(idUsuario,telefono);
		if(usuario!=null) {
			return usuario;
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * Método para calificar a un usuario
	 * @param idUsuario
	 * @param idUsuarioAcalificar
	 * @params calificacion
	 * @return  colleccion de string con los usuarios y sus calificaciones
	 */
	@PostMapping(value = "/calificaUsuario")
	public ResponseEntity<Collection<String>> calificarUsuario(@RequestParam Long idUsuario,@RequestParam Long idUsuarioAcalificar,@RequestParam Double calificacion ){
		if (idUsuario != idUsuarioAcalificar) {
			Collection<String> calificaciones = servicioUsuarios.calificaUsuario(idUsuario,idUsuarioAcalificar, calificacion);
			return new ResponseEntity<Collection<String>>(calificaciones, HttpStatus.OK);
		} else {
			Collection<String> calificaciones = null;
			return new ResponseEntity<Collection<String>>(calificaciones, HttpStatus.BAD_REQUEST);
		}
	}
	
}
