package dk.lyngby.controller.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IController;
import dk.lyngby.dao.IPlantDAO;
import dk.lyngby.dao.impl.PlantDAO;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.exception.Message;
import dk.lyngby.model.Plant;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.List;

// ALLE MINE EXEPCTIONS ER FJERNET, DA DE BLIVER HÅNDTERET I EXEPCTIONS-HANDLEREN :)
public class PlantControllerDB implements IController {

    private final IPlantDAO<Plant> dao;

    public PlantControllerDB() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = PlantDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) throws ApiException {

        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Plant plant = dao.read(id);
        if (plant != null) {
            ctx.json(new PlantDTO(plant));

        }
    }


    @Override
    public void readAll(Context ctx) throws ApiException {

        List<Plant> plants = dao.readAll();
        ctx.json(PlantDTO.toPlantDTOList(plants));

    }

    @Override
    public void create(Context ctx) throws ApiException {

            PlantDTO plant = ctx.bodyAsClass(PlantDTO.class);
            Plant createdPlant = new Plant(plant);
            PlantDTO createdPlantDTO = new PlantDTO(dao.create(createdPlant));
            ctx.status(201).json(createdPlantDTO);

    }

    @Override
    public void update(Context ctx) throws ApiException {

        int id = ctx.pathParamAsClass("id", Integer.class).get();
        PlantDTO updatedPlantDTO = ctx.bodyAsClass(PlantDTO.class);
        Plant updatedPlant = new Plant(updatedPlantDTO);

        Plant existingPlant = dao.read(id);
        if (existingPlant == null) {
          throw new ApiException(404, "Plant not found");

        }

        PlantDTO updatedPlantDTO_ = new PlantDTO(dao.update(id, updatedPlant));
        ctx.status(200).json(updatedPlantDTO_);

    }


    @Override
    public void delete(Context ctx) throws ApiException {

        int id = ctx.pathParamAsClass("id", Integer.class).get();
        dao.delete(id);
        ctx.status(200).json(new Message(200, "Plant deleted successfully", LocalDateTime.now().toString()));

    }

    // get plantbytype

    public void getPlantByType(Context ctx) throws ApiException {
        String type = ctx.pathParam("type");
        ctx.json(PlantDTO.toPlantDTOList(dao.getPlantByType(type)));

    }

}
