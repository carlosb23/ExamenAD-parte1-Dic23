package cesur.examen.core.common;

import java.util.List;

/**
 * EXAMEN DE ACCESO A DATOS
 * Diciembre 2023
 *
 * Nombre del alumno: Carlos Bustos Torralbo
 * Fecha: 11/12/23
 */
public interface DAO<T> {
    /* Basic CRUD operations */
    /* All write methods return the updated object */
    T save( T t);
    T update( T t);

    /*
     * remove() returns true if object is successfully deleted.
     * Otherwise, returns false
     */
    boolean remove(T t);

    T get( Long id);
    List<T> getAll();

}
