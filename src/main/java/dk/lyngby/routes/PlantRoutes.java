package dk.lyngby.routes;

import dk.lyngby.controller.impl.PlantController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantRoutes {
    private final PlantController controller = new PlantController();

    protected EndpointGroup getRoutes() {
        return () -> {
            path("/plants", () -> {
                get("/", controller::getAll);
                get("/type/{type}", controller::getByType);
                get("/{id}", controller::getById);
                post("/", controller::add);
            });
        };

    }
}
