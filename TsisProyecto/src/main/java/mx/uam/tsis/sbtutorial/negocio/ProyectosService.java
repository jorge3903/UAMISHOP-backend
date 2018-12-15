package mx.uam.tsis.sbtutorial.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.sbtutorial.datos.ProyectosRepository;
import mx.uam.tsis.sbtutorial.negocio.dominio.Proyectos;
import mx.uam.tsis.sbtutorial.negocio.dominio.Usuario;

@Service
public class ProyectosService {

		@Autowired
		private ProyectosRepository repository;
		@Autowired
		private UsuarioService servicioUsuario;

		public Iterable<Proyectos> dameProyectos() {
			// TODO Auto-generated method stub
			return repository.findAll();
		}
		
		public boolean agregarDueño(long idUsuario,Proyectos proyecto) {
			Usuario usuario = servicioUsuario.agregarProducto(idUsuario, proyecto);
			proyecto.setUsuario(usuario);
			if(repository.save(proyecto)!=null) {
				return true;
			}else {
				return false;
			}
		}

		public Proyectos agregarProyecto(Proyectos pys) {
			// TODO Auto-generated method stub
			return repository.save(pys);
		}

		public boolean actualizarProyecto(Long idpys) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean eliminarProyecto(Long idpys) {
			// TODO Auto-generated method stub
			return false;
		}
		
		public Proyectos ProyectoById(Long idpys) {
			// TODO Auto-generated method stub
			
			for(Proyectos pysActual : repository.findAll()) {
				
				if (pysActual.getId() == idpys ){
					return pysActual;
				}
		    }
			
			return null;
		}
}
