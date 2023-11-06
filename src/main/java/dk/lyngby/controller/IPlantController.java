package dk.lyngby.controller;

import dk.lyngby.dto.PlantDTO;
import dk.lyngby.exception.ApiException;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.List;

public interface IPlantController {
    void getAll(Context ctx) throws ApiException;

    void getById(Context ctx) throws ApiException;

    void getByType(Context ctx) throws ApiException;

    void add(Context ctx) throws ApiException;
}
