package accesoadatos.hibernate.AD04Tarea;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.persistence.*;


@Entity
public class Aula {
	
	// Creamos las variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;	
	 
	// Creamos la asociacion uno a muchos de aula a equipos
	@OneToMany (mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
	
	private List<Equipo>equipos = new ArrayList<>(); // No se porque pero esta declaracion tiene que ir despues de la relacion
	
	
	// Creamos constructores. Tenemos que crear uno vacio	
	public Aula() {	
	}

	public Aula(String nombre) {
		super();
		this.nombre = nombre;
		this.equipos = equipos;
	}

	// Metemos los Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}
	
	// Metemos unos metodos para poder añadir y eliminar equipos
	public void addEquipo(Equipo equipo) {
		equipos.add(equipo);
		equipo.setAula(this); // PAra actualizar el departamento de los empleados
	}
	public void removeEquipo(Equipo equipo) {
		equipos.remove(equipo);
		equipo.setAula(this);
	}
	
	
	
	
}
