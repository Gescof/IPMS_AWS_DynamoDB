package com.amazonaws.es.upm.etsisi.entities.omtraza;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa el resultado de un miembro de una traza.
 * @author Guillermo, Yan Liu
 */
public class Member implements ResulType {
	@JsonProperty("value")
	private float value;
	@JsonProperty("uom")
	private String uom;
	
	public Member() {}
	
	public Member(float value, String uom) {
		super();
		this.value = value;
		this.uom = uom;
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
	public String getUom() {
		return uom;
	}
	
	/**
	 * Establece la unidad de medida como cadena.
	 * @param uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uom == null) ? 0 : uom.hashCode());
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
		if (uom == null) {
			if (other.uom != null)
				return false;
		} else if (!uom.equals(other.uom))
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}

	/**
	 * Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @see es.upm.syst.IoT.entities.mota.ResulType#toStringOM()
	 * @return String
	 */
	@Override
	public String toString() {
		return "\"value\": " + value 
				+ ", \"uom\": \"" + uom + "\"";
	}
	
}
