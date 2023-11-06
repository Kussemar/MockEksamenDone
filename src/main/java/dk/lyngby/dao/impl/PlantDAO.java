package dk.lyngby.dao.impl;

import dk.lyngby.dao.IDao;
import dk.lyngby.dao.IPlantDAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import dk.lyngby.routes.Routes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlantDAO implements IPlantDAO<Plant> {
    private static PlantDAO instance;
    private static EntityManagerFactory emf;
    // Til logger, hvis vi skal bruge det
   private final Logger LOGGER = LoggerFactory.getLogger(PlantDAO.class);

    public static IPlantDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PlantDAO();
        }
        return instance;
    }


    @Override
    public Plant read(Integer integer) throws ApiException {
        try (var em = emf.createEntityManager()) {
            var plant = em.find(Plant.class, integer);
            if (plant == null) {
               ApiException e  =   new ApiException(404, "Plant not found");
               LOGGER.error("Plant not found. \n ", e);
               throw e;
            }
            return plant;
        }
    }

    @Override
    public List<Plant> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT p FROM Plant p", Plant.class);
            return query.getResultList();
        }
    }

    @Override
    public Plant create(Plant plant) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(plant);
            em.getTransaction().commit();

            //LOGGER.info("Plant created with id: " + plant.getId());

            return plant;

        }
    }

    @Override
    public Plant update(Integer integer, Plant plant) throws ApiException {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Plant plant_ = em.find(Plant.class, integer);

            plant_.setName(plant.getName());
            plant_.setType(plant.getType());
            plant_.setPrice(plant.getPrice());

            em.merge(plant_);
            em.getTransaction().commit();
            return plant_;
        }
    }

    @Override
    public void delete(Integer id) throws ApiException {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            var plant = em.find(Plant.class, id);
            if (plant == null) {
                throw new ApiException(404, "Plant with id: " + id + " not found");

            }
            em.remove(plant);
            em.getTransaction().commit();
        }
    }


    @Override
    public List<Plant> getPlantByType(String type) {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT p FROM Plant p WHERE p.type = :type", Plant.class);
            query.setParameter("type", type);
            return query.getResultList();
        }
    }

}
