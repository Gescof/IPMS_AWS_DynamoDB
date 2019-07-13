package com.amazonaws.es.upm.etsisi.entities.mota;

/**
 * Representa las medidas que contiene una traza.
 * @author Guillermo, Yan Liu
 */
public class Measures {
	private Member temperature;
	private Member humidity;
	private Member luminosity;
	
	/**
	 * <p>Establece las unidades de medida para cada Member:
	 * <li>Member temperature ("Cº")</li>
	 * <li>Member humidity ("RH")</li>
	 * <li>Member luminosity ("lx")</li>
	 * </p>
	 */
	public Measures() {
		temperature = new Member();
		temperature.setUnit("C");
		humidity = new Member();
		humidity.setUnit("RH");
		luminosity = new Member();
		luminosity.setUnit("lx");
	}
	
	public Measures(Member temperature, Member humidity, Member luminosity) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.luminosity = luminosity;
	}

	/**
	 * Devuelve la temperatura como objeto de tipo Member.
	 * @return Member temperature
	 * @see Member
	 */
	public Member getTemperature() {
		return temperature;
	}
	
	/**
	 * Establece la temperatura como objeto Member.
	 * @param temperature
	 * @see Member
	 */
	public void setTemperature(Member temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * Devuelve la humedad como objeto de tipo Member.
	 * @return Member humidity
	 * @see Member
	 */
	public Member getHumidity() {
		return humidity;
	}
	
	/**
	 * Establece la humedad como objeto Member.
	 * @param humidity
	 * @see Member
	 */
	public void setHumidity(Member humidity) {
		this.humidity = humidity;
	}
	
	/**
	 * Devuelve la luminosidad como objeto de tipo Member.
	 * @return Member luminosity
	 * @see Member
	 */
	public Member getLuminosity() {
		return luminosity;
	}
	
	/**
	 * Establece la luminosidad como objeto Member.
	 * @param luminosity
	 * @see Member
	 */
	public void setLuminosity(Member luminosity) {
		this.luminosity = luminosity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((humidity == null) ? 0 : humidity.hashCode());
		result = prime * result + ((luminosity == null) ? 0 : luminosity.hashCode());
		result = prime * result + ((temperature == null) ? 0 : temperature.hashCode());
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
		Measures other = (Measures) obj;
		if (humidity == null) {
			if (other.humidity != null)
				return false;
		} else if (!humidity.equals(other.humidity))
			return false;
		if (luminosity == null) {
			if (other.luminosity != null)
				return false;
		} else if (!luminosity.equals(other.luminosity))
			return false;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		return true;
	}

	/** Devuelve en formato de cadena este objeto siguiendo el patrón JSON.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{\"temperature\": " + temperature 
				+ ", \"humidity\": " + humidity 
				+ ", \"luminosity\": " + luminosity + "}";
	}
	
}
