/**
 * 北京钉图互动科技 all right reserver
 */
package com.commons.kafka.model;

/**
 * @author Hebing
 * @since 2018年10月16日
 */
public class ConsumerMessage {

	private String key;

	private String message;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ConsumerMessage [key=" + key + ", message=" + message + "]";
	}
}
