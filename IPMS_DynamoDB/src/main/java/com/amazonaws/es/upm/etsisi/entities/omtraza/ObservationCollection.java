package com.amazonaws.es.upm.etsisi.entities.omtraza;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa una colección de observaciones.
 * @author Guillermo, Yan Liu
 */
@JsonIgnoreProperties("Observation-Collection")
public class ObservationCollection {
	@JsonProperty("id")
	private String id;
	private Timestamp phenomenomTime;
	private List<OmMember> members;
	
	public ObservationCollection() {
		phenomenomTime = new Timestamp();
		members = new ArrayList<OmMember>();
	}
	
	public ObservationCollection(String id, Timestamp phenomenomTime, List<OmMember> members) {
		super();
		this.id = id;
		this.phenomenomTime = phenomenomTime;
		this.members = members;
	}

	/**
	 * Devuelve el identificador como cadena.
	 * @return String id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Establece el identificador como cadena.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Devuelve la fecha de observación del fenómeno como Timestamp.
	 * @return Timestamp phenomenomTime
	 * @see Timestamp
	 */
	public Timestamp getPhenomenomTime() {
		return phenomenomTime;
	}
	
	/**
	 * Establece la fecha de observación del fenómeno como Timestamp.
	 * @param phenomenomTime
	 */
	public void setPhenomenomTime(Timestamp phenomenomTime) {
		this.phenomenomTime = phenomenomTime;
	}
	
	/**
	 * Devuelve la lista de miembros de la colección como ArrayList<OMMember>.
	 * @return ArrayList<OMMember> members
	 * @see OmMember
	 */
	public List<OmMember> getMembers() {
		return members;
	}
	
	/**
	 * Establece la lista de miembros de la colección como ArrayList<OMMember>.
	 * @param members
	 * @see OmMember
	 */
	public void setMembers(List<OmMember> members) {
		this.members = members;
	}
	
	/** Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  "{\"phenomenomTime\": " + phenomenomTime + ", \"members\": " + members + ""
				+ "}";
	}
	
}
