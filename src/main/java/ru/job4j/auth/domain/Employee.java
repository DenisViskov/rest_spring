package ru.job4j.auth.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 02.11.2020
 */
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "inn")
    private int inn;
    @Column(name = "hiring_date")
    private LocalDate hiring_date;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Person> accounts;

    public Employee() {
        accounts = new HashSet<>();
    }

    public Employee(int id, String name, String lastName, int inn, LocalDate hiring_date) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.inn = inn;
        this.hiring_date = hiring_date;
        accounts = new HashSet<>();
    }

    public void addAccount(Person person) {
        accounts.add(person);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public LocalDate getHiring_date() {
        return hiring_date;
    }

    public void setHiring_date(LocalDate hiring_date) {
        this.hiring_date = hiring_date;
    }

    public Set<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Person> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                inn == employee.inn &&
                Objects.equals(name, employee.name) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(hiring_date, employee.hiring_date) &&
                Objects.equals(accounts, employee.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, inn, hiring_date, accounts);
    }
}
