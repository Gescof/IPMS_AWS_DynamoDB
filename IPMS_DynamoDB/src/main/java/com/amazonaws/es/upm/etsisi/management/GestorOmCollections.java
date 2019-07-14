package com.amazonaws.es.upm.etsisi.management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import com.amazonaws.es.upm.etsisi.entities.mota.MotaMeasure;
import com.amazonaws.es.upm.etsisi.entities.omtraza.Geometry;
import com.amazonaws.es.upm.etsisi.entities.omtraza.Member;
import com.amazonaws.es.upm.etsisi.entities.omtraza.ObservationCollection;
import com.amazonaws.es.upm.etsisi.entities.omtraza.OmMember;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GestorOmCollections {
	private final String TABLENAME = "ObservationCollections";
	
	/**
	 * Lee el fichero motaMeasures.json
	 * y genera una traza OM-JSON Collection por cada línea
	 * @param dynamoDBClient
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public List<JSONObject> loadOmJsonFromFile(AmazonDynamoDB dynamoDBClient) throws JsonParseException, IOException {
		System.out.println("Buscando fichero de prueba en S:\\sources\\motaMeasures.json...");
		File readFile = new File("S:\\sources\\motaMeasures.json");
		Scanner scanner = new Scanner(readFile);
    	System.out.println("Fichero cargado correctamente\n");
		List<JSONObject> jsonArrayList = new ArrayList<>();
		String motaTrazaStr;
		ObjectMapper objectMapper = new ObjectMapper();
		
		ObservationCollection omTraza = new ObservationCollection();
		List<OmMember> members = new ArrayList<>();
		OmMember omMember;
		Geometry geometry;
		Member member;
		
		while (scanner.hasNext()) {
			motaTrazaStr = scanner.next();
			MotaMeasure motaTraza = objectMapper.readValue(motaTrazaStr, MotaMeasure.class);
			omTraza.setId(motaTraza.getMotaId());
			omTraza.getPhenomenomTime().setDate(motaTraza.getTimestamp().getDate());
			
			geometry = new Geometry();
			geometry.setType("Point");
			geometry.setCoordinates(motaTraza.getGeometry().getCoordinates());
			omMember = new OmMember();
			omMember.setId("geometry" + motaTraza.getMotaId());
			omMember.setType("Geometry-Observation");
			omMember.getResultTime().setDate(motaTraza.getTimestamp().getDate());
			omMember.setResultType(geometry);
			members.add(omMember);
			
			member = new Member();
			member.setValue(motaTraza.getMeasures().getTemperature().getValue());
			member.setUom("https://en.wikipedia.org/wiki/Celsius");
			omMember = new OmMember();
			omMember.setId("temperature" + motaTraza.getMotaId());
			omMember.setType("Category-Observation");
			omMember.getResultTime().setDate(motaTraza.getTimestamp().getDate());
			omMember.setResultType(member);
			members.add(omMember);
			
			member = new Member();
			member.setValue(motaTraza.getMeasures().getHumidity().getValue());
			member.setUom("https://en.wikipedia.org/wiki/Relative_humidity");
			omMember = new OmMember();
			omMember.setId("humidity" + motaTraza.getMotaId());
			omMember.setType("Category-Observation");
			omMember.getResultTime().setDate(motaTraza.getTimestamp().getDate());
			omMember.setResultType(member);
			members.add(omMember);
			
			member = new Member();
			member.setValue(motaTraza.getMeasures().getLuminosity().getValue());
			member.setUom("https://en.wikipedia.org/wiki/Lux");
			omMember = new OmMember();
			omMember.setId("luminosity" + motaTraza.getMotaId());
			omMember.setType("Category-Observation");
			omMember.getResultTime().setDate(motaTraza.getTimestamp().getDate());
			omMember.setResultType(member);
			members.add(omMember);

			omTraza.setMembers(members);
			jsonArrayList.add(new JSONObject(objectMapper.writeValueAsString(omTraza)));
			members.clear();
		}			
		scanner.close();
		return jsonArrayList;
	}

	/**
	 * Obtiene un documento sin estandarizar proveniente 
	 * de la tabla no estandarizada en AWS DynamoDB,
	 * la traduce y la da de alta como traza OM-JSON Collection.
	 * @param motaMeasure
	 * @return
	 */
	public boolean parseMotaTrazaToOmJson(MotaMeasure motaMeasure, AmazonDynamoDB dynamoDBClient) {
		boolean result = false;
		ObservationCollection omTraza = new ObservationCollection();
		List<OmMember> members = new ArrayList<OmMember>();
		OmMember omMember;
		Geometry geometry;
		Member member;

		omTraza.setId(motaMeasure.getMotaId());
		omTraza.getPhenomenomTime().setDate(motaMeasure.getTimestamp().getDate());
		
		geometry = new Geometry();
		geometry.setType("Point");
		geometry.setCoordinates(motaMeasure.getGeometry().getCoordinates());
		omMember = new OmMember();
		omMember.setId("geometry" + motaMeasure.getMotaId());
		omMember.setType("Geometry-Observation");
		omMember.getResultTime().setDate(motaMeasure.getTimestamp().getDate());
		omMember.setResultType(geometry);
		members.add(omMember);
		
		member = new Member();
		member.setValue(motaMeasure.getMeasures().getTemperature().getValue());
		member.setUom("https://en.wikipedia.org/wiki/Celsius");
		omMember = new OmMember();
		omMember.setId("temperature" + motaMeasure.getMotaId());
		omMember.setType("Category-Observation");
		omMember.getResultTime().setDate(motaMeasure.getTimestamp().getDate());
		omMember.setResultType(member);
		members.add(omMember);
		
		member = new Member();
		member.setValue(motaMeasure.getMeasures().getHumidity().getValue());
		member.setUom("https://en.wikipedia.org/wiki/Relative_humidity");
		omMember = new OmMember();
		omMember.setId("humidity" + motaMeasure.getMotaId());
		omMember.setType("Category-Observation");
		omMember.getResultTime().setDate(motaMeasure.getTimestamp().getDate());
		omMember.setResultType(member);
		members.add(omMember);

		member = new Member();
		member.setValue(motaMeasure.getMeasures().getLuminosity().getValue());
		member.setUom("https://en.wikipedia.org/wiki/Lux");
		omMember = new OmMember();
		omMember.setId("luminosity" + motaMeasure.getMotaId());
		omMember.setType("Category-Observation");
		omMember.getResultTime().setDate(motaMeasure.getTimestamp().getDate());
		omMember.setResultType(member);
		members.add(omMember);

		omTraza.setMembers(members);
		altaOmCollection(omTraza, dynamoDBClient);
		result = true;
		
		return result;
	}
	
	/**
	 * @param omCollection
	 * @return
	 */
	public boolean altaOmCollection(ObservationCollection omCollection, AmazonDynamoDB dynamoDBClient) {
		boolean result = false;
        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
        table.putItem(new Item().withPrimaryKey("id", "observation-collection " + omCollection.getId())
        		.withJSON("phenomenomTime", omCollection.getPhenomenomTime().toString())
        		.withJSON("members", omCollection.getMembers().toString()));
		result = true;
		return result;
	}

	/**
	 * @param id
	 * @return
	 */
	public boolean bajaOmCollection(String id, AmazonDynamoDB dynamoDBClient) {
		boolean result = false;
		DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
		DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("id", id);
		table.deleteItem(deleteItemSpec);
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
	public ObservationCollection getOmCollection(String id, AmazonDynamoDB dynamoDBClient) throws JsonParseException, JsonMappingException, IOException {
		DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("id", id);
		Item outcome = table.getItem(spec);
		ObjectMapper objectMapper = new ObjectMapper();
		String omCollectionStr = outcome.toJSON();
		ObservationCollection omCollection = objectMapper.readValue(omCollectionStr, ObservationCollection.class);
		return omCollection;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public String getOmCollectionStr(String id, AmazonDynamoDB dynamoDBClient) throws JsonParseException, JsonMappingException, IOException {
		DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("id", id);
		Item outcome = table.getItem(spec);
		return outcome.toJSON();
	}

	/**
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public List<ObservationCollection> getListaOmCollections(AmazonDynamoDB dynamoDBClient) throws JsonParseException, JsonMappingException, IOException {
		List<ObservationCollection> omCollectionList = new ArrayList<ObservationCollection>();
		ObjectMapper objectMapper = new ObjectMapper();
		DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#id, members, phenomenomTime")
        		.withNameMap(new NameMap().with("#id", "id"));
        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            String omCollectionStr = item.toJSON();
            omCollectionList.add(objectMapper.readValue(omCollectionStr, ObservationCollection.class));
        }
		return omCollectionList;
	}
	
	/**
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public List<String> getListaOmCollectionsStr(AmazonDynamoDB dynamoDBClient) throws JsonParseException, JsonMappingException, IOException {
		List<String> omCollectionList = new ArrayList<String>();
		DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(TABLENAME);
        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#id, members, phenomenomTime")
        		.withNameMap(new NameMap().with("#id", "id"));
        ItemCollection<ScanOutcome> items = table.scan(scanSpec);
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            omCollectionList.add(item.toJSON());
        }
		return omCollectionList;
	}
	
}
