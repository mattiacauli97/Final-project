package it.epicode.energia.indirizzo;

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

import it.epicode.energia.dto.InserisciSoloIndirizzoDTO;
import it.epicode.energia.dto.ModificaIndirizzoDTO;
import it.epicode.energia.impl.LoginRequest;
import it.epicode.energia.model.TipoIndirizzo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IndirizzoControllerTest {
	
	@Autowired TestRestTemplate restTemplate;
	@LocalServerPort int port;
	
	@Test
	void getAllIndirizzi() {
		//primo test
		String url = "http://localhost:" + port + "/indirizzi/trovatutti";
		log.info("----------------getAllIndirizzi " + url + "------GET non autorizzato" );
		ResponseEntity<String> r = restTemplate.getForEntity(url, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//secondo test con admin
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------getAllIndirizzi" + url + "------GET autorizzato ad admin");
		r = restTemplate.exchange(url, HttpMethod.GET, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//terzo test con user
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------getAllIndirizzi" + url + "------GET autorizzato user");
		r = restTemplate.exchange(url, HttpMethod.GET, userEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
	
	@Test
	void getPerId() {
		//primo test
		String url = "http://localhost:" + port + "/indirizzi/trovaperid/1";
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
	void inserisciIndirizzo() {
		String url = "http://localhost:" + port + "/indirizzi/inserisci"; 
		InserisciSoloIndirizzoDTO i = new InserisciSoloIndirizzoDTO();
		i.setCivico(10);
		i.setIdComune(1);
		i.setLocalita("boh");
		i.setVia("Dante");
		i.setIdCliente(1);
		i.setTipo(TipoIndirizzo.SEDE_OPERATIVA);
		
		
		//test non autorizzato
		HttpEntity<InserisciSoloIndirizzoDTO> clienteEntity = new HttpEntity<InserisciSoloIndirizzoDTO>(i);
		log.info("----------------inserisciIndirizzo" + url + "------POST");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.POST, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		//test con autorizzazione admin
		HttpEntity<InserisciSoloIndirizzoDTO> adminEntity = new HttpEntity<InserisciSoloIndirizzoDTO>(i, getAdminHeader());
		log.info("----------------inserisciIndirizzo" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		//test con user non autorizzato
		HttpEntity<InserisciSoloIndirizzoDTO> userEntity = new HttpEntity<InserisciSoloIndirizzoDTO>(i, getUserHeader());
		log.info("----------------inserisciIndirizzo" + url + "------POST");
		r = restTemplate.exchange(url, HttpMethod.POST,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void modificaIndirizzo() {
		String url = "http://localhost:" + port + "/indirizzi/modifica"; 
		ModificaIndirizzoDTO i = new ModificaIndirizzoDTO();
		i.setId(1);
		i.setCivico(10);
		i.setIdCliente(1);
		i.setIdComune(1);
		i.setLocalita("boh");
		i.setVia("Dante Alighieri");
		
		HttpEntity<ModificaIndirizzoDTO> clienteEntity = new HttpEntity<ModificaIndirizzoDTO>(i);
		log.info("----------------modificaIndirizzo" + url + "------PUT non autorizzato");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.PUT, clienteEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<ModificaIndirizzoDTO> adminEntity = new HttpEntity<ModificaIndirizzoDTO>(i, getAdminHeader());
		log.info("----------------modificaIndirizzo" + url + "------PUT autorizzato con Admin");
		r = restTemplate.exchange(url, HttpMethod.PUT, adminEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<ModificaIndirizzoDTO> userEntity = new HttpEntity<ModificaIndirizzoDTO>(i, getUserHeader());
		log.info("----------------modificaIndirizzo" + url + "------PUT non autorizzato con User");
		r = restTemplate.exchange(url, HttpMethod.PUT,userEntity , String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	void cancellaIndirizzo() {
		String url = "http://localhost:" + port + "/indirizzi/cancella/1"; 
		
		log.info("----------------cancellaIndirizzo" + url + "------DELETE");
		ResponseEntity<String> r = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
		
		HttpEntity<String> adminEntity = new HttpEntity<String>(getAdminHeader());
		log.info("----------------cancellaIndirizzo" + url + "------DELETE");
		r = restTemplate.exchange(url, HttpMethod.DELETE, adminEntity, String.class);
		assertThat(r.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		
		HttpEntity<String> userEntity = new HttpEntity<String>(getUserHeader());
		log.info("----------------cancellaIndirizzo" + url + "------DELETE");
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
