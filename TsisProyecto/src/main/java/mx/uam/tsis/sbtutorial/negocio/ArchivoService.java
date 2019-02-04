package mx.uam.tsis.sbtutorial.negocio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import exception.FileStorageException;
import exception.MyFileNotFoundException;
import mx.uam.tsis.sbtutorial.datos.ArchivoRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Archivo;
import property.FileStorageProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

@Service
public class ArchivoService {

    private final Path fileStorageLocation;
    
    @Autowired
    private ArchivoRepository repository;

    @Autowired
    public ArchivoService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

	public Archivo agregarArchivo(Archivo archivo) {
		return repository.save(archivo);
	}
	
	public boolean eliminaArchivo(Long idArchivo) {
		repository.delete(idArchivo);
		return true;
	}

	public Archivo archivoById(Long idArchivo) {
		// TODO Auto-generated method stub
		for(Archivo archivo : repository.findAll()) {
			if(archivo.getId() == idArchivo) {
				return archivo;
			}
		}
		return null;
	}

	public Iterable<Archivo> dameArchivos() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
}