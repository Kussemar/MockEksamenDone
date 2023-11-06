package dk.lyngby.dao.impl;

import dk.lyngby.dao.IDao;
import dk.lyngby.dao.IPlantDAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Plant;
import dk.lyngby.model.Reseller;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PlantDAO implements IPlantDAO {
    private static PlantDAO instance;
    private static EntityManagerFactory emf;

    public static IPlantDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PlantDAO();
        }
        return instance;
    }


    @Override
    public PlantDTO read(Integer integer) throws ApiException {
        try (var em = emf.createEntityManager()) {
            var plant = em.find(PlantDTO.class, integer);
            if (plant == null) {
                throw new ApiException(404, "Plant not found");
            }
            return plant;
        }
    }

    @Override
    public List<PlantDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT p FROM Plant p", PlantDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public PlantDTO create(PlantDTO plantDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Plant p = new Plant(plantDTO);

            Reseller reseller = em.find(Reseller.class, plantDTO.getId());
            if (reseller == null) {
                throw new ApiException(404, "Reseller with id: " + plantDTO.getId() + " not found");
            }

            p.getResellers().add(reseller);
            em.persist(p);
            em.getTransaction().commit();
            return plantDTO;
        }
    }

    @Override
    public PlantDTO update(Integer integer, PlantDTO plantDTO) throws ApiException {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Plant plant = em.find(Plant.class, integer);

            plant.setName(plantDTO.getName());
            plant.setType(plantDTO.getPlantType());
            plant.setPrice(plantDTO.getPrice());

            em.merge(plant);
            em.getTransaction().commit();
            return plantDTO;
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
    public List<PlantDTO> getPlantByType(String type) {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT p FROM Plant p WHERE p.type = :type", PlantDTO.class);
            query.setParameter("type", type);
            return query.getResultList();
        }
    }
}
