package dk.lyngby.config;



import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Reseller reseller = new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334");
            Reseller reseller2 = new Reseller("Glostrup Planter", "Tværvej 35", "32233232");
            Reseller reseller3 = new Reseller("Holbæk Planteskole", "Stenhusvej 49", "59430945");

            Plant plant = new Plant("Rose", "Albertine", 400, 199.5f);
            Plant plant2 = new Plant("Bush", "Aronia", 400, 169.5f);
            Plant plant3 = new Plant("FruitsAndBerries", "AromaApple", 350, 399.5f);
            Plant plant4 = new Plant("Rhododendron", "Astrid", 40, 269.5f);
            Plant plant5 = new Plant("Rose", "The DarkLady", 100, 199.5f);

            reseller.addPlant(plant);
            reseller.addPlant(plant2);
            reseller.addPlant(plant3);

            reseller2.addPlant(plant4);
            reseller2.addPlant(plant5);

            reseller3.addPlant(plant);
            reseller3.addPlant(plant2);

            em.persist(reseller);
            em.persist(reseller2);
            em.persist(reseller3);

            em.getTransaction().commit();
        }
    }
}