package com.erich.util;

/**
 * This class provides convenient functions to convert hex string to byte array
 * and vice versa.
 * 
 */
public class HexUtil
{
	private static final char[ ] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };

	private static final int BITS_4 = 4;

	public static final int RADIX_HEX = 16;

	private static final byte MASK_LOW_4_BITS = 0x0F;

	private HexUtil( )
	{}

	/**
	 * Converts a byte array to hex string.
	 * 
	 * @param byteArray
	 *            - the input byte array
	 * @return hex string representation of byteArray.
	 */
	public static String encode( final byte[ ] byteArray )
	{
		return encode( byteArray, 0 );
	}

	public static String encode( final byte[ ] byteArray, final int offset )
	{
		if ( ByteArrayUtil.isEmpty( byteArray ) )
		{
			throw new IllegalArgumentException( "Could not encode an empty byte array" );
		}

		return encode( byteArray, offset, byteArray.length );
	}

	public static String encode( final byte[ ] byteArray, final int offset, final int length )
	{
		if ( ByteArrayUtil.isEmpty( byteArray ) )
		{
			throw new IllegalArgumentException( "Could not encode an empty byte array" );
		}
		else if ( offset < 0 || offset > byteArray.length )
		{
			throw new ArrayIndexOutOfBoundsException( offset );
		}

		final int end = offset + length;
		if ( end > byteArray.length )
		{
			throw new ArrayIndexOutOfBoundsException(
					"The length is out of Array index, offset + length = " + end );
		}

		StringBuilder buffer = new StringBuilder( );
		for ( int i = offset; i < end; i++ )
		{
			buffer.append( HEX_CHARS[ byteArray[ i ] >>> BITS_4 & MASK_LOW_4_BITS ] );
			buffer.append( HEX_CHARS[ byteArray[ i ] & MASK_LOW_4_BITS ] );
		}
		return buffer.toString( );
	}

	/**
	 * Converts a hex string into a byte array.
	 * 
	 * @param hexString
	 *            - string to be converted
	 * @return byte array converted from s
	 */
	public static byte[ ] decode( String hexString )
	{
		if ( !isHexString( hexString ) )
		{
			throw new IllegalArgumentException( "This is not an HEX string: " + hexString );
		}

		byte[ ] buf = new byte[ hexString.length( ) >> 1 ];
		int j = 0;
		for ( int i = 0; i < buf.length; i++ )
		{
			buf[ i ] = ( byte ) ( ( Character.digit( hexString.charAt( j++ ), RADIX_HEX ) << BITS_4 ) | Character
					.digit( hexString.charAt( j++ ), RADIX_HEX ) );
		}
		return buf;
	}

	public static boolean isHexString( String str )
	{
		if ( str == null || str.length( ) == 0 )
		{
			return false;
		}

		final int length = str.length( );
		boolean isHex = true;
		for ( int i = 0; i < length; i++ )
		{
			if ( Character.digit( str.charAt( i ), RADIX_HEX ) == -1 )
			{
				isHex = false;
				break;
			}
		}
		return isHex;
	}
}