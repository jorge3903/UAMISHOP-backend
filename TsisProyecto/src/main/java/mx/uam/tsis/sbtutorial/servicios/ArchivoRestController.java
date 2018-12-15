package mx.uam.tsis.sbtutorial.servicios;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import mx.uam.tsis.sbtutorial.negocio.ArchivoService;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;



import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin ( origins   =   {   "http://localhost:4200", "https://uamishop.azurewebsites.net"} )
@RestController
public class ArchivoRestController {

    private static final Logger logger = LoggerFactory.getLogger(ArchivoRestController.class);
    
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ArchivoService fileStorageService;
    
    /**
     * Metodo para subir un archivo de manera individual (sin producto)
     * @param file
     * @return Estado de la entidad Archivo
     */
    @PostMapping("/uploadFile")
    public ResponseEntity<Archivo> uploadFile(@RequestParam("file") MultipartFile file) {
    	
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
        String url = "http://localhost:8080/archivo/" + file.getOriginalFilename();
        
        Archivo archivo = new Archivo(fileName, url, fileDownloadUri,
                file.getContentType(), file.getSize());
        
        if(fileStorageService.agregarArchivo(archivo)  != null) {
        	//se ha creado correctamente el producto
			return new ResponseEntity<Archivo>(archivo, HttpStatus.CREATED);
        }
        else {
			//no se pudo crear el producto
        	return new ResponseEntity<Archivo>(archivo, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Metodo para subir un multiples archivo de manera individual (sin producto)
     * @param files
     * @return
     */
    @PostMapping("/uploadMultipleFiles")
    public List<ResponseEntity<Archivo>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    /**
     * Metodo para la descarga de un archivo
     * @param filename
     * @return el archivo a descargar
     */
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    /**
     * Metodo para obtener un archivo
     * @param filename
     * @return archivo
     */
    @RequestMapping(value = "/archivo/{fileName:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getImageAsResource(@PathVariable String fileName) {
        HttpHeaders headers = new HttpHeaders();
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    
    /**
     * Metodo para obtener todos los archivos de la base de datos
     * @param /archivos
     * @return todos los archivos
     */
    @RequestMapping(value = "/archivos", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Archivo> getArchivos() {
        
        return fileStorageService.dameArchivos();
    }
  
}