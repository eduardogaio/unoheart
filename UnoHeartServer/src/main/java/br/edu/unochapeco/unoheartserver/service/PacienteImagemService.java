/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.service;

import br.edu.unochapeco.unoheartserver.controller.PacienteImagemController;
import br.edu.unochapeco.unoheartserver.model.PacienteImagem;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Eduardo
 */
@Path("/pacienteimagem")
public class PacienteImagemService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PacienteImagemService
     */
    public PacienteImagemService() {
    }

    @GET
    @Path("/listar/{paciente}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response listart(@PathParam("paciente") String paciente) {

        try {
            PacienteImagemController controller = new PacienteImagemController();
            PacienteImagem pacienteImagem = controller.selecionarPacienteImagem(paciente);
            if (pacienteImagem != null) {
                return Response.status(Response.Status.OK).entity(pacienteImagem).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/incluir")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response incluir(String dados) {

        try {            
            PacienteImagem pacienteImagem = new PacienteImagem().parse(dados);
            PacienteImagemController controller = new PacienteImagemController();
            ErrorHandling listaErros = controller.validarCadastro(pacienteImagem);

            if (listaErros.getError().isEmpty()) {
                pacienteImagem = controller.realizarCadastro(pacienteImagem);

                return Response.status(Response.Status.OK).entity(pacienteImagem).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }

        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
