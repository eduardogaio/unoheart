/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.service;

import br.edu.unochapeco.unoheartserver.controller.HistoricoFrequenciaController;
import br.edu.unochapeco.unoheartserver.model.FrequenciaFiltroGrafico;
import br.edu.unochapeco.unoheartserver.model.HistoricoFrequencia;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Eduardo
 */
@Path("/frequenciacardiaca")
public class FrequenciaService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FrequenciaServiceResource
     */
    public FrequenciaService() {
    }

    @POST
    @Path("/incluir")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response incluir(String dados) {

        try {            
            HistoricoFrequencia frequencia = new HistoricoFrequencia().parse(dados);
            HistoricoFrequenciaController controller = new HistoricoFrequenciaController();
            ErrorHandling listaErros = controller.validarInclusao(frequencia);

            if (listaErros.getError().isEmpty()) {
                frequencia = controller.realizarInclusao(frequencia);

                return Response.status(Response.Status.OK).entity(frequencia).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }

        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/listar")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")    
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response listarFrequencia(String dados) {

        try {
            System.out.println(dados);
            HistoricoFrequenciaController controller = new HistoricoFrequenciaController();
            FrequenciaFiltroGrafico filtroGrafico = new FrequenciaFiltroGrafico().parse(dados);
            List<HistoricoFrequencia> itens = controller.listarFrequencia(filtroGrafico);

            return Response.status(Response.Status.OK).entity(itens).build();
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/remover")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response remover(String dados) {

        try {
            HistoricoFrequencia frequencia = new HistoricoFrequencia().parse(dados);
            HistoricoFrequenciaController controller = new HistoricoFrequenciaController();

            ErrorHandling listaErros = controller.remover(frequencia);

            if (listaErros.getError().isEmpty()) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
