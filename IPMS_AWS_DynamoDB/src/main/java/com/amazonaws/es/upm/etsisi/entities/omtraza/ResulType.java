package com.amazonaws.es.upm.etsisi.entities.omtraza;

/**
 * Representa el tipo de resultado para una OMCollection. Puede ser un OMMember o una Geometry.
 * @author Guillermo, Yan Liu
 * @see OmMember
 * @see Geometry
 */
public interface ResulType {
	
	/**
	 * Devuelve en formato de cadena este objeto siguiendo el patrón OM-JSON.
	 * @return String
	 */
	public String toString();
	
}
