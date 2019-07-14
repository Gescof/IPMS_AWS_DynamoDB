package com.amazonaws.es.upm.etsisi.entities.mota;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reperesenta una MotaMeasure.
 * @author Guillermo, Yan Liu
 */
@JsonIgnoreProperties("MotaMeasure")
public class MotaMeasure {
	@JsonProperty("mota_id")
	private String motaId;
	private Timestamp timestamp;
	private Geometry geometry;
	private Measures measures;
	
	public MotaMeasure() {
		timestamp = new Timestamp();
		geometry = new Geometry();
		measures = new Measures();
	}
	
	public MotaMeasure(String motaId, Timestamp timestamp, Geometry geometry, Measures measures) {
		super();
		this.motaId = motaId;
		this.timestamp = timestamp;
		this.geometry = geometry;
		this.measures = measures;
	}
	
	/**
	 * Devuelve el identificador como cadena.
	 * @return String MotaId
	 */
	public String getMotaId() {
		return motaId;
	}
	
	/**
	 * Establece el identificador de la mota como cadena.
	 * @param motaId
	 */
	public void setMotaId(String motaId) {
		this.motaId = motaId;
	}
	
	/**
	 * Devuelve la fecha como objeto de tipo Timestamp.
	 * @return Timestamp
	 * @see Timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Establece la fecha como objeto Timestamp.
	 * @param timestamp
	 * @see Timestamp
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Devuelve la geometría como objeto de tipo Geometry.
	 * @return Geometry
	 * @see Geometry
	 */
	public Geometry getGeometry() {
		return geometry;
	}
	
	/**
	 * Establece la geometría como objeto Geometry.
	 * @param geometry
	 * @see Geometry
	 */
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	/**
	 * Devuelve las medidas como objeto de tipo Measures.
	 * @return Measures
	 * @see Measures
	 */
	public Measures getMeasures() {
		return measures;
	}
	
	/**
	 * Establece las medidas como objeto Measures.
	 * @param measures
	 * @see Measures
	 */
	public void setMeasures(Measures measures) {
		this.measures = measures;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geometry == null) ? 0 : geometry.hashCode());
		result = prime * result + ((measures == null) ? 0 : measures.hashCode());
		result = prime * result + ((motaId == null) ? 0 : motaId.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		MotaMeasure other = (MotaMeasure) obj;
		if (geometry == null) {
			if (other.geometry != null)
				return false;
		} else if (!geometry.equals(other.geometry))
			return false;
		if (measures == null) {
			if (other.measures != null)
				return false;
		} else if (!measures.equals(other.measures))
			return false;
		if (motaId == null) {
			if (other.motaId != null)
				return false;
		} else if (!motaId.equals(other.motaId))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	/** Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"timestamp\": " + timestamp 
				+ ", \"geometry\": " + geometry + ", \"measures\": " + measures 
				+ ", \"motaId\": \"" + motaId + "\""
				+ "}";
	}
	
}
