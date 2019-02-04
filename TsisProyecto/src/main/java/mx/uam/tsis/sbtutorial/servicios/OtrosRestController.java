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
import mx.uam.tsis.sbtutorial.negocio.OtrosService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Otros;

@CrossOrigin ( origins = { "http://localhost:4200", "https://uamishop.azurewebsites.net"}) 
@RestController
public class OtrosRestController {

	@Autowired
	private OtrosService servicioOtros;   
    @Autowired
    private ArchivoService fileStorageService;
	
	//mapeos para los json de la web cambio de prueba
	
	/**
	 * Metodo para agregar un producto de la categoria "otros" con su archivo a la API
	 * @param file
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @param idUsuario
	 * @return Estado de la entidad libro
	 */
    @PostMapping("/Otros")
	public ResponseEntity<Otros> agregarOtro(
			@RequestParam String nombre, @RequestParam Double precio, 
			@RequestParam String descripcion, @RequestParam("file") MultipartFile file,Long idUsuario){
    	
    	if (idUsuario !=null) {
    	 String fileName = fileStorageService.storeFile(file);
         String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/downloadFile/")
                 .path(fileName)
                 .toUriString();
         String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/archivo/")
                 .path(fileName)
                 .toUriString();
         //se crea el archivo con los datos
         Archivo archivo = fileStorageService.agregarArchivo(new Archivo(fileName, url, fileDownloadUri,
                 file.getContentType(), file.getSize()));
         //se crea el producto con los datos
         Otros otro= new Otros(nombre, precio, descripcion, archivo);
         
         if(archivo == null) {
        	 //si no se pudo crear el archivo tampoco se podra crear el producto
        	 return new ResponseEntity<Otros>(otro, HttpStatus.BAD_REQUEST);
         }
         
    	
		if(servicioOtros.agregarOtro(otro) != null) {
			//se ha creado correctamente el producto
			if(servicioOtros.agregarDueño(idUsuario, otro)) {
				return new ResponseEntity<Otros>(otro, HttpStatus.CREATED);
			}else { 
				servicioOtros.eliminarOtro(otro.getId());
				fileStorageService.eliminaArchivo(archivo.getId());
				otro=null;
				return new ResponseEntity<Otros>(otro, HttpStatus.BAD_REQUEST);
			}
		}
		else {
			//no se pudo crear el producto
			fileStorageService.eliminaArchivo(archivo.getId());
			otro = null;
			return new ResponseEntity<Otros>(otro, HttpStatus.BAD_REQUEST);
		}
    	}else {
    		Otros otr=null;
    		return new ResponseEntity<Otros>(otr, HttpStatus.BAD_REQUEST);
    	}
	}
	
	/**
	 *  Metodo para obtener todos los productos de la categoria "otros" de la API
	 * @return lista de todos los productos de la categoria "otros"
	 */
	@RequestMapping(value = "/otros", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Otros>> dameOtros(){
		//solo para probar
		Iterable<Otros> otro = servicioOtros.dameOtros();
		return new ResponseEntity<Iterable<Otros>>(otro, HttpStatus.OK);
	}
}
