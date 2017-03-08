/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.service;

import br.edu.unochapeco.unoheartserver.controller.PacienteController;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacienteEquipe;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * REST Web Service
 *
 * @author Eduardo
 */
@Path("/paciente")
public class PacienteService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PacienteService
     */
    public PacienteService() {
    }

    /**
     * Realiza o login para acessar o sistema
     *
     * @param dados Conteúdo REST da classe Paciente
     * @return HTTP Status 200 - Retorna um objeto completo da classe Paciente
     * HTTP Status 202 - Retorna um objeto da classe ErrorHandling contendo a
     * lita de rejeições HTTP Status 500 - Retorna erro interno do servidor
     */
    @PUT
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response realizarLogin(String dados) {

        try {
            System.out.println(dados);

            Paciente paciente = new Paciente().parse(dados);
            PacienteController controller = new PacienteController();

            ErrorHandling listaErros = controller.validarLogin(paciente);

            if (listaErros.getError().isEmpty()) {
                paciente = controller.realizarLogin(paciente);

                if (paciente != null) {
                    return Response.status(Response.Status.OK).entity(paciente).build();
                } else {
                    listaErros.addMessage("Login e/ou senha inválidos");

                    return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
                }
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cadastra novo paciente
     *
     * @param dados Conteúdo REST da classe Paciente
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
            Paciente paciente = new Paciente().parse(dados);
            PacienteController controller = new PacienteController();
            ErrorHandling listaErros = controller.validarCadastro(paciente);

            if (listaErros.getError().isEmpty()) {
                paciente = controller.realizarCadastro(paciente);

                return Response.status(Response.Status.OK).entity(paciente).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }

        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Atualiza os dados do paciente
     *
     * @param dados Conteúdo REST da classe Paciente
     * @return HTTP Status 200 - Retorna um objeto completo da classe Paciente
     * HTTP Status 202 - Retorna um objeto da classe ErrorHandling contendo a
     * lita de rejeições HTTP Status 500 - Retorna erro interno do servidor
     */
    @POST
    @Path("/alterar")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response alterar(String dados) {

        try {
            Paciente paciente = new Paciente().parse(dados);
            PacienteController controller = new PacienteController();
            ErrorHandling listaErros = controller.validarCadastro(paciente);

            if (listaErros.getError().isEmpty()) {
                controller.alterarCadastro(paciente);

                return Response.status(Response.Status.OK).entity(paciente).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).entity(listaErros).build();
            }

        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Exibe a relação de pacientes cadastrados
     *
     * @return HTTP Status 200 - Retorna um objeto completo da classe Paciente
     * HTTP Status 202 - Retorna um objeto da classe ErrorHandling contendo a
     * lita de rejeições HTTP Status 500 - Retorna erro interno do servidor
     */
    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response listarPaciente() {

        try {
            PacienteController controller = new PacienteController();
            List<Paciente> itens = controller.listarPaciente();

            return Response.status(Response.Status.OK).entity(itens).build();
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/listarequipe")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response listarEquipe(String dados) {

        try {
            System.out.println(dados);

            PacienteEquipe pacienteEquipe = new PacienteEquipe().parse(dados);

            PacienteController controller = new PacienteController();
            List<PacienteEquipe> itens = controller.listartPacienteEquipe(pacienteEquipe);

            return Response.status(Response.Status.OK).entity(itens).build();
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/incluirequipe")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response incluirEquipe(String dados) {

        try {
            System.out.println(dados);

            PacienteEquipe pacienteEquipe = new PacienteEquipe().parse(dados);

            PacienteController controller = new PacienteController();
            if (controller.incluirEquipe(pacienteEquipe)) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.ACCEPTED).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(PacienteService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
