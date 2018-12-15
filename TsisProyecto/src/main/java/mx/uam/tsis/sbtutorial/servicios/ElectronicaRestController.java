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
import mx.uam.tsis.sbtutorial.negocio.ElectronicaService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import mx.uam.tsis.sbtutorial.negocio.dominio.Electronica;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"} ) 
@RestController
public class ElectronicaRestController  {

	@Autowired
	private ElectronicaService servicioElectronica;   
    @Autowired
    private ArchivoService fileStorageService;
	
	//mapeos para los json de la web
	
	/**
	 * Metodo para agregar un producto electronico con su archivo a la API
	 * @param idArchivo
	 * @param nombre
	 * @param precio
	 * @param descripcion
	 * @return Estado de la entidad Producto
	 */
    @PostMapping("/electronica")
	public ResponseEntity<Electronica> agregarElectronica(
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
         Electronica electronica= new Electronica(nombre, precio, descripcion, archivo);
         
         if(archivo == null) {
        	 //si no se pudo crear el archivo tampoco se podra crear el producto
        	 return new ResponseEntity<Electronica>(electronica, HttpStatus.BAD_REQUEST);
         }
         
    	
		if(servicioElectronica.agregarElectronica(electronica) != null) {
			//se ha creado correctamente el producto
			if(servicioElectronica.agregarDueño(idUsuario, electronica)) {
				return new ResponseEntity<Electronica>(electronica, HttpStatus.OK);
			}else {
				return new ResponseEntity<Electronica>(electronica, HttpStatus.BAD_REQUEST);
			}
		}else {
			//no se pudo crear el producto
			return new ResponseEntity<Electronica>(electronica, HttpStatus.BAD_REQUEST);
		}
    	}else {
    		Electronica elec=null;
    		return new ResponseEntity<Electronica>(elec, HttpStatus.BAD_REQUEST);
    	}
	}
	
	/**
	 *  Metodo para obtener todos los Productos electronicos de la API
	 * @return lista de todos los productos
	 */
	@RequestMapping(value = "/electronica", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Electronica>> dameElectronica(){
		Iterable<Electronica> electronica = servicioElectronica.dameElectronica();
		// return servicioProductos.dameProductos();
		return new ResponseEntity<Iterable<Electronica>>(electronica, HttpStatus.OK);
	}
	
	/**
	 * Metodo para obtener todos los Productos electronicos de la API por CATEGORIA
	 * @param categoria
	 * @return lista de Productos con esa categoria
	 */
	/*@RequestMapping(value = "/productos/categorias/{cat}", method = RequestMethod.GET)
	public Iterable<Producto> dameProductosByCategoria(@PathVariable String cat){
		return servicioProductos.dameProductosByCategoria(cat);
	}*/
	
	/**
	 * NO IMPLEMENTADO
	 * Metodo para editar un producto electronico
	 * @param idElectronica
	 * @return
	 */
	@RequestMapping(value = "/electronica/{idElectronica}", method = RequestMethod.PUT)
	public String actulizarElectronica(@PathVariable Long idElectronica){
		if(servicioElectronica.actualizarElectronica(idElectronica)) {
			return "OK. PRODUCTO ACTUALIZADO CORRECTAMENTE";
		}
		else {
			return "ERROR. NO SE PUDO ACTUALIZAR EL PRODUCTO INDICADO";
		}
	}
	
	/**
	 * Metodo para eliminar un producto eletronico de la API
	 * @param idElectronica
	 * @return Mensaje de error si no se borro correctamente y de OK si se borro correctamente
	 */
	@RequestMapping(value = "/electronica/{idElectronica}", method = RequestMethod.DELETE)
	public String eliminarElectronica(@PathVariable Long idElectronica){
		if(servicioElectronica.eliminarElectronica(idElectronica)) {
			return "OK. PRODUCTO ELIMINADO CORRECTAMENTE";
		}
		else {
			return "ERROR. NO SE PUDO ELIMINAR EL PRODUCTO INDICADO";
		}
	}
}

