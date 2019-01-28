package mx.uam.tsis.sbtutorial.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mx.uam.tsis.sbtutorial.negocio.ArchivoService;
import mx.uam.tsis.sbtutorial.negocio.LibroService;
import mx.uam.tsis.sbtutorial.negocio.ProductoService;
import mx.uam.tsis.sbtutorial.negocio.UsuarioService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Libro;
import mx.uam.tsis.sbtutorial.negocio.dominio.Producto;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"}) 
@RestController
public class LibroRestController {

	@Autowired
	private LibroService servicioLibros;   
    @Autowired
    private ArchivoService fileStorageService;
	
	//mapeos para los json de la web
	
	/**
	 * Metodo para agregar un libro con su archivo a la API
	 * @param idArchivo
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @return Estado de la entidad libro
	 */
    @PostMapping("/libros")
	public ResponseEntity<Libro> agregarLibro(
			@RequestParam String nombre, @RequestParam Double precio, 
			@RequestParam String descripcion, @RequestParam("file") MultipartFile file,Long idUsuario){
    	
    	if (idUsuario !=null) {
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
         Libro libro= new Libro(nombre, precio, descripcion, archivo);
         
         if(archivo == null) {
        	 //si no se pudo crear el archivo tampoco se podra crear el producto
        	 return new ResponseEntity<Libro>(libro, HttpStatus.BAD_REQUEST);
         }
         
    	
		if(servicioLibros.agregarLibro(libro) != null) {
			//se ha creado correctamente el producto
			if(servicioLibros.agregarDueño(idUsuario, libro)) {
				return new ResponseEntity<Libro>(libro, HttpStatus.CREATED);
			}else { 
				return new ResponseEntity<Libro>(libro, HttpStatus.BAD_REQUEST);
			}
		}
		else {
			//no se pudo crear el producto
			return new ResponseEntity<Libro>(libro, HttpStatus.BAD_REQUEST);
		}
    	}else {
    		Libro lib=null;
    		return new ResponseEntity<Libro>(lib, HttpStatus.BAD_REQUEST);
    	}
	}
	
	/**
	 *  Metodo para obtener todos los libros de la API
	 * @return lista de todos los productos
	 */
	@RequestMapping(value = "/libros", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Libro>> dameLibros(){
		Iterable<Libro> libro = servicioLibros.dameLibros();
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Libro>>(libro, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener todos los libros de la API por CATEGORIA
	 * @param categoria
	 * @return lista de Productos con esa categoria
	 */
	/*@RequestMapping(value = "/productos/categorias/{cat}", method = RequestMethod.GET)
	public Iterable<Producto> dameProductosByCategoria(@PathVariable String cat){
		return servicioProductos.dameProductosByCategoria(cat);
	}*/
	
	/**
	 * NO IMPLEMENTADO
	 * Metodo para editar un libro
	 * @param idLibro
	 * @return
	 */
	@RequestMapping(value = "/libros/{idlibro}", method = RequestMethod.PUT)
	public String actulizarLibro(@PathVariable Long idLibro){
		if(servicioLibros.actualizarLibro(idLibro)) {
			return "OK. PRODUCTO ACTUALIZADO CORRECTAMENTE";
		}
		else {
			return "ERROR. NO SE PUDO ACTUALIZAR EL PRODUCTO INDICADO";
		}
	}
	
	/**
	 * Metodo para eliminar un libro de la API
	 * @param idLibro
	 * @return Mensaje con el resultado obtenido para el libro
	 */
	@RequestMapping(value = "/libros/{idLibro}", method = RequestMethod.DELETE)
	public String eliminarLibro(@PathVariable Long idLibro){
		if(servicioLibros.eliminarLibro(idLibro)) {
			return "OK. PRODUCTO ELIMINADO CORRECTAMENTE";
		}
		else {
			return "ERROR. NO SE PUDO ELIMINAR EL PRODUCTO INDICADO";
		}
	}
	
	@RequestMapping(value = "/ropa", method = RequestMethod.GET)
	public String prueba(){
		return "Ya quedo";
	}
}
