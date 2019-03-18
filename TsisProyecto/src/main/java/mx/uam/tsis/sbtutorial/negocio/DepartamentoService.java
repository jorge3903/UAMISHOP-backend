package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.DepartamentoRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Departamento;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository repository;
	@Autowired
	private UsuarioService servicioUsuario;
	
	public Iterable<Departamento> dameDepartamentos() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public Iterable<Departamento> departamentoPorPagina(int pageNumber, int pageSize){
	      PageRequest pageRequest = new PageRequest(pageNumber,pageSize);
	      Iterable<Departamento> res = repository.findAll(pageRequest);
	      return  res;
	   }
	
	public Departamento agregarDepartamento(Departamento departamento) {
		// TODO Auto-generated method stub
		return repository.save(departamento);
	}
	
	public boolean agregarDueño(long idUsuario,Departamento departamento) {
		Usuario usuario = servicioUsuario.agregarProducto(idUsuario, departamento);
		if(usuario!=null) {
			departamento.setUsuario(usuario);
			if(repository.save(departamento)!=null) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public boolean modificaDepartamento(Long idUsuario,Long idDepartamento,String nombre,Double precio,String descripcion,String ubicacion) {
		Usuario usuario = servicioUsuario.usuarioById(idUsuario);
		if(usuario!= null) {
			for(Long idProd:usuario.getProductos()) {
				if(idProd == idDepartamento) {
					Departamento departamento= repository.findOne(idDepartamento);
					if(departamento!=null) {
						departamento.setNombre(nombre);
						departamento.setPrecio(precio);
						departamento.setDescripcion(descripcion);
						departamento.setUbicacion(ubicacion);
						repository.save(departamento);
						return true;
					}else {
						return false;
					}
				}
			}
			return false;
		}else {
			return false;
		}
	}
	
	public boolean eliminarDepartamento(Long idDepartamento) {
		repository.delete(idDepartamento);
		return true;
	}
}
