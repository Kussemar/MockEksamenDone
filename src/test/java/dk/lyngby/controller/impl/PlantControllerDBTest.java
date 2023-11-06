package dk.lyngby.controller.impl;

import dk.lyngby.config.ApplicationConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.impl.PlantControllerDB;
import dk.lyngby.dao.IPlantDAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import io.javalin.Javalin;
import org.eclipse.jetty.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PlantControllerDBTest {

    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/api/v1";
   private static IPlantDAO plantDAO;
   private static EntityManagerFactory emfTest;

   private static Reseller r1, r2, r3;
   private static Plant p1, p2, p3, p4, p5;

   @BeforeAll
   static void beforeAll() {
       HibernateConfig.setTest(true);
       emfTest = HibernateConfig.getEntityManagerFactory();
       PlantControllerDB plantControllerDB = new PlantControllerDB();
       app = Javalin.create();
       ApplicationConfig.startServer(app, 7777);
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
    void read() {
       // given, when, then
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plants/" + p1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200)
                .body("id",equalTo(p1.getId()));
    }

    @Test
    void readAll() {
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plants")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200)
                .body("size()", is(5));
    }


    @Test
    void create() {
        PlantDTO plant6 = new PlantDTO("Flower", "Daisy", 100, 50.5f);
        given()
                .contentType("application/json")
                .body(plant6)
                .when()
                .post(BASE_URL + "/plants")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED_201)
                .body("name", equalTo(plant6.getName()));
    }


    @Test
    void update() {

        p1.setName("UpdatedRose");
        PlantDTO plantDTO = new PlantDTO(p1);
        given()
                .contentType("application/json")
                .body(plantDTO)
                .when()
                .put(BASE_URL + "/plants/" + plantDTO.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200)
                .body("name", equalTo("UpdatedRose"));
    }


    @Test
    void delete() {
        given()
                .contentType("application/json")
                .when()
                .delete(BASE_URL + "/plants/" + p1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200);

        // Additionally, you might want to check that the plant doesn't exist anymore
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plants/" + p1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND_404);
    }


    @Test
    void getPlantByType() {
        List<PlantDTO> plants = given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plants/type/Rose")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200)
//                .body("size()", is(2))  // based on your setup, there are two "Rose" type plants.
//                .body("[0].type", equalTo("Rose"));
                .extract().body().jsonPath().getList("", PlantDTO.class);

        assertEquals(2, plants.size());
        assertEquals("Rose", plants.get(0).getPlantType());

    }

}