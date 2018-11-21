package mx.uam.tsis.sbtutorial.servicios;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.uam.tsis.sbtutorial.negocio.ProductoService;
import mx.uam.tsis.sbtutorial.negocio.UsuarioService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@RestController
public class UsuarioRestController {

	@Autowired
	private ProductoService servicioProductos;
	@Autowired
	private UsuarioService servicioUsuarios;
	
	//mapeos para los json de la web
	
	/**
	 * Metodo para agregar un usuario a la API
	 * @param usuario
	 * @return el usuario agregado, si no un error de peticion http
	 */
	@PostMapping(value = "/usuarios")
	public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario){
		Usuario nuevoUsuario = servicioUsuarios.agregarUsuario(usuario);
		if(nuevoUsuario!=null) {
			//return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.CREATED);
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.OK);
		}
		
		else {
			//return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.BAD_REQUEST);
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo para validar el Login del Usuario
	 * @param correo
	 * @param contraseña
	 * @return el usuario si existe en la base de datos, null si no existe
	 */
	@RequestMapping(value = "/usuarios/{correo}/{contraseña}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> logearUsuario(@PathVariable String correo , @PathVariable String contraseña){
		Usuario nuevoUsuario=servicioUsuarios.validaUsuario(correo,contraseña);
		if(nuevoUsuario!=null) {
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.CREATED);
		}
		
		else {
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.BAD_REQUEST);
		}
	}
	
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
	@RequestMapping(value = "/usuarios/{idUsuario}/productos/{idProducto}", method = RequestMethod.PUT)
	public Usuario actulizarUsuario(@PathVariable Long idUsuario, @PathVariable Long idProducto){
		
		Usuario usuario = servicioUsuarios.usuarioById(idUsuario);
		Producto producto = servicioProductos.ProductoById(idProducto);
		
		usuario.getProductos().add(producto);
		
		return servicioUsuarios.actualizarUsuario(usuario);
	}
	
	/**
	 * Metodo para borrar a un usuario de la BD
	 * @param usuario
	 * @return regresa true si se elimino correctamente, false si no
	 */
	@RequestMapping(value = "/usuarios/{idUsuario}", method = RequestMethod.DELETE)
	public boolean eliminarUsuario(@PathVariable Long idUsuario){
		if(servicioUsuarios.eliminarUsuario(idUsuario)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
