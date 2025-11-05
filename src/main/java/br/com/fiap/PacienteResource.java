package br.com.fiap;

import br.com.fiap.beans.Paciente;
import br.com.fiap.bo.PacienteBO;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;
import java.util.ArrayList;

@Provider

@Path("/paciente")
public class PacienteResource {

    private PacienteBO pacienteBO = new PacienteBO();

    // Selecionar
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Paciente> selecionarRs() throws ClassNotFoundException, SQLException, SQLException {
        return  (ArrayList<Paciente>)  pacienteBO.selecionarBo();
    }

    // Inserir
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirRs(Paciente paciente, @Context UriInfo uriInfo ) throws ClassNotFoundException, SQLException {
        pacienteBO.inserirBo(paciente);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path((paciente.getCpf()));
        return Response.created(builder.build()).build();
    }

    // Atualizar
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarRs(Paciente paciente, @PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        pacienteBO.atualizarBo(paciente);
        return Response.ok().build();
    }

    // Deletar
    @DELETE
    @Path("/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletarRs(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        pacienteBO.deletarBo(cpf);
        return Response.ok().build();
    }

}