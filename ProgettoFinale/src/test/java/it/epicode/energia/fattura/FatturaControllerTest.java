package it.epicode.energia.fattura;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

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

import it.epicode.energia.dto.InserisciFatturaDTO;
import it.epicode.energia.dto.ModificaFatturaDTO;
import it.epicode.energia.impl.LoginRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FatturaControllerTest {

	@Autowired TestRestTemplate restTemplate;
	@LocalServerPort int port;
	
	@Test
	void getAllFatture() {
		//primo test
		String url = "http://localhost:" + port + "/fatture/trovatutte";
		log.info("----------------getAllFatture " + url + "------GET non autorizzato" );
		ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//secondo test con admin
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------getAllFatture" + url + "------GET autorizzato ad admin");
		r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//terzo test con user
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------getAllFatture" + url + "------GET autorizzato user");
		r = restTemplate.exchange(url, HttpMethod.GET, userEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
	
	@Test
	void getPerId() {
		//primo test
		String url = "http://localhost:" + port + "/fatture/trovaperid/1";
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
	void inserisciFattura() {
		String url = "http://localhost:" + port + "/fatture/inserisci"; 
		InserisciFatturaDTO f = new InserisciFatturaDTO();
		f.setAnno(2010);
		f.setData(LocalDate.now());
		f.setId_cliente(1);
		f.setIdStato(1);
		f.setImporto(2000);
		
		//test non autorizzato
		HttpEntity<InserisciFatturaDTO> clienteEntity = new HttpEntity<InserisciFatturaDTO>(f);
		log.info("----------------inserisciFattura" + url + "------POST");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//test con autorizzazione admin
		HttpEntity<InserisciFatturaDTO> adminEntity = new HttpEntity<InserisciFatturaDTO>(f, getAdminHeader());
		log.info("----------------inserisciFattura" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//test con user non autorizzato
		HttpEntity<InserisciFatturaDTO> userEntity = new HttpEntity<InserisciFatturaDTO>(f, getUserHeader());
		log.info("----------------inserisciFattura" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void modificaFattura() {
		String url = "http://localhost:" + port + "/fatture/modifica"; 
		ModificaFatturaDTO f = new ModificaFatturaDTO();
		f.setAnno(2010);
		f.setData(LocalDate.now());
		f.setNumero(1);
		f.setId_cliente(1);
		f.setIdStato(1);
		f.setImporto(3000);
		
		HttpEntity<ModificaFatturaDTO> clienteEntity = new HttpEntity<ModificaFatturaDTO>(f);
		log.info("----------------modificaFattura" + url + "------PUT non autorizzato");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.PUT, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<ModificaFatturaDTO> adminEntity = new HttpEntity<ModificaFatturaDTO>(f, getAdminHeader());
		log.info("----------------modificaFattura" + url + "------PUT autorizzato con Admin");
		r = restTemplate.exchange(url, HttpMethod.PUT, adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<ModificaFatturaDTO> userEntity = new HttpEntity<ModificaFatturaDTO>(f, getUserHeader());
		log.info("----------------modificaFattura" + url + "------PUT non autorizzato con User");
		r = restTemplate.exchange(url, HttpMethod.PUT,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void cancellaFattura() {
		String url = "http://localhost:" + port + "/fatture/cancella/1"; 
		
		log.info("----------------cancellaFattura" + url + "------DELETE");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------cancellaFattura" + url + "------DELETE");
		r = restTemplate.exchange(url, HttpMethod.DELETE, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------cancellaFattura" + url + "------DELETE");
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
