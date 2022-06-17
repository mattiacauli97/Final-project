package it.epicode.energia.fattura;

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

import it.epicode.energia.dto.InserisciStatoFatturaDTO;
import it.epicode.energia.dto.ModificaStatoFatturaDTO;
import it.epicode.energia.impl.LoginRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatoFatturaControllerTest {

	@Autowired TestRestTemplate restTemplate;
	@LocalServerPort int port;
	
	@Test
	void getAllStatiFattura() {
		//primo test
		String url = "http://localhost:" + port + "/statifatture/trovatutti";
		log.info("----------------getAllStatiFatture " + url + "------GET non autorizzato" );
		ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//secondo test con admin
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------getAllStatiFatture" + url + "------GET autorizzato ad admin");
		r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//terzo test con user
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------getAllStatiFatture" + url + "------GET autorizzato user");
		r = restTemplate.exchange(url, HttpMethod.GET, userEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
	
	@Test
	void getPerId() {
		//primo test
		String url = "http://localhost:" + port + "/statifatture/trovaperid/1";
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
		String url = "http://localhost:" + port + "/statifatture/inserisci"; 
		InserisciStatoFatturaDTO sf = new InserisciStatoFatturaDTO();
		
		//test non autorizzato
		HttpEntity<InserisciStatoFatturaDTO> clienteEntity = new HttpEntity<InserisciStatoFatturaDTO>(sf);
		log.info("----------------inserisciStatoFattura" + url + "------POST");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//test con autorizzazione admin
		HttpEntity<InserisciStatoFatturaDTO> adminEntity = new HttpEntity<InserisciStatoFatturaDTO>(sf, getAdminHeader());
		log.info("----------------inserisciStatoFattura" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//test con user non autorizzato
		HttpEntity<InserisciStatoFatturaDTO> userEntity = new HttpEntity<InserisciStatoFatturaDTO>(sf, getUserHeader());
		log.info("----------------inserisciStatoFattura" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void modificaFattura() {
		String url = "http://localhost:" + port + "/statifatture/modifica"; 
		ModificaStatoFatturaDTO sf = new ModificaStatoFatturaDTO();
		
		HttpEntity<ModificaStatoFatturaDTO> clienteEntity = new HttpEntity<ModificaStatoFatturaDTO>(sf);
		log.info("----------------modificaStatoFattura" + url + "------PUT non autorizzato");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.PUT, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<ModificaStatoFatturaDTO> adminEntity = new HttpEntity<ModificaStatoFatturaDTO>(sf, getAdminHeader());
		log.info("----------------modificaStatoFattura" + url + "------PUT autorizzato con Admin");
		r = restTemplate.exchange(url, HttpMethod.PUT, adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<ModificaStatoFatturaDTO> userEntity = new HttpEntity<ModificaStatoFatturaDTO>(sf, getUserHeader());
		log.info("----------------modificaStatoFattura" + url + "------PUT non autorizzato con User");
		r = restTemplate.exchange(url, HttpMethod.PUT,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void cancellaFattura() {
		String url = "http://localhost:" + port + "/statifatture/cancella/1"; 
		
		log.info("----------------cancellaStatoFattura" + url + "------DELETE");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------cancellaStatoFattura" + url + "------DELETE");
		r = restTemplate.exchange(url, HttpMethod.DELETE, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------cancellaStatoFattura" + url + "------DELETE");
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
