package com.amazonaws.es.upm.etsisi.entities.mota;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa el resultado de un miembro de una traza.
 * @author Guillermo, Yan Liu
 */
public class Member {
	@JsonProperty("value")
	private float value;
	@JsonProperty("unit")
	private String unit;
	
	public Member() {}
	
	public Member(float value, String unit) {
		super();
		this.value = value;
		this.unit = unit;
	}
	
	/**
	 * Devuelve el valor de este objeto.
	 * @return float value
	 */
	public float getValue() {
		return value;
	}
	
	/**
	 * Establece el valor para este objeto.
	 * @param value
	 */
	public void setValue(float value) {
		this.value = value;
	}
	
	/**
	 * Devuelve la unidad de medida como cadena.
	 * @return String unit
	 */
	public String getUnit() {
		return unit;
	}
	
	/**
	 * Establece la unidad de medida como cadena.
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + Float.floatToIntBits(value);
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
		Member other = (Member) obj;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}

	/** Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"value\": " + value 
				+ ", \"unit\": \"" + unit + "\"}";
	}
	
}
