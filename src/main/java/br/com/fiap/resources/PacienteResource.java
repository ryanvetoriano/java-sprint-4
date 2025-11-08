package br.com.fiap.resources;

import br.com.fiap.beans.Paciente;
import br.com.fiap.bo.PacienteBO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/paciente")
public class PacienteResource {

    private PacienteBO pacienteBO;

    public PacienteResource() throws ClassNotFoundException, SQLException {
        this.pacienteBO = new PacienteBO(); // inicializa BO
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarRs(@QueryParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        ArrayList<Paciente> resultado = new ArrayList<>();
        if (cpf != null && !cpf.isEmpty()) {
            Paciente paciente = pacienteBO.buscarLogin(cpf);
            if (paciente != null) {
                resultado.add(paciente);
            }
        } else {
            resultado = pacienteBO.selecionarBo();
        }
        return Response.ok(resultado).build(); // sempre retorna JSON array
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirRs(Paciente paciente, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        pacienteBO.inserirBo(paciente);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(paciente.getCpf());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarRs(@PathParam("cpf") String cpf, Paciente paciente) throws ClassNotFoundException, SQLException {
        paciente.setCpf(cpf);
        pacienteBO.atualizarBo(paciente);
        return Response.ok(paciente).build();
    }

    @DELETE
    @Path("/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarRs(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        pacienteBO.deletarBo(cpf);
        return Response.ok().build();
    }
}
