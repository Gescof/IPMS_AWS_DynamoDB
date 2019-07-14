package com.amazonaws.es.upm.etsisi.entities.mota;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Representa una fecha que tenga una traza.
 * @author Guillermo, Yan Liu
 *
 */
public class Timestamp {
	@JsonProperty("$date")
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private Date date;

	public Timestamp()
	{
		this.date = new Date();
	}
	
	public Timestamp(Date date) {
		this.date = date;
	}

	/**
	 * Devuelve la fecha.
	 * @return Date date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Establece la fecha.
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/** Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"$date\": \"" + date + "\"}";
	}
}
