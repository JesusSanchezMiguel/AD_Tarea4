package accesoadatos.hibernate.AD04Tarea;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import javax.persistence.*;



public class Main {

	// Variables de clase.

	static Scanner entrada = new Scanner(System.in);	

	// Nos creamos el EntityManagerFactory. Lo importante es el nombre
	// TareaInventario, que tiene que ser igual que hemos puesto en el archivo de
	// persistencia
	// Tambien hemos cambiado la conexion con la base de datos para indicar que
	// estamos conectados a la BD Inventario
	// En el persinstence ponemos la BD inventario y en Database Connection tambien

	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("TareaInventario");
	static EntityManager em = emf.createEntityManager();

	public static void main(String[] args) {
		
		// Declaro variables de utilidades

		int opcion = 1;
		boolean salir = false;
		Calendar fAlta = Calendar.getInstance();

		// Me construyo el menu:

		while (!salir) {
			System.out.println("Seleccione la opcion deseada:---------------------------");
			System.out.println("1: Listado de aulas y sus equipos");
			System.out.println("2: Insertar Aula");
			System.out.println("3: Borrar un Aula");
			System.out.println("4: Modificar un aula");
			System.out.println("5: Insertar un equipo y añadirlo a un aula");
			System.out.println("6: Buscar un equipo por su numero de serie");
			System.out.println("7: Salir");
			opcion = entrada.nextInt();

			// Creamos el menu de trabajo
			switch (opcion) {
			case 1: // ---------------------------------------------------------------------------------------------------------------------------------------------------

				System.out.println("1: Listado de aulas y sus equipos");
				List<Aula> aulas = new ArrayList<>();

				em.getTransaction().begin();  // No tengo claro si para realizar una consulta tengo que hacer una transaccion. ???????????????????
				Query query1 = em.createQuery("FROM Aula a");
				aulas = (List<Aula>) query1.getResultList();
				em.getTransaction().commit();

				for (Aula a : aulas) {
					System.out.println("Aula " + a.getId() + " De nombre " + a.getNombre());
					System.out.println("Tiene los equipos: ------");

					for (Equipo e : a.getEquipos()) {  // Obtenemos los equipos a traves del aula actual del bucle
						System.out.println(e.getId() + " Con fecha de alta " + e.getFechaAlta()
								+ " y Con estas caracteristicas: " + e.getCaracteristicas());
					}
					System.out.println("------------------------");
				}
				break;

			case 2: // ----------------------------------------------------------------------------------------------------------------------------------------------------

				System.out.println("2: Insertar un Aula");
				boolean fin2 = false;
				String nAulaNuevo = "";
				System.out.println("Inserte el nombre del Aula deseado ");
				nAulaNuevo = entrada.next();

				Aula aulaNueva = new Aula(nAulaNuevo);

				em.getTransaction().begin();
				em.persist(aulaNueva); // Metemeos el aula en la base de datos
				em.getTransaction().commit();

				break;

			case 3: // ---------------------------------------------------------------------------------------------------------------------------------------------------
				System.out.println("3: Borrar un Aula");
				int idAulaBorrar;
				boolean fin3 = false;

				while (!fin3) {
					System.out.println("Introduzca el Id del aula a borrar");
					idAulaBorrar = entrada.nextInt();
					Aula AulaBuscar = em.find(Aula.class, idAulaBorrar);  

					if (AulaBuscar == null) {
						System.out.println("El ID no se encuentra en la base de datos, por favor introduzca otro Id");
					} else {
						
						em.getTransaction().begin();
						em.remove(AulaBuscar);
						em.flush();
						em.getTransaction().commit();

						System.out.println("El aula borrado  es: " + AulaBuscar.getNombre());
						fin3 = true;
					}
				}
				break;

			case 4:// -----------------------------------------------------------------------------------------------------------------------------------------------------

				System.out.println("4: Modificar un aula");
				int idAulaBuscar = 0;
				String nombreAula = null;
				boolean fin4 = false;
				while (!fin4) {
					System.out.println("Introduzca el Id del aula a modificar");
					idAulaBuscar = entrada.nextInt();
					Aula AulaBuscar = em.find(Aula.class, idAulaBuscar);
					if (AulaBuscar == null) {
						System.out.println("El ID no se encuentra en la base de datos, por favor introduzca otro Id");
					} else {
						System.out.println("Introduzca el nuevo nombre del aula");
						nombreAula = entrada.next();
						AulaBuscar.setNombre(nombreAula);

						em.getTransaction().begin();
						em.persist(AulaBuscar);
						em.flush(); // Se actualizan los datos en la BBDD
						em.getTransaction().commit(); // Confirmamos los cambios y finalizo la transaccion
						
						System.out.println("Se ha modificado el aula ");
						fin4 = true;
					}
				}
				break;

			case 5:// -------------------------------------------------------------------------------------------------------------------------------------------------------

				System.out.println("5: Insertar un equipo y añadirlo a un aula");

				System.out.println("Introduzca el numero de serie del equipo");
				int numSerie = entrada.nextInt();

				System.out.println("Introduzca el año de alta ");
				int anyo = entrada.nextInt();

				System.out.println("Introduzca el mes de alta");
				int mes = entrada.nextInt();

				System.out.println("Introduzca el dia de alta");
				int dia = entrada.nextInt();

				System.out.println("Introduzca las caracteristicas del equipo");
				String caracteristicas = entrada.next();

				// Llamo al metodo que me devuelve una lista de aulas
				List<Aula> Aulas = listaAulas();
				boolean fin5 = false;

				while (!fin5) { // Bucle para mostrar los aulas y seleccionar uno valido
					System.out.println("Seleccione un aula disponible de los siguientes:");
					System.out.println("--------------------------------------------------");
					for (Aula a : Aulas) {
						System.out.println("Id aula: " + a.getId() + ", nombre: " + a.getNombre());
					}

					int idAulaBuscar1 = entrada.nextInt();
					// Buscamos el aula selecciona
					Aula AulaBuscar = em.find(Aula.class, idAulaBuscar1);
					if (AulaBuscar == null) {
						System.out.println("El ID no se encuentra en la base de datos, por favor introduzca otro Id");
						fin5 = false;
					} else {
						System.out.println("En aula es correcto");
						
						Aula aulaEquipo = AulaBuscar; // Tenemos el aula
						fAlta.set(anyo, mes, dia);
						Equipo equipoNuevo = new Equipo(numSerie, fAlta.getTime(), caracteristicas); // Tenemos el equipo
						aulaEquipo.addEquipo(equipoNuevo); // Metemos el equipo en el aula a traves del metodo de la clase Aula

						em.getTransaction().begin();
						em.persist(aulaEquipo);
						em.flush(); // Se actualizan los datos en la BBDD
						em.getTransaction().commit(); // Confirmamos los cambios y finalizo la transaccion

						System.out.println("Equipo insertado correctamente");
						fin5 = true;
					}
				}
				break;

			case 6:// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

				System.out.println("6: Buscar un equipo por su numero de serie");
				boolean fin6 = false;

				while (!fin6) {

					System.out.println("Introduzca el numero de serie del equipo");
					int numSerieBuscar = entrada.nextInt();

					// Lanzamos la consulta para buscar el equipo por su numero de serie:

					Query query6 = em.createQuery("FROM Equipo e where e.num_serie = :ns");
					query6.setParameter("ns", numSerieBuscar); // Igual que los preparedStatement
					// Volcamos el resultado en el ResultList que nos devuelve la Query
					List<Equipo> EquipoResult = (List<Equipo>) query6.getResultList(); // Hay que hacer un casting

					// Analizamos el resultado.
					Equipo equipoEncontrado = null;
					if (!EquipoResult.isEmpty()) { // Verificamos que la lista devuelta no esta vacia
						equipoEncontrado = EquipoResult.get(0); // Como la lista tiene solo un elemento, estara en el  indice 0
						System.out.println("El equipo encontrado es: " + equipoEncontrado.getId() + ", Num Serie: "
								+ equipoEncontrado.getNum_serie() + ", Caracteristitas; "
								+ equipoEncontrado.getCaracteristicas());
						System.out.println("Y pertenece al aula: " + equipoEncontrado.getAula().getNombre());
						// Hemos sacado el nombre del aula a traves de la referencia aula de la clase equipo.
						fin6 = true;
					} else {
						System.out.println("El equipo con el numero de serie " + numSerieBuscar + " no existe");
						fin6 = false;						
					}
				}

				break;

			case 7:// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				System.out.println("7: Salir");
				salir = true;

				em.close();
				emf.close();
				break;

			default:
				System.out.println("Mete algo correcto por favor");
				break;
			}

		}
		 /* 1: Si borraros un aula se borran sus equipos porque en la clase Aula, hemos puesto el parametro orphanRemoval a true.
		  * 
		  * 2: La relacion es bidireccional ya que se ha realizado asi en las asociaciones de las clases aula y equipo
		  * 
		  * 3: La propiedad hbm2ddl.auto significa el comportamiento que va a tener hibernate respecto de las tablas cuando se ejecute el programa.
		  *          Podemos tener valores: Create: Se crean la tablas nuevamente, con lo que los valores anteriores se borrarian
		  *                                 Create-only: Solo crea las tablas nuevas y no borra las tablas existentes.
		  *                                 Create-drop: Crea las tablas al inicio de la aplicacion y las borra cuando finaliza 
		  *                                 Validate: Comprueba la validez de las tablas. No se muy bien lo que hace la verdad.
		  *                                 update: Las tablas no se borrarian cada vez que se ejecute el programa.
		  *                                 
		  * 4: .show_sql a false no nos muestra las consultas sql realizadas por hibernate y nos deja la consola limpia para nuestra aplicacion
		  * 		
          */
		
		
		
	}
	
	// Hacemos un metodo para que me devuelva una lista de aulas
	static public List<Aula> listaAulas() {
		List<Aula> aulas10 = new ArrayList<>();
		em.getTransaction().begin();

		Query query10 = em.createQuery("FROM Aula a");
		aulas10 = (List<Aula>) query10.getResultList();
		em.getTransaction().commit();

		return aulas10;
	}

	// Hacemos un metodo para que me devuelva una lista de equipos. No lo he usado 

	static public List<Equipo> listaEquipos() {
		List<Equipo> equipos11 = new ArrayList<>();
		em.getTransaction().begin();

		Query query11 = em.createQuery("FROM Aula a");
		equipos11 = (List<Equipo>) query11.getResultList();
		em.getTransaction().commit();

		return equipos11;
	}

}
