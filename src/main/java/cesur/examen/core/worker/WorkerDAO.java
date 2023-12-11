package cesur.examen.core.worker;

import cesur.examen.core.common.DAO;
import cesur.examen.core.common.JDBCUtils;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno: Carlos Bustos
 * Fecha: 11/12/23
 *
 * No se permite escribir en consola desde las clases DAO, Service y Utils usando System.out.
 * En su lugar, usa log.info(), log.warning() y log.severe() para mostrar información interna
 * o para seguir la traza de ejecución.
 */
@Log public class WorkerDAO implements DAO<Worker> {

    /* Please, use this constants for the queries */
    private final String QUERY_ORDER_BY = "Select * from trabajador order by desde";
    private final String QUERY_BY_DNI = "Select * from trabajador where dni=?";
    private final String UPDATE_BY_ID = "Update trabajador Set nombre=?, dni=?, desde=? where id=?";

    @Override
    public Worker save(Worker worker) {
        return null;
    }

    /**
     * Update Worker in database.
     * Remember that date objects in jdbc should be converted to sql.Date type.
     * @param worker
     * @return the updated worker or null if the worker does not exist in database.
     */
    @Override
    public Worker update(Worker worker) {
        Worker out = null;

        try (PreparedStatement pst = JDBCUtils.getConn().prepareStatement(UPDATE_BY_ID)) {
            pst.setString(1, worker.getName());
            pst.setString(2, worker.getDni());
            pst.setDate(3, JDBCUtils.dateUtilToSQL(worker.getFrom()));
            pst.setLong(4, worker.getId());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                out = worker;
            }
        } catch (SQLException e) {
            log.severe("Error in update()");
            throw new RuntimeException(e);
        }

        return out;
    }

    @Override
    public boolean remove(Worker worker) {
        return false;
    }

    @Override
    public Worker get(Long id) {
        return null;
    }

    /**
     * Retrieve the worker that has this dni. Null if there is no wrker.
     * @param dni
     * @return
     */
    public Worker getWorkerByDNI(String dni) {

        /* Implemented for your pleasure */

        if( JDBCUtils.getConn()==null){
            throw new RuntimeException("Connection is not created!");
        }

        Worker out = null;

        try( PreparedStatement pst = JDBCUtils.getConn().prepareStatement(QUERY_BY_DNI) ){
            pst.setString(1,dni);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                Worker w = new Worker();
                w.setId( rs.getLong("id") );
                w.setName( rs.getString("nombre"));
                w.setDni( rs.getString("dni"));
                w.setFrom( rs.getDate("desde"));
                out=w;
            }
        } catch (SQLException e) {
            log.severe("Error in getWorkerById()");
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public List<Worker> getAll() {
        return null;
    }

    /**
     * Get a list with all workers, ordered by from column.
     * The first is the oldest worker and the last are the newest.
     * If there is no worker, the list is empty.
     * @return
     */
    public List<Worker> getAllOrderByFrom(){
        ArrayList<Worker> out = new ArrayList<>(0);

        /* Make implementation here ...  */
        try (Statement pst = JDBCUtils.getConn().createStatement()){
            ResultSet rs = pst.executeQuery(QUERY_ORDER_BY);

            while (rs.next()){
                Worker worker = new Worker();
                worker.setId(rs.getLong("id"));
                worker.setName(rs.getString("nombre"));
                worker.setDni(rs.getString("dni"));
                worker.setFrom(rs.getDate("desde"));
                out.add(worker);
            }
            log.info("Trabajadores añadidos correctamente");
        } catch (SQLException e) {
            log.severe("Error al rellenar a los trabajadores");
            throw new RuntimeException(e);
        }
        return out;
    }
}
