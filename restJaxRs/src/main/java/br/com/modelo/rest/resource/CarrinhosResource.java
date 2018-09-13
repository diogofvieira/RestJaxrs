package br.com.modelo.rest.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.modelo.rest.dao.CarrinhoDAO;
import br.com.modelo.rest.modelo.Carrinho;
import br.com.modelo.rest.modelo.Produto;

import com.thoughtworks.xstream.XStream;

@Path("carrinhos")
public class CarrinhosResource {
	
	//@GET
	//@Produces(MediaType.APPLICATION_XML)
	//public String busca() {
	//   Carrinho carrinho = new CarrinhoDAO().busca(1l);
	//    return carrinho.toXML();
	//}

	//@GET
    //@Produces(MediaType.APPLICATION_XML)
    //public String busca(@QueryParam("id") long id) {
    //    Carrinho carrinho = new CarrinhoDAO().busca(id);
    //    return carrinho.toXML();
    //}

	//@Path("{id}")
	//@GET
	//@Produces(MediaType.APPLICATION_JSON)
	//public String busca(@PathParam("id") long id) {
	//    Carrinho carrinho = new CarrinhoDAO().busca(id);
	//    return carrinho.toJson();
	//}

	
	@Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String busca(@PathParam("id") long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho.toXML();
    }
	
	//@POST
	//@Produces(MediaType.APPLICATION_XML)
	//public String adiciona(String conteudo) {
	//    Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
	//    new CarrinhoDAO().adiciona(carrinho);
	//    return "<status>sucesso</status>";
	// }
	
	@POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(String conteudo) {
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        new CarrinhoDAO().adiciona(carrinho);
        URI uri = URI.create("/carrinhos/" + carrinho.getId());
        return Response.created(uri).build();
    }
	
	@Path("{id}/produtos/{produtoId}")
    @DELETE
    public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.remove(produtoId);
		return Response.ok().build();
    }

	//@Path("{id}/produtos/{produtoId}")
	//@PUT
	//@Consumes(MediaType.APPLICATION_XML)
	//public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {
	//    Carrinho carrinho = new CarrinhoDAO().busca(id);
	//    Produto produto = (Produto) new XStream().fromXML(conteudo);
	//    carrinho.troca(produto);
	//    return Response.ok().build();
	//}
	
	@Path("{id}/produtos/{produtoId}/quantidade")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {
	    Carrinho carrinho = new CarrinhoDAO().busca(id);
	    Produto produto = (Produto) new XStream().fromXML(conteudo);
	    carrinho.trocaQuantidade(produto);
	    return Response.ok().build();
	}



}
