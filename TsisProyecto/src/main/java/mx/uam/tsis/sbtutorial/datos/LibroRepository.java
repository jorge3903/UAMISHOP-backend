package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.PagingAndSortingRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Libro;

public interface LibroRepository extends PagingAndSortingRepository<Libro, Long>{

}
