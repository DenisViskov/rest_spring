package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.domain.Employee;

/**
 * Интерфейс реализующий способность
 *
 * @author Денис Висков
 * @version 1.0
 * @since 02.11.2020
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
