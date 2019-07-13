package com.amazonaws.es.upm.etsisi.management;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.amazonaws.es.upm.etsisi.entities.mota.MotaMeasure;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <li>int NUMIDS -> Número de trazas JSON que se generan.</li>
 * <li>int MINDAY, MAXDAY -> Día de inicio (MINDAY) y final (MAXDAY) para generar las fechas (Epoch).</li>
 * <li>float MINCOORZERO, MAXCOORZERO -> Coordenada mínima y máxima a generar.</li>
 * <li>float MINTEMP, MAXTEMP -> Temperatura mínima y máxima a generar.</li>
 * <li>float MINHUM, MAXHUM -> Humedad mínima y máxima a generar.</li>
 * <li>float MINLUM,  MAXLUM -> Luminosidad mínima y máxima a generar.</li>
 * @author Guillermo, Yan Liu
 * @version 1.0
 */
public class GestorMotaMeasures {
	private final String TABLENAME = "MotaMeasures";
	private static final int NUMIDS = 100;
	private static final int MINDAY = 1483228800;
	private static final int MAXDAY = 1546300800;
	private static final float MAXCOORZERO = 40.9f, MINCOORZERO = 40.0f;
	private static final float MAXCOORONE = -3.0f, MINCOORONE = -3.9f;
	private static final float MAXTEMP = 50.0f, MINTEMP = 0.0f;
	private static final float MAXHUM = 100.0f, MINHUM = 0.0f;
	private static final float MAXLUM = 100.0f, MINLUM = 0.0f;

//    private static Map<String, AttributeValue> newMotaMeasure(MotaMeasure motaTraza) {
//        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
//        item.put("motaMeasure", new AttributeValue(motaTraza.toString()));
//        return item;
//    }
	
	/**
	 * @param motaMeasure
	 * @return
	 */
	public boolean altaMota(MotaMeasure motaMeasure, AmazonDynamoDB dynamoDBClient) {
		boolean result = false;
		Random random = new Random();
		long randomDay;
		ZonedDateTime randomDate;		
		float[] coordinates = new float[2];
		
		motaMeasure.getGeometry().setType("Point");
		motaMeasure.setMotaId("mota" + random.nextInt(100));
		
		randomDay = MINDAY + random.nextInt(MAXDAY - MINDAY);
		Instant instant = Instant.ofEpochSecond(randomDay);
		randomDate = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
		motaMeasure.getTimestamp().setDate(Date.from(randomDate.toInstant()));

		coordinates[0] = random.nextFloat() * (MAXCOORZERO - MINCOORZERO) + MINCOORZERO;
		coordinates[1] = random.nextFloat() * (MAXCOORONE - MINCOORONE) + MINCOORONE;
		motaMeasure.getGeometry().setCoordinates(coordinates);
		
		motaMeasure.getMeasures().getTemperature().setValue(random.nextFloat() * (MAXTEMP - MINTEMP) + MINTEMP);
		motaMeasure.getMeasures().getHumidity().setValue(random.nextFloat() * (MAXHUM - MINHUM) + MINHUM);
		motaMeasure.getMeasures().getLuminosity().setValue(random.nextFloat() * (MAXLUM - MINLUM) + MINLUM);
		
        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
        table.putItem(new Item().withPrimaryKey("mota_id", motaMeasure.getMotaId()).withJSON("MotaMeasure", motaMeasure.toString()));
		result = true;
		
		return result;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public boolean bajaMota(String id, AmazonDynamoDB dynamoDBClient) {
		boolean result = false;
		//daoMotaMeasures.deleteByMotaMeasure_motaId(id);
		result = true;
		return result;
	}

	/**
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public MotaMeasure getMotaTraza(String id, AmazonDynamoDB dynamoDBClient) throws JsonParseException, JsonMappingException, IOException {
		DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("mota_id", id);
		Item outcome = table.getItem(spec);
		ObjectMapper objectMapper = new ObjectMapper();
		String motaTrazaStr = outcome.toJSON();
		MotaMeasure motaTraza = objectMapper.readValue(motaTrazaStr, MotaMeasure.class);
		return motaTraza;
	}
	
	/**
	 * @return
	 */
	public List<MotaMeasure> getListaMotaTrazas(AmazonDynamoDB dynamoDBClient) {
		return null;
	}
	
	/**
	 * Genera trazas JSON no estandarizadas en un fichero (motaMeasures.json) 
	 * alojado en la carpeta S:\\sources\\motaMeasures.json
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws ParseException
	 */
	public void generateMotaMeasuresToFile() throws UnsupportedEncodingException, JsonProcessingException, ParseException {	
		try {
			PrintWriter writer = new PrintWriter("S:\\sources\\motaMeasures.json");
		
			Random random = new Random();			
			long randomDay;
			ZonedDateTime randomDate;		
			
			MotaMeasure motaTraza = new MotaMeasure();
			float[] coordinates = new float[2];
			motaTraza.getGeometry().setType("Point");
	
			for(int i = 0; i < NUMIDS; i++) {
				motaTraza.setMotaId("mota" + (i + 1));
				
				randomDay = MINDAY + random.nextInt(MAXDAY - MINDAY);
				Instant instant = Instant.ofEpochSecond(randomDay);
				randomDate = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
				motaTraza.getTimestamp().setDate(Date.from(randomDate.toInstant()));
	
				coordinates[0] = random.nextFloat() * (MAXCOORZERO - MINCOORZERO) + MINCOORZERO;
				coordinates[1] = random.nextFloat() * (MAXCOORONE - MINCOORONE) + MINCOORONE;
				motaTraza.getGeometry().setCoordinates(coordinates);
				
				motaTraza.getMeasures().getTemperature().setValue(random.nextFloat() * (MAXTEMP - MINTEMP) + MINTEMP);
				motaTraza.getMeasures().getHumidity().setValue(random.nextFloat() * (MAXHUM - MINHUM) + MINHUM);
				motaTraza.getMeasures().getLuminosity().setValue(random.nextFloat() * (MAXLUM - MINLUM) + MINLUM);

				ObjectMapper objectMapper = new ObjectMapper();
				writer.println(objectMapper.writeValueAsString(motaTraza));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Fichero generado.");
	}
	
}
