package mx.uam.tsis.sbtutorial.datos;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Otros;

public interface OtrosRepository extends PagingAndSortingRepository<Otros, Long>{

}
