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
import mx.uam.tsis.sbtutorial.negocio.TutoriaService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Tutoria;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"}) 
@RestController
public class TutoriaRestController {

	@Autowired
	private TutoriaService servicioTutoria;   
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
    @PostMapping("/tutorias")
	public ResponseEntity<Tutoria> agregarTutoria(
			@RequestParam String nombre, @RequestParam Double precio, 
			@RequestParam String descripcion, @RequestParam("file") MultipartFile file,String area,Long idUsuario){
    	
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
         Tutoria tutoria= new Tutoria(nombre, precio, descripcion, archivo,area);
         
         if(archivo == null) {
        	 //si no se pudo crear el archivo tampoco se podra crear el producto
        	 return new ResponseEntity<Tutoria>(tutoria, HttpStatus.BAD_REQUEST);
         }
         
			//se ha creado correctamente el producto
        if(servicioTutoria.agregarTutoria(tutoria) != null) {
        	if(servicioTutoria.agregarDueño(idUsuario, tutoria)) {
        		return new ResponseEntity<Tutoria>(tutoria, HttpStatus.CREATED);
        	}else { 
        		servicioTutoria.eliminarTutoria(tutoria.getId());
				fileStorageService.eliminaArchivo(archivo.getId());
				tutoria=null;
        		return new ResponseEntity<Tutoria>(tutoria, HttpStatus.BAD_REQUEST);
        	}
        }else {
        	fileStorageService.eliminaArchivo(archivo.getId());
        	tutoria=null;
   		 	return new ResponseEntity<Tutoria>(tutoria, HttpStatus.BAD_REQUEST);
   	 	}
    	}else {
    		Tutoria tut=null;
    		return new ResponseEntity<Tutoria>(tut, HttpStatus.BAD_REQUEST);
    	}
	}
	
	/**
	 *  Metodo para obtener todos los libros de la API
	 * @return lista de todos los productos
	 */
	@RequestMapping(value = "/tutorias", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Tutoria>> dameTutorias(){
		Iterable<Tutoria> tutorias = servicioTutoria.dameTutorias();
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Tutoria>>(tutorias, HttpStatus.OK);
	}
	
	/**
	 * Metodo para editar la informacion de una tutoria
	 * @param idUsuario
	 * @param idProducto
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @param area
	 * @return true si se edito bien o false si no
	 */
    @PostMapping("/modificaTutoria")
	public boolean modificaTutoria(
		@RequestParam Long idUsuario, @RequestParam Long idProducto, @RequestParam String nombre,
		@RequestParam Double precio,@RequestParam String descripcion,@RequestParam String area){
        	return servicioTutoria.modificaTutoria(idUsuario, idProducto, nombre, precio, descripcion,area);
    }
}
