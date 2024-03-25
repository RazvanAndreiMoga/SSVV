package ssvv;

import domain.Tema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import repository.TemaXMLRepo;
import service.Service;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddAssignmentTest {

    private TemaXMLRepo temaFileRepository;
    private TemaValidator temaValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/temeTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<inbox>\n" +
                    "\n" +
                    "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setup() {
        this.temaFileRepository = new TemaXMLRepo("fisiere/temeTest.xml");
        this.temaValidator = new TemaValidator();
        this.service = new Service(null, null, this.temaFileRepository, this.temaValidator, null, null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/temeTest.xml").delete();
    }

    /*
    TC 1
     */
    @Test
    void testAddAssignmentEmptyID() {
        Tema newTema = new Tema("", "a", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    /*
    TC 2
     */
    @Test
    void testAddAssignmentNullID() {
        Tema newTema = new Tema(null, "a", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    /*
    TC 3
     */
    @Test
    void testAddAssignmentEmptyDescription() {
        Tema newTema = new Tema("1", "", 1, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    /*
    TC 4
     */
    @Test
    void testAddAssignmentDeadlineTooLarge() {
        Tema newTema = new Tema("1", "a", 15, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    /*
    TC 5
     */
    @Test
    void testAddAssignmentDeadlineTooSmall() {
        Tema newTema = new Tema("1", "a", 0, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    /*
    TC 6
     */
    @Test
    void testAddAssignmentReceivedTooSmall() {
        Tema newTema = new Tema("1", "a", 1, 0);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    /*
    TC 7
     */
    @Test
    void testAddAssignmentReceivedTooLarge() {
        Tema newTema = new Tema("1", "a", 1, 15);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }


    /*
    TC 8
     */
    @Test
    void testAddAssignmentValidAssignment() {
        Tema newTema = new Tema("1", "a", 1, 1);
        this.service.addTema(newTema);
        assertEquals(this.service.getAllTeme().iterator().next(), newTema);
    }

    /*
    TC 9
     */
    @Test
    void testAddAssignmentDuplicateAssignment() {
        Tema newTema = new Tema("1", "a", 1, 1);
        this.service.addTema(newTema);

        Tema newTema2 = new Tema("1", "a", 1, 1);

        assertEquals(this.service.addTema(newTema2).getID(), newTema.getID());
    }

}
