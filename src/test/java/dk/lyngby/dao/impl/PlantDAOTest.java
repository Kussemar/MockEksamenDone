package dk.lyngby.dao.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.impl.PlantControllerDB;
import dk.lyngby.dao.IPlantDAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlantDAOTest {

    private static IPlantDAO<Plant> plantDAO;
    private static EntityManagerFactory emfTest;

    private static Reseller r1, r2, r3;
    private static Plant p1, p2, p3, p4, p5;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        plantDAO = PlantDAO.getInstance(emfTest);

    }

    @BeforeEach
    void setUp() {
        r1 = new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334");
        r2 = new Reseller("Glostrup Planter", "Tværvej 35", "32233232");
        r3 = new Reseller("Holbæk Planteskole", "Stenhusvej 49", "59430945");

        p1 = new Plant("Rose", "Albertine", 400, 199.5f);
        p2 = new Plant("Bush", "Aronia", 400, 169.5f);
        p3 = new Plant("FruitsAndBerries", "AromaApple", 350, 399.5f);
        p4 = new Plant("Rhododendron", "Astrid", 40, 269.5f);
        p5 = new Plant("Rose", "The DarkLady", 100, 199.5f);

        // Using EntityManager to persist entities
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();

            // Optionally clear previous test data
            em.createQuery("DELETE FROM Plant p").executeUpdate();
            em.createQuery("DELETE FROM Reseller r").executeUpdate();

            // Reset sequences
            em.createNativeQuery("ALTER SEQUENCE plants_plant_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE resellers_reseller_id_seq RESTART WITH 1").executeUpdate();

            // Set up relationships and persist
            r1.addPlant(p1);
            r2.addPlant(p2);
            r3.addPlant(p3);
            r1.addPlant(p4);
            r2.addPlant(p5);


            em.persist(r1);
            em.persist(r2);
            em.persist(r3);
/*
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
*/

            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown(){
        HibernateConfig.setTest(false);
    }


    @Test
    void read() throws ApiException {
    Plant plantActually = plantDAO.read(p1.getId());
    assertEquals(p1, plantActually);
    }

    @Test
    void readAll() throws ApiException {
        List<Plant> plantListExpected = new ArrayList<>(List.of( (p1),(p2), (p3),(p4),(p5)));

        List<Plant> plantListActually = plantDAO.readAll();
        for (int i = 0; i < plantListExpected.size(); i++) {

            Plant plantExpected = plantListExpected.get(i);
            Plant plantActually = plantListActually.get(i);
            assertEquals(plantExpected.getId(), plantActually.getId());
        }
        //assertEquals(plantListExpected, plantListActually);
    }

    @Test
    void create() throws ApiException {
        Plant plantExpected = new Plant("Rose", "Albertine", 400, 199.5f);

        plantExpected = plantDAO.create(plantExpected);


        Plant plantActually = plantDAO.read(plantExpected.getId());

        assertEquals(plantExpected, plantActually);
    }

    @Test
    void update() throws ApiException {
    Plant expected = plantDAO.read(p1.getId());
    expected.setName("The Expected Plant");
    expected = plantDAO.update(p1.getId(), expected);

    Plant actual = plantDAO.read(p1.getId());

    assertEquals(expected, actual);


    }

    @Test
    void delete() throws ApiException {
        plantDAO.delete(p1.getId());
        assertThrows(ApiException.class, () -> plantDAO.read(p1.getId()));


    }

    @Test
    void getPlantByType() {
        List<Plant> plantListExpected = new ArrayList<>(List.of( (p1),(p5)));

        List<Plant> plantListActually = plantDAO.getPlantByType("Rose");
        for (int i = 0; i < plantListExpected.size(); i++) {

            Plant plantExpected = plantListExpected.get(i);
            Plant plantActually = plantListActually.get(i);
            assertEquals(plantExpected.getType(), plantActually.getType());
        }
    }
}