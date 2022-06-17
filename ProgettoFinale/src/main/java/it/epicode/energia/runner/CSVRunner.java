package it.epicode.energia.runner;

/**
 * classe runner per inserire nel db i dati dei csv
 */
import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import it.epicode.energia.dto.ComuneCSV;
import it.epicode.energia.dto.ProvinciaCSV;
import it.epicode.energia.model.Comune;
import it.epicode.energia.model.Provincia;
import it.epicode.energia.repository.ComuneRepository;
import it.epicode.energia.repository.ProvinciaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class CSVRunner implements ApplicationRunner{
	
	ComuneRepository cr;
	ProvinciaRepository pr;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {		
		String fileProvinceCSV = "csv/province-italiane.csv";
		CsvSchema provinceCSVSchema = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
		CsvMapper mapper2 = new CsvMapper();
		File fileProvincia = new ClassPathResource(fileProvinceCSV).getFile();
		MappingIterator<List<String>> valueReader2 = mapper2
				.reader(ProvinciaCSV.class)
				.with(provinceCSVSchema)
				.readValues(fileProvincia);
		for (Object o : valueReader2.readAll()) {
			ProvinciaCSV dto = new ProvinciaCSV();
			Provincia p = new Provincia();
			BeanUtils.copyProperties(o, dto);
			BeanUtils.copyProperties(dto, p);
			pr.save(p);
		}
		
		String fileComuniCSV = "csv/comuni.csv";
		CsvSchema comuniCSVSchema = CsvSchema.emptySchema().withColumnSeparator(';').withHeader();
		CsvMapper mapper = new CsvMapper();
		File fileComune = new ClassPathResource(fileComuniCSV).getFile();
		MappingIterator<List<String>> valueReader = mapper
				.reader(ComuneCSV.class)
				.with(comuniCSVSchema)
				.readValues(fileComune);
		for (Object o : valueReader.readAll()) {
			ComuneCSV dto = new ComuneCSV();
			Comune c = new Comune();
			BeanUtils.copyProperties(o, dto);
			c.setCap(dto.getCap());
			c.setComune(dto.getComune());
			c.setProvincia(pr.findById(dto.getProvincia()).get());
			cr.save(c);
		}
	}
}
