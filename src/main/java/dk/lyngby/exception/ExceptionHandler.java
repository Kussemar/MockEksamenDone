package dk.lyngby.exception;

import io.javalin.http.Context;

import java.time.LocalDateTime;


public class ExceptionHandler
{
    public void apiExceptionHandler(ApiException e, Context ctx)
    {
        ctx.status(e.getStatusCode());
        ctx.json(new Message(e.getStatusCode(), e.getMessage(), LocalDateTime.now().toString()));
    }

    public void exceptionHandler(Exception e, Context ctx)
    {
        ctx.status(500);
        ctx.json(new Message(500, e.getMessage(), LocalDateTime.now().toString()));
    }
}
