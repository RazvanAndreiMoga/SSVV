package ssvv;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntegration {
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File xml2 = new File("fisiere/temeTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml2))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File xml3 = new File("fisiere/noteTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml3))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo("fisiere/temeTest.xml");
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo("fisiere/noteTest.xml");
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @AfterAll
    public static void teardown() {

        new File("fisiere/studentiTest.xml").delete();
        new File("fisiere/temeTest.xml").delete();
        new File("fisiere/noteTest.xml").delete();
    }

    @Test
    public void testAddStudent() {
        Student student = new Student("333", "Ana", 931, "ana@gmail.com");
        assertNull(service.addStudent(student));
    }

    @Test
    public void testAddTema() {
        Tema tema = new Tema("333", "a", 1, 1);
        assertNull(service.addTema(tema));
    }

    @Test
    public void testAddGrade() {

        Nota nota = new Nota("333", "333", "333", 10, LocalDate.now());
        assertEquals(service.addNota(nota, "bine"), 10.0);

        service.deleteNota("333");
        service.deleteStudent("333");
        service.deleteTema("333");
    }

    @Test
    public void testAddStudentTemaGrade() {

        Student student = new Student("222", "Ana", 931, "ana@gmail.com");
        Tema tema = new Tema("222", "a", 1, 1);
        Nota nota = new Nota("222", "222", "222", 10, LocalDate.now());

        assertNull(service.addStudent(student));
        assertNull(service.addTema(tema));
        assertEquals(service.addNota(nota, "bine"), 10.0);

        service.deleteNota("333");
        service.deleteStudent("333");
        service.deleteTema("333");
    }

}

