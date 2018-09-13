package br.com.modelo.rest;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.modelo.rest.Servidor;
import br.com.modelo.rest.modelo.Carrinho;
import br.com.modelo.rest.modelo.Produto;

import com.thoughtworks.xstream.XStream;

public class ClientTest {
	
	private HttpServer server;
	private Client client; 
	private WebTarget target; 

	@Before
	public void startaServidor() {
		server = Servidor.inicializaServidor();
		
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		this.client = ClientBuilder.newClient(config);
		
		//this.client = ClientBuilder.newClient();
		this.target = client.target("http://localhost:8080");
	}

	@After
    public void mataServidor() {
        server.stop();
    }

    //@Test
    //public void testaQueAConexaoComOServidorFunciona() {
    	//Client client = ClientBuilder.newClient();
    	//WebTarget target = client.target("http://www.mocky.io");
    	//String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
    	//Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
    	
    	//Client client = ClientBuilder.newClient();
    	//WebTarget target = client.target("http://localhost:8080");
    	//String conteudo = target.path("/carrinhos").request().get(String.class);
    	//Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
    	//Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());

    //}
    
    @Test
    public void testaQueBuscarUmCarrinhoTrasUmCarrinho() {
        
        String conteudo = target.path("/carrinhos/1").request().get(String.class);
        Carrinho fromXML = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar",fromXML.getRua());

    }

    @Test
    public void testaAdicionaUmCarrinho() {
    	
    	Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Microfone", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();
        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        //Response response = target.path("/carrinhos").request().post(entity);
        Response response = target.path("/carrinhos").request().post(entity);
        //Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
        Assert.assertEquals(201, response.getStatus());
        String location = response.getHeaderString("Location");
        String conteudo_b = client.target(location).request().get(String.class);
        Assert.assertTrue(conteudo_b.contains("Microfone"));
    }

	
	
}
