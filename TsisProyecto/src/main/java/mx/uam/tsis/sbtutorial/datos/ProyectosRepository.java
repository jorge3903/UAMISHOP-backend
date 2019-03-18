package mx.uam.tsis.sbtutorial.datos;

// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Proyectos;

public interface ProyectosRepository extends PagingAndSortingRepository<Proyectos, Long>{

}
