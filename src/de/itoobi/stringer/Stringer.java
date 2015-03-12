/*
 * Created by Tobias Gläser of itoobi.de
 * Taken from http://itoobi.de/?page_id=319
 */
package de.itoobi.stringer;

public class Stringer {

	/**
	 * @Author Tobias Gläser used to generate brute-force strings
	 */

	public static final char[] CHARSET_CHARONLYLOWER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	public static final char[] CHARSET_CHARONLY = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	public static final char[] CHARSET_LETTERANDNUMBERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	public static final char[] CHARSET_GERLETTERANDNUMBERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ü', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ö', 'ä', 'ü', 'ß', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', '0' };
	public static final char[] CHARSET_ALL = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ü', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ö', 'ä', 'ü', 'ß', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '!',
			'"', '§', '$', '%', '&', '/', '(', ')', '=', '?' };
	private boolean first = true;
	private char[] charset;
	private String string;
	private int maxid;
	public long generated = 0;

	public Stringer(String arg) {
		boolean ok = false;
		if (arg.equalsIgnoreCase("all")) {
			this.charset = CHARSET_ALL;
			ok = true;
		}
		if (arg.equalsIgnoreCase("charlower")) {
			this.charset = CHARSET_CHARONLYLOWER;
			ok = true;
		}
		if (arg.equalsIgnoreCase("ger")) {
			this.charset = CHARSET_GERLETTERANDNUMBERS;
			ok = true;
		}
		if (arg.equalsIgnoreCase("charonly")) {
			this.charset = CHARSET_CHARONLY;
			ok = true;
		}
		if (!ok) {
			this.charset = CHARSET_LETTERANDNUMBERS;
		}
		string = String.valueOf(charset[0]);
		maxid = charset.length - 1;
	}

	public String nextString() {
		generated++;
		if (!first) {
			this.string = addOne(string);
			return this.string;
		} else {
			first = false;
			return string;
		}
	}

	public int getCharsetCount() {
		return charset.length;
	}
	
	public void setCurrent(String x) {
		this.string = x;
	}

	public String getCurrent() {
		return this.string;
	}

	private String addOne(String s) {
		char[] old = s.toCharArray();
		int cid = getID(old[old.length - 1]);
		if (cid < maxid) {
			//normal case
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < old.length - 1; i++) {
				b.append(old[i]);
			}
			b.append(getById(cid + 1));
			return b.toString();
		} else {
			//increase case
			boolean total = true;
			int cs = 1;
			p: for (; cs < old.length; cs++) {
				cid = getID(old[old.length - (cs + 1)]);
				if (cid < maxid) {
					total = false;
					break p;
				}
			}
			if (total) {
				int nl = old.length + 1;
				StringBuilder b = new StringBuilder();
				for (int i = 0; i < nl; i++) {
					b.append(getById(0));
				}
				return b.toString();
			} else {
				StringBuilder b = new StringBuilder();
				int needed = old.length;
				int caseid = old.length - cs;
				for (int i = 0; i < caseid - 1; i++) {
					b.append(old[i]);
					needed--;
				}
				b.append(getById(getID(old[caseid - 1]) + 1));
				needed--;
				for (int i = 0; i < needed; i++) {
					b.append(getById(0));
				}
				return b.toString();
			}
		}
	}

	private char getById(int id) {
		return charset[id];
	}

	private int getID(char c) {
		for (int i = 0; i < charset.length; i++) {
			if ((int) charset[i] == (int) c) {
				return i;
			}
		}
		return -1;
	}
}