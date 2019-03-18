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
import mx.uam.tsis.sbtutorial.negocio.ProyectosService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Proyectos;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"} ) 
@RestController
public class ProyectosRestController {

	@Autowired
	private ProyectosService servicioProyecto;   
    @Autowired
    private ArchivoService fileStorageService;
	
	//mapeos para los json de la web
	
	/**
	 * Metodo para agregar un proyecto con su archivo a la API
	 * @param idArchivo
	 * @param nombre
	 * @param representante
	 * @param requisitos
	 * @param precio
	 * @param descripcion
	 * @return Estado de la entidad Producto
	 */
    @PostMapping("/proyectos")
	public ResponseEntity<Proyectos> agregarProyecto(
			@RequestParam String nombre,@RequestParam String representante, @RequestParam Double precio, 
			@RequestParam String descripcion,@RequestParam String requisitos, @RequestParam("file") MultipartFile file,Long idUsuario){
    	
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
         Proyectos proyecto= new Proyectos(nombre, precio, descripcion,representante,requisitos,archivo);
         
         if(archivo == null) {
        	 //si no se pudo crear el archivo tampoco se podra crear el producto
        	 return new ResponseEntity<Proyectos>(proyecto, HttpStatus.BAD_REQUEST);
         }
         
    	
		if(servicioProyecto.agregarProyecto(proyecto) != null) {
			//se ha creado correctamente el producto
			if(servicioProyecto.agregarDueño(idUsuario, proyecto)) {
				return new ResponseEntity<Proyectos>(proyecto, HttpStatus.CREATED);
			}else {
				servicioProyecto.eliminarProyecto(proyecto.getId());
				fileStorageService.eliminaArchivo(archivo.getId());
				proyecto=null;
				return new ResponseEntity<Proyectos>(proyecto, HttpStatus.BAD_REQUEST);
			}
		}else {
			//no se pudo crear el producto
			fileStorageService.eliminaArchivo(archivo.getId());
			proyecto=null;
			return new ResponseEntity<Proyectos>(proyecto, HttpStatus.BAD_REQUEST);
		}
    	}else {
    		Proyectos pro=null;
    		return new ResponseEntity<Proyectos>(pro, HttpStatus.BAD_REQUEST);
    	}
	}
	
	/**
	 *  Metodo para obtener todos los Proyectos de la API
	 * @return lista de todos los productos
	 */
	@RequestMapping(value = "/proyectos", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Proyectos>> dameProyectos(){
		Iterable<Proyectos> proyecto = servicioProyecto.dameProyectos();
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Proyectos>>(proyecto, HttpStatus.OK);
	}
	
	/**
	 *  Metodo para obtener todos los Proyectos de la API por pagina
	 * @return lista de todos los productos ce la pagina
	 */
	@RequestMapping(value = "/proyectosPorPagina", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Proyectos>> proyectosdePagina(@RequestParam int pagina,@RequestParam int elementos){
		Iterable<Proyectos> proyecto = servicioProyecto.proyectosPorPagina(pagina, elementos);
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Proyectos>>(proyecto, HttpStatus.OK);
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
	 * NO IMPLEMENTADO
	 * Metodo para editar un proyecto
	 * @param idProducto
	 * @return string de producto actualizado o error al actualizar
	 */
	@RequestMapping(value = "/proyectos/{idProyecto}", method = RequestMethod.PUT)
	public String actulizarProyecto(@PathVariable Long idProyecto){
		if(servicioProyecto.actualizarProyecto(idProyecto)) {
			return "OK. PRODUCTO ACTUALIZADO CORRECTAMENTE";
		}
		else {
			return "ERROR. NO SE PUDO ACTUALIZAR EL PRODUCTO INDICADO";
		}
	}
	
	/**
	 * Metodo para eliminar un proyecto de la API
	 * @param idProducto
	 * @return Mensaje con el resultado obtenido para el producto
	 */
	@RequestMapping(value = "/proyectos/{idProyecto}", method = RequestMethod.DELETE)
	public String eliminarProyecto(@PathVariable Long idProyecto){
		if(servicioProyecto.eliminarProyecto(idProyecto)) {
			return "OK. PRODUCTO ELIMINADO CORRECTAMENTE";
		}
		else {
			return "ERROR. NO SE PUDO ELIMINAR EL PRODUCTO INDICADO";
		}
	}
	
	/**
	 * Metodo para editar la informacion de un proyecto
	 * @param idUsuario
	 * @param idProducto
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @param representante
	 * @param requisitos
	 * @return true si se edito bien o false si no
	 */
    @PostMapping("/modificaProyecto")
	public boolean modificaProyecto(
		@RequestParam Long idUsuario, @RequestParam Long idProducto, @RequestParam String nombre,
		@RequestParam Double precio,@RequestParam String descripcion,@RequestParam String representante,@RequestParam String requisitos){
        	return servicioProyecto.modificaProyecto(idUsuario, idProducto, nombre, precio, descripcion,representante,requisitos);
    }
}

