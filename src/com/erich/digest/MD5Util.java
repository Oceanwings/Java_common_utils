package com.erich.digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.erich.util.HexUtil;

public final class MD5Util
{
	private static final String DIGEST_NAME = "MD5";

	private MD5Util( )
	{}

	/**
	 * Returns a MessageDigest for the given <code>algorithm</code>.
	 * 
	 * @param algorithm
	 *            The MessageDigest algorithm name.
	 * @return An MD5 digest instance.
	 * @throws RuntimeException
	 *             when a {@link java.security.NoSuchAlgorithmException} is
	 *             caught
	 */

	private static MessageDigest getDigest( ) throws NoSuchAlgorithmException
	{
		try
		{
			return MessageDigest.getInstance( DIGEST_NAME );
		}
		catch ( NoSuchAlgorithmException e )
		{
			throw new RuntimeException( e );
		}
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[ ] md5( byte[ ] data ) throws NoSuchAlgorithmException
	{
		return getDigest( ).digest( data );
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 16 element
	 * <code>byte[]</code>.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest
	 */
	public static byte[ ] md5( String data ) throws NoSuchAlgorithmException
	{
		return md5( data.getBytes( ) );
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex( byte[ ] data )
	{
		String result = null;
		try
		{
			result = HexUtil.encode( md5( data ) );
		}
		catch ( NoSuchAlgorithmException e )
		{
			e.printStackTrace( );
			result = new String( data );
		}
		return result;
	}

	/**
	 * Calculates the MD5 digest and returns the value as a 32 character hex
	 * string.
	 * 
	 * @param data
	 *            Data to digest
	 * @return MD5 digest as a hex string
	 */
	public static String md5Hex( String data )
	{
		String result = null;
		try
		{
			result = HexUtil.encode( md5( data ) );
		}
		catch ( NoSuchAlgorithmException e )
		{
			e.printStackTrace( );
			result = data;
		}
		return result;
	}
}