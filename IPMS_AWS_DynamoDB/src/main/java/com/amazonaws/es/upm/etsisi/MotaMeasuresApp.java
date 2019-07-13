package com.amazonaws.es.upm.etsisi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.es.upm.etsisi.entities.mota.MotaMeasure;
import com.amazonaws.es.upm.etsisi.management.GestorMotaMeasures;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class MotaMeasuresApp {
	private static GestorMotaMeasures gestorMotas;
	private static Scanner scanner;
	
	static {
		gestorMotas = new GestorMotaMeasures();
		scanner = new Scanner(System.in);
	}
	
	/*
     * Before running the code:
     *      Fill in your AWS access credentials in the provided credentials
     *      file template, and be sure to move the file to the default location
     *      (S:\\sources\\credentials) where the sample code will load the
     *      credentials from.
     *      https://console.aws.amazon.com/iam/home?#security_credential
     *
     * WARNING:
     *      To avoid accidental leakage of your credentials, DO NOT keep
     *      the credentials file in your source directory.
     */
    private static AmazonDynamoDB dynamoDBClient;

    /**
     * The only information needed to create a client are security credentials
     * consisting of the AWS Access Key ID and Secret Access Key. All other
     * configuration, such as the service endpoints, are performed
     * automatically. Client parameters, such as proxies, can be specified in an
     * optional ClientConfiguration object when constructing a client.
     *
     * @see com.amazonaws.auth.BasicAWSCredentials
     * @see com.amazonaws.auth.ProfilesConfigFile
     * @see com.amazonaws.ClientConfiguration
     */
    private static void init() throws Exception {
        /*
         * The ProfileCredentialsProvider will return your [Admin]
         * credential profile by reading from the credentials file located at
         * (S:\\sources\\credentials).
         */
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
        	System.out.println("Iniciando sesion en AWS...");
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (S:\\sources\\credentials), and is in valid format.",
                    e);
        }
        System.out.println("Sesion iniciada correctamente\n");
    	System.out.println("Conectando con el cliente de DynamoDB...");
        dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion(Regions.EU_WEST_3)
            .build();
        System.out.println("Cliente conectado\n");
    }
	
	/**
	 * Muestra el menú de operaciones por consola.
	 */
	private static void mostrarMenu() {
		System.out.println("\nSeleccione una opcion...");
		System.out.println("1. Generar trazas con valores aleatorios de motas a fichero (motaMeasures.json).");
		System.out.println("2. Alta en AWS DynamoDB de una nueva traza de mota.");
		System.out.println("3. Baja de AWS DynamoDB de una traza de mota.");
		System.out.println("4. Buscar una traza de mota en AWS DynamoDB.");
		System.out.println("5. Listar todas las trazas de motas de AWS DynamoDB.");
		System.out.println("0. Salir.");
	}
	
	/**
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 * @throws ParseException
	 */
	private static void generarTrazas() throws UnsupportedEncodingException, JsonProcessingException, ParseException {
		gestorMotas.generateMotaMeasuresToFile();
	}
	
	/**
	 * Operación para dar de alta una traza de mota.
	 */
	private static void altaTraza() {
		MotaMeasure motaTraza = new MotaMeasure();
		if(gestorMotas.altaMota(motaTraza, dynamoDBClient)) {
			System.out.println("Traza dada de alta en la base de datos.");
			System.out.println(motaTraza);
		} else {
			System.out.println("No se ha podido dar de alta la traza en la base de datos.");
		}
	}
	
	/**
	 * Operación para dar de baja una traza de mota.
	 */
	private static void bajaTraza() {
		System.out.println("Introduzca el id a buscar para dar de baja: ");
		String id = scanner.nextLine();
		if(gestorMotas.bajaMota(id, dynamoDBClient)) {
			System.out.println("Traza dada de baja de la base de datos.");
		} else {
			System.out.println("No se ha podido dar de baja a la traza en la base de datos.");
		}
	}
	
	/**
	 * Operación para buscar una traza de mota.
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private static void buscarTraza() throws JsonParseException, JsonMappingException, IOException {
		System.out.println("Introduzca el id a buscar: ");
		String id = scanner.nextLine();
		MotaMeasure motaTraza = gestorMotas.getMotaTraza(id, dynamoDBClient);
		if(motaTraza != null) {
			System.out.println("Traza encontrada:");
			System.out.println(motaTraza);
		} else {
			System.out.println("La traza con el id " + id + " no se encuentra en la base de datos.");
		}
	}
	
	/**
	 * Operación para listar todas las trazas de motas.
	 */
	private static void listarTrazas() {
		List<MotaMeasure> listaMotas = gestorMotas.getListaMotaTrazas(dynamoDBClient);
		if(!listaMotas.isEmpty()) {
			listaMotas.forEach(mota->System.out.println(mota));
		} else {
			System.out.println("Lista de trazas vacia.");
		}
	}
	
	public static void main(String... args) {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String opcionStr;
		do {
			mostrarMenu();
			opcionStr = scanner.nextLine();
			switch (opcionStr) {
			case "1":
				try {
					generarTrazas();
				} catch (UnsupportedEncodingException | JsonProcessingException | ParseException e) {
					e.printStackTrace();
				}
				break;
			case "2":
				altaTraza();
				break;
			case "3":
				bajaTraza();
				break;
			case "4":
				try {
					buscarTraza();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "5":
				listarTrazas();
				break;
			case "0":
				System.out.println("Fin del programa.");
				System.exit(0);
				break;
			default:
				System.out.println("Entrada no valida.");
			}	
		} while(!opcionStr.equals("0"));

	}
	
}
