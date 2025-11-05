package br.com.fiap;

import br.com.fiap.beans.Consulta;
import br.com.fiap.bo.ConsultaBO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    private ConsultaBO consultaBO;

    public ConsultaResource() throws SQLException, ClassNotFoundException {
        this.consultaBO = new ConsultaBO();
    }

    // Listar consultas de um paciente
    @GET
    @Path("/{idPaciente}")
    public List<Consulta> listarConsultas(@PathParam("idPaciente") int idPaciente) throws SQLException {
        return consultaBO.selecionarBo(idPaciente);
    }

    // Inserir nova consulta
    @POST
    public Response inserirConsulta(Consulta consulta, @QueryParam("idPaciente") int idPaciente, @Context UriInfo uriInfo) throws SQLException {
        consultaBO.inserirBo(consulta, idPaciente);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(consulta.getIdConsulta()));
        return Response.created(builder.build()).build();
    }

    // Atualizar consulta
    @PUT
    public Response atualizarConsulta(Consulta consulta) throws SQLException {
        consultaBO.atualizarBo(consulta);
        return Response.ok().build();
    }

    // Deletar consulta
    @DELETE
    @Path("/{idConsulta}")
    public Response deletarConsulta(@PathParam("idConsulta") int idConsulta, @QueryParam("idPaciente") int idPaciente) throws SQLException {
        consultaBO.deletarBo(idConsulta, idPaciente);
        return Response.ok().build();
    }
}
