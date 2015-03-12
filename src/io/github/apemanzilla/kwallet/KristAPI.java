/*
 * Originally created by apemanzilla
 * Found at https://github.com/apemanzilla/KWallet
 */
package io.github.apemanzilla.kwallet;

import org.apache.commons.codec.digest.DigestUtils;

public class KristAPI {

	private static char numtochar(int inp) {
		for (int i = 6; i <= 251; i += 7) {
			if (inp <= i) {
				if (i <= 69) {
					return (char) ('0' + (i - 6) / 7);
				}
				return (char) ('a' + ((i - 76) / 7));
			}
		}
		return 'e';
	}

	public static String makeAddressV2(String key) {
		String[] protein = { "", "", "", "", "", "", "", "", "" };
		int link = 0;
		String v2 = "k";
		String stick = DigestUtils.sha256Hex(DigestUtils.sha256Hex(key));
		for (int i = 0; i <= 9; i++) {
			if (i < 9) {
				protein[i] = stick.substring(0, 2);
				stick = DigestUtils.sha256Hex(DigestUtils.sha256Hex(stick));
			}
		}
		int i = 0;
		while (i <= 8) {
			link = Integer.parseInt(stick.substring(2 * i, 2 + (2 * i)), 16) % 9;
			if (protein[link].equals("")) {
				stick = DigestUtils.sha256Hex(stick);
			} else {
				v2 = v2 + numtochar(Integer.parseInt(protein[link], 16));
				protein[link] = "";
				i++;
			}
		}
		return v2;
	}
}
