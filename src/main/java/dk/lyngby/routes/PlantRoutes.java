package dk.lyngby.routes;

import dk.lyngby.controller.impl.PlantController;
import dk.lyngby.controller.impl.PlantControllerDB;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantRoutes {
    private final PlantControllerDB controller = new PlantControllerDB();

    protected EndpointGroup getRoutes() {
        return () -> {
            path("/plants", () -> {
                get("/", controller::readAll);
                get("/type/{type}", controller::getPlantByType);
                get("/{id}", controller::read);
                delete("/{id}", controller::delete);
                put("/{id}", controller::update);
                post("/", controller::create);
            });
        };

    }
}
