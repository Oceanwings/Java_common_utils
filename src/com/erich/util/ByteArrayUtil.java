package com.erich.util;

public final class ByteArrayUtil
{
	public static final byte[ ] EMPTY_BYTE_ARRAY = new byte[ 0 ];

	private ByteArrayUtil( )
	{}

	/**
	 * 
	 * cloneByteArray(Describe the responsibility of this method)
	 * 
	 * @param source
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[ ] cloneByteArray( final byte[ ] source )
	{
		return ( source != null ) ? cloneByteArray( source, 0, source.length ) : source;
	}

	/**
	 * 
	 * cloneByteArray(Describe the responsibility of this method)
	 * 
	 * @param source
	 * @param startPos
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[ ] cloneByteArray( final byte[ ] source, int startPos )
	{
		return ( source != null ) ? cloneByteArray( source, startPos, source.length ) : source;
	}

	/**
	 * 
	 * cloneByteArray(Describe the responsibility of this method)
	 * 
	 * @param source
	 * @param startPos
	 * @param length
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[ ] cloneByteArray( final byte[ ] source, int startPos, int length )
	{
		if ( source == null || source.length == 0 )
		{
			return source;
		}
		else if ( startPos >= source.length || length == 0 )
		{
			return EMPTY_BYTE_ARRAY;
		}

		// Adjust
		startPos = ( startPos < 0 ) ? 0 : startPos;
		length = ( startPos + length >= source.length ) ? ( source.length - startPos ) : length;

		final byte[ ] result = new byte[ length ];
		System.arraycopy( source, startPos, result, 0, length );
		return result;
	}

	public static boolean isEmpty( byte[ ] array )
	{
		return ( array == null || array.length == 0 );
	}
}