package br.com.fiap.resources;

import br.com.fiap.beans.Exame;
import br.com.fiap.bo.ExameBO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/exames")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExameResource {

    private ExameBO exameBO;

    public ExameResource() throws SQLException, ClassNotFoundException {
        this.exameBO = new ExameBO();
    }

    // Listar exames de um paciente
    @GET
    @Path("/{idPaciente}")
    public List<Exame> listarExames(@PathParam("idPaciente") int idPaciente) throws SQLException {
        return exameBO.selecionarBo(idPaciente);
    }

    // Buscar exame por ID (para edição ou visualização)
    @GET
    @Path("/detalhe/{idExame}")
    public Exame buscarExame(@PathParam("idExame") int idExame) throws SQLException {
        return exameBO.buscarPorId(idExame);
    }

    // Inserir novo exame
    @POST
    public Response inserirExame(Exame exame, @QueryParam("idPaciente") int idPaciente, @Context UriInfo uriInfo) throws SQLException {
        exameBO.inserirBo(exame, idPaciente);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(exame.getIdExame()));
        return Response.created(builder.build()).build();
    }

    // Atualizar exame
    @PUT
    public Response atualizarExame(Exame exame) throws SQLException {
        exameBO.atualizarBo(exame);
        return Response.ok(exameBO.buscarPorId(exame.getIdExame())).build();
    }

    // Deletar exame
    @DELETE
    @Path("/{idExame}")
    public Response deletarExame(@PathParam("idExame") int idExame, @QueryParam("idPaciente") int idPaciente) throws SQLException {
        exameBO.deletarBo(idExame, idPaciente);
        return Response.ok().build();
    }
}
