package cesur.examen.core.worker;

import java.util.Date;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno: Carlos Bustos
 * Fecha: 11/12/23
 *
 *  No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 *  En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 *  o para seguir la traza de ejecución.
 */
/**
 *  Services classes offers methods to our main application, and can
 *  use multiple methods from DAOs and Entities.
 *  It's a layer between Data Access Layer and Aplication Layer (where
 *  Main app and controllers lives)
 *  It helps to encapsulate multiple opperations with DAOs that can be
 *  reused in application layer.
 */


public class WorkerService {
    /*
    RenovateWorker() set "from" date of the worker with this dni to today's date.
    Remember Date().
    Returns the new updated worker, null if fails or dni doesn't exist.
    */
    private static final WorkerDAO workerDAO = new WorkerDAO();

    public static Worker renovateWorker(String dni){
        Worker out = null;

        //LLamamos al metodo del Dni
        Worker workerexistente = workerDAO.getWorkerByDNI(dni);

        //Le decimos que si es diferente de null al metodo, ponga una fecha nueva y añada una nueva linea.
        if (workerexistente != null) {
            workerexistente.setFrom(new Date());

            //Aqui actualiza al trabajador en la base de datos para cambiarle la fecha.
            WorkerDAO workerDAO = new WorkerDAO();
            out = workerDAO.update(workerexistente);
        }

        return out;
    }
}
