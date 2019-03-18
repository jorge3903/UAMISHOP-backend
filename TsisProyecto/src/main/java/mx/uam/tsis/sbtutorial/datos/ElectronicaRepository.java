package mx.uam.tsis.sbtutorial.datos;

import org.springframework.data.repository.PagingAndSortingRepository;

import mx.uam.tsis.sbtutorial.negocio.dominio.Electronica;

public interface ElectronicaRepository extends PagingAndSortingRepository<Electronica, Long>{

}
