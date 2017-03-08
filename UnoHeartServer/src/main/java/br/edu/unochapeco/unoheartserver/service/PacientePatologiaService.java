/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.service;

import br.edu.unochapeco.unoheartserver.controller.PacienteImagemController;
import br.edu.unochapeco.unoheartserver.controller.PacientePatologiaController;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacientePatologia;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Eduardo
 */
@Path("/pacientepatologia")
public class PacientePatologiaService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PacientePatologiaService
     */
    public PacientePatologiaService() {
    }

    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response listarPaciente(String dados) {

        try {
            Paciente paciente = new Paciente().parse(dados);
            PacientePatologiaController controller = new PacientePatologiaController();
            List<PacientePatologia> itens = controller.listarPacientePatologia(paciente);

            return Response.status(Response.Status.OK).entity(itens).build();
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
            PacientePatologia pacientePatologia = new PacientePatologia().parse(dados);
            PacientePatologiaController controller = new PacientePatologiaController();
            ErrorHandling listaErros = controller.validarCadastro(pacientePatologia);

            if (listaErros.getError().isEmpty()) {
                pacientePatologia = controller.realizarCadastro(pacientePatologia);

                return Response.status(Response.Status.OK).entity(pacientePatologia).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }

        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
