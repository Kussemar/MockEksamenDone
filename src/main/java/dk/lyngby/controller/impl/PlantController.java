
package dk.lyngby.controller.impl;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.IPlantController;
import dk.lyngby.dao.IPlantDAO;
import dk.lyngby.dao.impl.PlantDAOMock;
import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import dk.lyngby.exception.Message;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.time.LocalDateTime;
import java.util.List;

public class PlantController implements IPlantController {

    private final PlantDAOMock dao;

    public PlantController() {
        var emf = HibernateConfig.getEntityManagerFactory();
        dao = PlantDAOMock.getInstance(emf);
    }


    @Override
    public void getAll(Context ctx) throws ApiException {
        try {
            ctx.json(dao.readAll());
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new Message(500, "Internal server error while fetching all plants", LocalDateTime.now().toString()));
        }
    }

    @Override
    public void getById(Context ctx) throws ApiException {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            ctx.json(dao.read(id));
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Message(400, "Invalid ID format", LocalDateTime.now().toString()));

        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Internal server error while fetching plant by id", LocalDateTime.now().toString()));
        }

    }

    @Override
    public void getByType(Context ctx) throws ApiException {
        try {
            String type = ctx.pathParam("type");
            ctx.json(dao.getPlantByType(type));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new Message(500, "Internal server error while fetching plant by type", LocalDateTime.now().toString()));
        }

    }

    @Override
    public void add(Context ctx) throws ApiException {
        try {
            PlantDTO plant = ctx.bodyAsClass(PlantDTO.class);
            ctx.json(dao.create(plant));
        } catch (Exception e) {
            ctx.status(500);
            ctx.json(new Message(500, "Internal server error while adding plant", LocalDateTime.now().toString()));
        }


    }
}
