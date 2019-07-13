package com.amazonaws.es.upm.etsisi.entities.omtraza;

import java.util.Arrays;

/**
 * Representa la geometría que contiene una traza.
 * @author Guillermo, Yan Liu
 */
public class Geometry implements ResulType {
	private String type;
	private float[] coordinates;
	
	public Geometry() {}
	
	public Geometry(String type, float[] coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}
	
	/**
	 * Devuelve el tipo de geometría como cadena.
	 * @return String type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Establece el tipo de geometría como cadena.
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coordinates);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geometry other = (Geometry) obj;
		if (!Arrays.equals(coordinates, other.coordinates))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/**
	 * Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @see es.upm.syst.IoT.entities.mota.ResulType#toString()
	 * @return String
	 */
	@Override
	public String toString() {
		return "\"type\": " + type 
				+ ", \"coordinates\": " + Arrays.toString(coordinates);
	}
	
}
