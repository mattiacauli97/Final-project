package it.epicode.energia.cliente;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.epicode.energia.dto.InserisciClienteDTO;
import it.epicode.energia.dto.ModificaClienteDTO;
import it.epicode.energia.impl.LoginRequest;
import it.epicode.energia.model.TipoCliente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {

	@Autowired TestRestTemplate restTemplate;
	@LocalServerPort int port;
	
	@Test
	void getAllClienti() {
		//primo test
		String url = "http://localhost:" + port + "/clienti/trovatutti";
		log.info("----------------getAllClienti " + url + "------GET non autorizzato" );
		ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//secondo test con admin
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------getAllClienti" + url + "------GET autorizzato ad admin");
		r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//terzo test con user
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------getAllClienti" + url + "------GET autorizzato user");
		r = restTemplate.exchange(url, HttpMethod.GET, userEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
	
	@Test
	void getPerId() {
		//primo test
		String url = "http://localhost:" + port + "/clienti/trovaperid/1";
		log.info("----------------getPerId " + url + "------GET non autorizzato" );
		ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//secondo test con admin
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------getPerId" + url + "------GET autorizzato ad admin");
		r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//terzo test con user
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------getPerId" + url + "------GET autorizzato user");
		r = restTemplate.exchange(url, HttpMethod.GET, userEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
	
	@Test
	void inserisciCliente() {
		String url = "http://localhost:" + port + "/clienti/inserisci"; 
		InserisciClienteDTO c = new InserisciClienteDTO();
		c.setPartitaIva("12345");
		c.setRagioneSociale(TipoCliente.SRL);
		c.setEmail("mattiacauli@hotmail.it");
		c.setFatturatoAnnuale(150000);
		c.setPec("m.cauli5@pec.it");
		c.setTelefono("1234567890");
		c.setTelefonoContatto("1234567890");
		c.setEmailContatto("m.cauli@hotmail.it");
		c.setCognomeContatto("Cauli");
		c.setNomeContatto("Mattia");
		
		//test non autorizzato
		HttpEntity<InserisciClienteDTO> clienteEntity = new HttpEntity<InserisciClienteDTO>(c);
		log.info("----------------inserisciCliente" + url + "------POST");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//test con autorizzazione admin
		HttpEntity<InserisciClienteDTO> adminEntity = new HttpEntity<InserisciClienteDTO>(c, getAdminHeader());
		log.info("----------------inserisciCliente" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//test con user non autorizzato
		HttpEntity<InserisciClienteDTO> userEntity = new HttpEntity<InserisciClienteDTO>(c, getUserHeader());
		log.info("----------------inserisciCliente" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void modificaCliente() {
		String url = "http://localhost:" + port + "/clienti/modifica"; 
		
		ModificaClienteDTO c = new ModificaClienteDTO();
		c.setId(1);
		c.setPartitaIva("12345");
		c.setRagioneSociale(TipoCliente.SRL);
		c.setEmail("mattiacauli@hotmail.it");
		c.setFatturatoAnnuale(150000);
		c.setPec("m.cauli5@pec.it");
		c.setTelefono("1234567890");
		c.setTelefonoContatto("1234567890");
		c.setEmailContatto("m.cauli@hotmail.it");
		c.setCognomeContatto("Cauli");
		c.setNomeContatto("Mattia");
		
		HttpEntity<ModificaClienteDTO> clienteEntity = new HttpEntity<ModificaClienteDTO>(c);
		log.info("----------------modificaCliente" + url + "------PUT non autorizzato");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.PUT, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<ModificaClienteDTO> adminEntity = new HttpEntity<ModificaClienteDTO>(c, getAdminHeader());
		log.info("----------------modificaCliente" + url + "------PUT autorizzato con Admin");
		r = restTemplate.exchange(url, HttpMethod.PUT, adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<ModificaClienteDTO> userEntity = new HttpEntity<ModificaClienteDTO>(c, getUserHeader());
		log.info("----------------modificaCliente" + url + "------PUT non autorizzato con User");
		r = restTemplate.exchange(url, HttpMethod.PUT,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void cancellaCliente() {
		String url = "http://localhost:" + port + "/clienti/cancella/1"; 
		
		log.info("----------------cancellaCliente" + url + "------DELETE");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------cancellaCliente" + url + "------DELETE");
		r = restTemplate.exchange(url, HttpMethod.DELETE, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------cancellaCliente" + url + "------DELETE");
		r = restTemplate.exchange(url, HttpMethod.DELETE, userEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
		
	}
	
	protected String getAdminToken() {
		String url = "http://localhost:" + port + "/api/auth/login/jwt";
		LoginRequest login = new LoginRequest();
		login.setUserName("mcauli5");
		login.setPassword("cauli123");
		HttpEntity<LoginRequest> loginRequest = new HttpEntity<LoginRequest>(login); //RequestBody
		String jwt = restTemplate.postForObject(url, loginRequest, String.class);
		log.info("---------" + jwt);
		return jwt;
	}
	
	protected String getUserToken() {
		String url = "http://localhost:" + port + "/api/auth/login/jwt";
		LoginRequest login = new LoginRequest();
		login.setUserName("mcauli");
		login.setPassword("cauli123");
		HttpEntity<LoginRequest> loginRequest = new HttpEntity<LoginRequest>(login); //RequestBody
		String jwt = restTemplate.postForObject(url, loginRequest, String.class);
		return jwt;
	}
	
	protected HttpHeaders getAdminHeader() {
		HttpHeaders header = new HttpHeaders();
		String jwt = getAdminToken();
		header.set("Authorization", "Bearer " + jwt);
		log.info(jwt);
		return header;
	}
	
	protected HttpHeaders getUserHeader() {
		HttpHeaders header = new HttpHeaders();
		String jwt = getUserToken();
		header.set("Authorization", "Bearer " + jwt);
		return header;
	}
	
}
