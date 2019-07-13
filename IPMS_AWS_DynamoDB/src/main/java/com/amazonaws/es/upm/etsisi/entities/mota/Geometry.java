package com.amazonaws.es.upm.etsisi.entities.mota;

import java.util.Arrays;

/**
 * Representa la geometr�a que contiene una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Geometry {
	private String type;
	private float[] coordinates;
	
	public Geometry()
	{}
	
	public Geometry(String type, float[] coordinates) {
		this.type = type;
		this.coordinates = coordinates;
	}

	/**
	 * Devuelve el tipo de geometr�a como cadena.
	 * @return String type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Establece el tipo de geometr�a como cadena.
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Devuelve las coordenadas.
	 * @return float[] coordinates
	 */
	public float[] getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Establece las coordenadas.
	 * @param coordinates
	 */
	public void setCoordinates(float[] coordinates) {
		this.coordinates = coordinates;
	}

	/** Devuelve en formato de cadena este objeto siguiendo el patr�n JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\"type\":\"" + type + "\", \"coordinates\":" + Arrays.toString(coordinates);
	}
	
}