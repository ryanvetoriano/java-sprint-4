package br.com.fiap.resources;

import br.com.fiap.beans.ReceitaMedica;
import br.com.fiap.bo.ReceitaMedicaBO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;


@Path("/receitas")
public class ReceitaMedicaResource {

    private ReceitaMedicaBO receitaBO;

    public ReceitaMedicaResource() {
        try {
            receitaBO = new ReceitaMedicaBO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/{idPaciente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorPaciente(@PathParam("idPaciente") int idPaciente) {
        try {
            List<ReceitaMedica> lista = receitaBO.listarPorPaciente(idPaciente);
            return Response.ok(lista).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao listar receitas médicas").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(@QueryParam("idPaciente") int idPaciente, ReceitaMedica receita) {
        try {
            if (receita.getPaciente() == null) {
                receita.setPaciente(new br.com.fiap.beans.Paciente());
            }
            receita.getPaciente().setIdPaciente(idPaciente);
            String msg = receitaBO.cadastrar(receita);
            return Response.status(Response.Status.CREATED).entity(msg).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao cadastrar receita médica").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(ReceitaMedica receita) {
        try {
            String msg = receitaBO.atualizar(receita);
            return Response.ok(msg).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao atualizar receita médica").build();
        }
    }

    @DELETE
    @Path("/{idReceita}")
    public Response deletar(@PathParam("idReceita") int idReceita) {
        try {
            String msg = receitaBO.deletar(idReceita);
            return Response.ok(msg).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao excluir receita médica").build();
        }
    }
}
