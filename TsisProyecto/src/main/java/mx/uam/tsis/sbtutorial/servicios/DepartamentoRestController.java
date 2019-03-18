package mx.uam.tsis.sbtutorial.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import mx.uam.tsis.sbtutorial.negocio.ArchivoService;
import mx.uam.tsis.sbtutorial.negocio.DepartamentoService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Departamento;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"}) 
@RestController
public class DepartamentoRestController {

	@Autowired
	private DepartamentoService servicioDepartamento;   
    @Autowired
    private ArchivoService fileStorageService;
	
	//mapeos para los json de la web
	
	/**
	 * Metodo para agregar un departamento con su archivo a la API
	 * @param Archivo
	 * @param ubicacion
	 * @param precio
	 * @param descripcion
	 * @return Departamento si todo salio bien o null en caso contrario
	 */
    @PostMapping("/departamentos")
	public ResponseEntity<Departamento> agregarDepartamento(
			@RequestParam String nombre,@RequestParam String ubicacion, @RequestParam Double precio, 
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
         Departamento departamento= new Departamento(nombre,ubicacion, precio, descripcion, archivo);
         
         if(archivo == null) {
        	 //si no se pudo crear el archivo tampoco se podra crear el producto
        	 return new ResponseEntity<Departamento>(departamento, HttpStatus.BAD_REQUEST);
         }
         
         if(servicioDepartamento.agregarDepartamento(departamento) != null) {
        	 if(servicioDepartamento.agregarDueño(idUsuario, departamento)) {
        		 return new ResponseEntity<Departamento>(departamento, HttpStatus.CREATED);
        	 }else { 
        		 servicioDepartamento.eliminarDepartamento(departamento.getId());
 				 fileStorageService.eliminaArchivo(archivo.getId());
 				 departamento=null;
        		 return new ResponseEntity<Departamento>(departamento, HttpStatus.BAD_REQUEST);
        	 }
         }else {
        	fileStorageService.eliminaArchivo(archivo.getId()); 
        	departamento=null;
        	return new ResponseEntity<Departamento>(departamento, HttpStatus.BAD_REQUEST);
          }
    	}else {
    		Departamento dep=null;
    		return new ResponseEntity<Departamento>(dep, HttpStatus.BAD_REQUEST);
    	}
	}
	
	/**
	 *  Metodo para obtener todos los Departamentos de la API
	 * @return lista de todos los departamentos
	 */
	@RequestMapping(value = "/departamentos", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Departamento>> dameDepartamentos(){
		Iterable<Departamento> departamentos = servicioDepartamento.dameDepartamentos();
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Departamento>>(departamentos, HttpStatus.OK);
	}
	
	/**
	 *  Metodo para obtener todos los Departamentos de la API por pagina
	 * @return lista de todos los departamentos de la pagina
	 */
	@RequestMapping(value = "/departamentosPorPagina", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Departamento>> departamentosPorPagina(@RequestParam int pagina,@RequestParam int elementos){
		Iterable<Departamento> departamentos = servicioDepartamento.departamentoPorPagina(pagina, elementos);
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Departamento>>(departamentos, HttpStatus.OK);
	}
	
	/**
	 * Metodo para editar la informacion de un departamento
	 * @param idUsuario
	 * @param idProducto
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @param ubicacion
	 * @return true si se edito bien o false si no
	 */
    @PostMapping("/modificaDepartamento")
	public boolean modificaDepartamento(
		@RequestParam Long idUsuario, @RequestParam Long idProducto, @RequestParam String nombre,
		@RequestParam Double precio,@RequestParam String descripcion,@RequestParam String ubicacion){
        	return servicioDepartamento.modificaDepartamento(idUsuario, idProducto, nombre, precio, descripcion,ubicacion);
    }
}