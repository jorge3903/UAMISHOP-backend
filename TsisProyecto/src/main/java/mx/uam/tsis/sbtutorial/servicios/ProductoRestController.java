package mx.uam.tsis.sbtutorial.servicios;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mx.uam.tsis.sbtutorial.negocio.ArchivoService;
import mx.uam.tsis.sbtutorial.negocio.ProductoService;
import mx.uam.tsis.sbtutorial.negocio.UsuarioService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"} ) 
@RestController
public class ProductoRestController {
	
	@Autowired
	private ProductoService servicioProductos;
	@Autowired
	private UsuarioService servicioUsuarios;    
    @Autowired
    private ArchivoService fileStorageService;
	
	//mapeos para los json de la web verdad
	
	/**
	 * Metodo para agregar un producto con su archivo a la API
	 * @param idArchivo
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @return Estado de la entidad Producto
	 */
    @PostMapping("/productos")
	public ResponseEntity<Producto> agregarProducto(
			@RequestParam String nombre, @RequestParam Double precio, 
			@RequestParam String descripcion, @RequestParam("file") MultipartFile file){
    	
    	//se carga el nombre original del archivo y su extencion, ademas se guarda el 
    	//archivo en la carpeta archivos del proyecto
    	 String fileName = fileStorageService.storeFile(file);
         
    	 //se contruye la url para a descarga del archivo
         //"http://localhost:8080/downloadFile/archivo.extencion",
         String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/downloadFile/")
                 .path(fileName)
                 .toUriString();
         
         //se construye la url del archivo para su consumo en la API
         //http://localhost:8080/archivo/archivo.extencion
         String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/archivo/")
                 .path(fileName)
                 .toUriString();
        		 
        		 //"http://localhost:8080/archivo/" + file.getOriginalFilename();
         
         //se crea el archivo con los datos
         Archivo archivo = fileStorageService.agregarArchivo(new Archivo(fileName, url, fileDownloadUri,
                 file.getContentType(), file.getSize()));
         
         //se crea el producto con los datos
         Producto producto = new Producto(nombre, precio, descripcion, archivo);
         
         if(archivo == null) {
        	 //si no se pudo crear el archivo tampoco se podra crear el producto
        	 return new ResponseEntity<Producto>(producto, HttpStatus.BAD_REQUEST);
         }
         
    	
		if(servicioProductos.agregarProducto(producto) != null) {
			//se ha creado correctamente el producto
			return new ResponseEntity<Producto>(producto, HttpStatus.CREATED);
		}
		else {
			//no se pudo crear el producto
			return new ResponseEntity<Producto>(producto, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 *  Metodo para obtener todos los Productos de la API
	 * @return lista de todos los productos
	 */
	@RequestMapping(value = "/productos", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Producto>> dameProductos(){
		Iterable<Producto> productos = servicioProductos.dameProductos();
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Producto>>(productos, HttpStatus.OK);
	}
	
	/**
	 *  Metodo para obtener todos los Productos favoritos de un usuario
	 * @return lista de todos los productos favoritos del usuario
	 */
	@RequestMapping(value = "/misFavoritos/{idUsuario}", method = RequestMethod.GET)
	public Collection<Producto> dameFavoritos(@PathVariable Long idUsuario){
		return servicioProductos.dameFavoritos(idUsuario);
	}
	
	/**
	 * Metodo para obtener todos los Productos de la API por CATEGORIA
	 * @param categoria
	 * @return lista de Productos con esa categoria
	 */
	/*@RequestMapping(value = "/productos/categorias/{cat}", method = RequestMethod.GET)
	public Iterable<Producto> dameProductosByCategoria(@PathVariable String cat){
		return servicioProductos.dameProductosByCategoria(cat);
	}*/
	
	/**
	 * Metodo para adregar un producto favorito a un usuario
	 * @param idProducto, idUsuario
	 * @return true si lo agrego bien, false si no
	 */
	@RequestMapping(value = "/agregaFavorito/{idUsuario}/{idProducto}", method = RequestMethod.PUT)
	public boolean agregaFavorito(@PathVariable Long idUsuario,@PathVariable Long idProducto){
		return servicioProductos.agregarEnFavoritos(idUsuario, idProducto);
	}
	
	/**
	 * Metodo para quitar un producto favorito a un usuario
	 * @param idProducto, idUsuario
	 * @return true si lo quito bien, false si no
	 */
	@RequestMapping(value = "/eliminaFavorito/{idUsuario}/{idProducto}", method = RequestMethod.PUT)
	public Collection<Producto> eliminaFavorito(@PathVariable Long idUsuario,@PathVariable Long idProducto){
		return servicioProductos.quitarFavorito(idUsuario, idProducto);
	}
	
	/**
	 * NO IMPLEMENTADO
	 * Metodo para editar un producto
	 * @param idProducto
	 * @return
	 */
	/**@RequestMapping(value = "/productos/{idProducto}", method = RequestMethod.PUT)
	public String actulizarProducto(@PathVariable Long idProducto){
		if(servicioProductos.actualizarProducto(idProducto)) {
			return "OK. PRODUCTO ACTUALIZADO CORRECTAMENTE";
		}
		else {
			return "ERROR. NO SE PUDO ACTUALIZAR EL PRODUCTO INDICADO";
		}
	}**/
	
	/**
	 *  Metodo para obtener el producto con el ID especificado
	 * @return producto con id especificado
	 */
	@RequestMapping(value = "/productos/{idProducto}", method = RequestMethod.GET)
	public Producto dameProductoConId(@PathVariable Long idProducto){
		Producto producto = servicioProductos.ProductoById(idProducto);
		// return servicioProductos.dameProductos();
		return producto;
	}
	
	
	
	/**
	 *  Metodo para obtener todos los Productos de un usuario
	 * @return lista de todos los productos del usuario con el id especificado
	 */
	@RequestMapping(value = "/misProductos/{idUsuario}", method = RequestMethod.GET)
	public Collection<Producto> dameMisProductos(@PathVariable Long idUsuario){
		Collection<Producto> misProductos = servicioProductos.misProductos(idUsuario);
		// return servicioProductos.dameProductos();
		return misProductos;
	}
	
	
	/**
	 * Metodo para eliminar un producto de la API
	 * @param idProducto
	 * @return Mensaje con el resultado obtenido para el producto
	 */
	@RequestMapping(value = "/productos", method = RequestMethod.DELETE)
	public Collection<Producto> eliminarProducto(@RequestParam Long idUsuario, @RequestParam Long idProducto){
		Collection<Producto> productos = servicioProductos.eliminarProducto(idUsuario,idProducto);
		return productos;
	}
	
	
	/**
	 * Metodo para eliminar un producto que consideramos ilegal 
	 * @param idProducto
	 * @return True si se elimino correctamente, false si no
	 */
	@RequestMapping(value = "/bpi/{idProducto}", method = RequestMethod.DELETE)
	public boolean eliminarProductoIlegal(@PathVariable Long idProducto){
		boolean estado = servicioProductos.eliminarProductoIlegal(idProducto);
		return estado;
	}
	
	
	public class URL{
		String url="";
		public String getURL() {
			return this.url;
		}
		public void setURL(String url) {
			this.url = url;
		}
	}
	
	/**
	 * Metodo para editar la imagen de un producto
	 * @param idUsuario
	 * @param idProducto
	 * @param Archivo
	 * @return el url del archivo si se edito bien o "false" si no
	 */
    @PostMapping("/modificaImg")
	public URL modificaImagen(
		@RequestParam Long idUsuario, @RequestParam Long idProducto, @RequestParam("file") MultipartFile file){
    	String fileName = fileStorageService.storeFile(file);
    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/archivo/")
                .path(fileName)
                .toUriString();
        Archivo archivo = fileStorageService.agregarArchivo(new Archivo(fileName, url, fileDownloadUri,
                file.getContentType(), file.getSize()));
        Archivo archAnterior = servicioProductos.modificaImagen(idUsuario,idProducto,archivo);
        URL url1 = new URL();
        if(archAnterior!=null) {
        	fileStorageService.eliminaArchivo(archAnterior.getId());
        	url1.setURL(url);
        	return url1;
        }else {
        	fileStorageService.eliminaArchivo(archivo.getId());
        	return url1;
        }
    }
    
    
    /**
	 * Metodo para editar la informacion de un producto
	 * @param idUsuario
	 * @param idProducto
	 * @param nombre
	 * @param precio
	 * @param descropcion
	 * @return true si se edito bien o false si no
	 */
    @PostMapping("/modificaProducto")
	public boolean modificaProducto(
		@RequestParam Long idUsuario, @RequestParam Long idProducto, @RequestParam String nombre,
		@RequestParam Double precio,@RequestParam String descripcion){
        	return servicioProductos.modificaProducto(idUsuario, idProducto, nombre, precio, descripcion);
    }
	
}
