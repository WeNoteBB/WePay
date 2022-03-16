package com.wenote.alipay.utils;

public class HexStr
{
	public static String stringToHex(String s)
	{
		byte[] stringBytes = s.getBytes();
		return HexStr.bufferToHex(stringBytes);
	}

	public static String bufferToHex(byte buffer[])
	{
		return HexStr.bufferToHex(buffer, 0, buffer.length).toUpperCase();
	}

	public static String bufferToHex(byte buffer[], int startOffset, int length)
	{
		StringBuffer hexString = new StringBuffer(2 * length);
		int endOffset = startOffset + length;
		for (int i = startOffset; i < endOffset; i++)
			HexStr.appendHexPair(buffer[i], hexString);
		return hexString.toString().toUpperCase();
	}

	public static String hexToString(String hexString)
			throws NumberFormatException
	{
		byte[] bytes = HexStr.hexToBuffer(hexString);
		return new String(bytes);
	}

	public static byte[] hexToBuffer(String hexString)
			throws NumberFormatException
	{
		int length = hexString.length();
		byte[] buffer = new byte[(length + 1) / 2];
		boolean evenByte = true;
		byte nextByte = 0;
		int bufferOffset = 0;

		if ((length % 2) == 1)
			evenByte = false;
		for (int i = 0; i < length; i++)
		{
			char c = hexString.charAt(i);
			int nibble;
			if ((c >= '0') && (c <= '9'))
				nibble = c - '0';
			else if ((c >= 'A') && (c <= 'F'))
				nibble = c - 'A' + 0x0A;
			else if ((c >= 'a') && (c <= 'f'))
				nibble = c - 'a' + 0x0A;
			else
				throw new NumberFormatException("Invalid hex digit '" + c
						+ "'.");

			if (evenByte)
			{
				nextByte = (byte) (nibble << 4);
			} else
			{
				nextByte += (byte) nibble;
				buffer[bufferOffset++] = nextByte;
			}
			evenByte = !evenByte;
		}
		return buffer;
	}

	private static void appendHexPair(byte b, StringBuffer hexString)
	{
		char highNibble = kHexChars[(b & 0xF0) >> 4];
		char lowNibble = kHexChars[b & 0x0F];
		hexString.append(highNibble);
		hexString.append(lowNibble);
	}

	/**
	 * pad to the left
	 * 
	 * @param s
	 *            - original string
	 * @param len
	 *            - desired len
	 * @param c
	 *            - padding char
	 * @return padded string
	 */
	public static String padleft(String s, int len, char c)
	{
		s = s.trim();
		if (s.length() > len)
			throw new NumberFormatException("invalid len " + s.length() + "/"
					+ len);
		StringBuffer d = new StringBuffer(len);
		int fill = len - s.length();
		while (fill-- > 0)
			d.append(c);
		d.append(s);
		return d.toString();
	}

	/**
	 * left pad with '0'
	 * 
	 * @param s
	 *            - original string
	 * @param len
	 *            - desired len
	 * @return zero padded string
	 */
	public static String zeropad(String s, int len)
	{
		return padleft(s, len, '0');
	}

	public static String longToHex(long l, int len)
	{
		return zeropad(Long.toHexString(l).toUpperCase(), len).toUpperCase();
	}

	private static final char kHexChars[] =
	{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
}
