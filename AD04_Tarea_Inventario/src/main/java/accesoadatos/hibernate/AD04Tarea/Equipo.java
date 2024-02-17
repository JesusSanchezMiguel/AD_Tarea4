package accesoadatos.hibernate.AD04Tarea;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Equipos") // Cambiamos de nombre la tabla
public class Equipo {

	// Declaramos las variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int num_serie;

	@Temporal(TemporalType.DATE)
	private Date fechaAlta;

	private String caracteristicas;
	
	// Metemos la relacion muchos a uno que tendremos con los Aulas
	@ManyToOne
	@JoinColumn(name ="aula_id", foreignKey = @ForeignKey(name = "Aula_FK"))
	
	private Aula aula; // No se porque pero esto tiene que ir despues

	// Metemos los constructores. Hay que meter unos vacio.
	public Equipo() {
	}

	public Equipo(int num_serie, Date fechaAlta, String caracteristicas) {
		super();
		this.num_serie = num_serie;
		this.fechaAlta = fechaAlta;
		this.caracteristicas = caracteristicas;
	}

	// Metemos los getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum_serie() {
		return num_serie;
	}

	public void setNum_serie(int num_serie) {
		this.num_serie = num_serie;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	@Override
	public String toString() {
		return "Equipo [id=" + id + ", num_serie=" + num_serie + ", fechaAlta=" + fechaAlta + ", caracteristicas="
				+ caracteristicas + "]";
	}
	

}
