/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.service;

import br.edu.unochapeco.unoheartserver.controller.EquipeController;
import br.edu.unochapeco.unoheartserver.model.Equipe;
import br.edu.unochapeco.unoheartserver.model.PacienteEquipe;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Eduardo
 */
@Path("/equipe")
public class EquipeService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EquipeService
     */
    public EquipeService() {
    }

    /**
     * Realiza login do membro da equipe.
     *
     * @param dados
     * @return
     */
    @PUT
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response realizarLogin(String dados) {

        try {
            System.out.println(dados);

            Equipe equipe = new Equipe().parse(dados);
            EquipeController controller = new EquipeController();

            ErrorHandling listaErros = controller.validarLogin(equipe);

            if (listaErros.getError().isEmpty()) {
                equipe = controller.realizarLogin(equipe);

                if (equipe != null) {
                    return Response.status(Response.Status.OK).entity(equipe).build();
                } else {
                    listaErros.addMessage("Login e/ou senha inválidos");

                    return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
                }
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cadastra novo membro da equipe.
     *
     * @param dados Conteúdo REST da classe Equipe
     * @return HTTP Status 200 - Retorna um objeto completo da classe
     * Administrador HTTP Status 202 - Retorna um objeto da classe ErrorHandling
     * contendo a lita de rejeições HTTP Status 500 - Retorna erro interno do
     * servidor
     */
    @POST
    @Path("/incluir")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response incluir(String dados) {

        try {
            System.out.println(dados);
            Equipe equipe = new Equipe().parse(dados);
            EquipeController controller = new EquipeController();
            ErrorHandling listaErros = controller.validarCadastro(equipe);

            if (listaErros.getError().isEmpty()) {
                equipe = controller.realizarCadastro(equipe);

                return Response.status(Response.Status.OK).entity(equipe).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }

        } catch (Exception ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Atualiza os dados do membro da equipe.
     *
     * @param dados Conteúdo REST da classe Equipe
     * @return HTTP Status 200 - Retorna um objeto completo da classe Equipe
     * HTTP Status 202 - Retorna um objeto da classe ErrorHandling contendo a
     * lita de rejeições HTTP Status 500 - Retorna erro interno do servidor
     */
    @POST
    @Path("/alterar")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response alterar(String dados) {

        try {
            Equipe equipe = new Equipe().parse(dados);
            EquipeController controller = new EquipeController();
            ErrorHandling listaErros = controller.validarCadastro(equipe);

            if (listaErros.getError().isEmpty()) {
                controller.alterarCadastro(equipe);

                return Response.status(Response.Status.OK).entity(equipe).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }

        } catch (Exception ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response listar(String dados) {

        try {
            System.out.println(dados);

            PacienteEquipe pacienteEquipe = new PacienteEquipe().parse(dados);

            EquipeController controller = new EquipeController();
            List<Equipe> itens = controller.listarEquipe(pacienteEquipe);

            return Response.status(Response.Status.OK).entity(itens).build();
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
