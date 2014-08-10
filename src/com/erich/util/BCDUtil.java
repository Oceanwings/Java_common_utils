package com.erich.util;

import java.math.BigInteger;

public final class BCDUtil
{
	private static final char ZERO = '0';

	public static final int MAX_BCD_LENGTH_FOR_LONG = 18 >> 1;

	private static final int TEN = 10;
	private static final int HUNDREN = 100;

	private static final int BITS_4 = Byte.SIZE >> 1;
	private static final int MASK_LOW_4_BITS = 0xF;

	private static final byte BCD_VALUE_9 = 0x9;

	private static final byte[ ] EMPTY_RESULT = new byte[ 0 ];

	private BCDUtil( )
	{}

	public static long decodeAsLong( final byte[ ] bcdCodeArray )
	{
		return decodeAsLong( bcdCodeArray, 0 );
	}

	public static long decodeAsLong( final byte[ ] bcdCodeArray, final int offset )
	{
		if ( ByteArrayUtil.isEmpty( bcdCodeArray ) )
		{
			throw new IllegalArgumentException( "Could not decode an empty BCD code array." );
		}

		return decodeAsLong( bcdCodeArray, offset, bcdCodeArray.length );
	}

	public static long decodeAsLong( final byte[ ] bcdCodeArray, final int offset, final int length )
	{
		checkDecodeParameter( bcdCodeArray, offset, length );

		final int end = offset + length;
		final int lastIndex = end - 1;
		long result = 0;
		for ( int i = offset; i < end; ++i )
		{
			result += bcdToInt( bcdCodeArray[ i ] );
			if ( i < lastIndex )
			{
				result *= HUNDREN;
			}
		}
		return result;
	}

	private static boolean checkDecodeParameter( final byte[ ] bcdCodeArray, final int offset,
			final int length )
	{
		if ( ByteArrayUtil.isEmpty( bcdCodeArray ) )
		{
			throw new IllegalArgumentException( "Could not decode an empty BCD code array" );
		}
		else if ( offset < 0 || offset > bcdCodeArray.length )
		{
			throw new ArrayIndexOutOfBoundsException( offset );
		}
		else if ( length <= 0 )
		{
			throw new IllegalArgumentException( "Invalid BCD code array length: " + length );
		}
		else if ( length > MAX_BCD_LENGTH_FOR_LONG )
		{
			throw new IllegalArgumentException(
					"BCD code array length is out of the range of Long." );
		}

		final int end = offset + length;
		if ( end > bcdCodeArray.length )
		{
			throw new ArrayIndexOutOfBoundsException(
					"The length is out of Array index, offset + length = " + end );
		}

		return true;
	}

	public static BigInteger decodeAsBigInteger( final byte[ ] bcdCodeArray )
	{
		return decodeAsBigInteger( bcdCodeArray, 0 );
	}

	public static BigInteger decodeAsBigInteger( final byte[ ] bcdCodeArray, final int offset )
	{
		if ( ByteArrayUtil.isEmpty( bcdCodeArray ) )
		{
			throw new IllegalArgumentException( "Could not decode an empty BCD code array." );
		}

		return decodeAsBigInteger( bcdCodeArray, offset, bcdCodeArray.length );
	}

	public static BigInteger decodeAsBigInteger( final byte[ ] bcdCodeArray, final int offset,
			final int length )
	{
		checkDecodeParameter( bcdCodeArray, offset, length );

		final int end = offset + length;
		final char[ ] digits = new char[ bcdCodeArray.length << 1 ];
		int index = 0;
		for ( int i = offset; i < end; ++i )
		{
			digits[ index++ ] = ( char ) ( getHighFourBits( bcdCodeArray[ i ] ) + ZERO );
			digits[ index++ ] = ( char ) ( getLowFourBits( bcdCodeArray[ i ] ) + ZERO );
		}

		return new BigInteger( new String( digits ) );
	}

	public static boolean isValidBCDCode( byte[ ] bcdCodeArray )
	{
		return isValidBCDCode( bcdCodeArray, 0 );
	}

	public static boolean isValidBCDCode( final byte[ ] bcdCodeArray, final int offset )
	{
		return ByteArrayUtil.isEmpty( bcdCodeArray ) ? false : isValidBCDCode( bcdCodeArray,
				offset, bcdCodeArray.length );
	}

	public static boolean isValidBCDCode( final byte[ ] bcdCodeArray, final int offset,
			final int length )
	{
		if ( ByteArrayUtil.isEmpty( bcdCodeArray ) || offset < 0 || offset > bcdCodeArray.length
				|| length <= 0 || ( offset + length ) > bcdCodeArray.length )
		{
			return false;
		}

		final int end = offset + length;
		boolean isValid = true;
		for ( int i = offset; i < end; ++i )
		{
			if ( !isValidBCDCode( bcdCodeArray[ i ] ) )
			{
				isValid = false;
				break;
			}
		}
		return isValid;
	}

	private static boolean isValidBCDCode( byte bcdCode )
	{
		return ( getHighFourBits( bcdCode ) <= BCD_VALUE_9 )
				&& ( getLowFourBits( bcdCode ) <= BCD_VALUE_9 );
	}

	private static int bcdToInt( byte bcdCode )
	{
		if ( bcdCode == 0 )
		{
			return 0;
		}

		return ( getHighFourBits( bcdCode ) * TEN + getLowFourBits( bcdCode ) );
	}

	private static byte getHighFourBits( final byte number )
	{
		return ( byte ) ( ( number >>> BITS_4 ) & MASK_LOW_4_BITS );
	}

	private static byte getLowFourBits( final byte number )
	{
		return ( byte ) ( number & MASK_LOW_4_BITS );
	}

	public static byte[ ] encodeInteger( final int intNumber )
	{
		return encodeInteger( intNumber, 0 );
	}

	public static byte[ ] encodeInteger( final int intNumber, final int size )
	{
		return encodeNumericStringCore( Integer.toString( intNumber ), size );
	}

	public static byte[ ] encodeLong( final long longNumber )
	{
		return encodeLong( longNumber, 0 );
	}

	public static byte[ ] encodeLong( final long longNumber, final int size )
	{
		return encodeNumericStringCore( Long.toString( longNumber ), size );
	}

	public static byte[ ] encodeNumber( final Number number )
	{
		return encodeNumber( number, 0 );
	}

	public static byte[ ] encodeNumber( final Number number, final int size )
	{
		if ( number == null )
		{
			return EMPTY_RESULT;
		}

		return encodeNumericStringCore( number.toString( ), 0 );
	}

	public static byte[ ] encodeNumericString( String string )
	{
		return encodeNumericString( string, 0 );
	}

	public static byte[ ] encodeNumericString( String string, final int size )
	{
		return StringUtil.isNumeric( string ) ? encodeNumericStringCore( string, size )
				: EMPTY_RESULT;
	}

	/**
	 * encodeNumericStringCore(Describe the responsibility of this method)
	 * 
	 * @param value
	 *            numeric string
	 * @param size
	 *            specified size for encoded BCD bytes. This parameter will be
	 *            ignored if its value is less 0 (include 0).
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	// FIXME: 负数？
	private static byte[ ] encodeNumericStringCore( String value, int size )
	{
		int length = value.length( );
		final int valueSize = ( length + 1 ) >> 1;
		if ( length < ( valueSize << 1 ) )
		{
			value = ZERO + value;
			++length;
		}

		if ( size <= 0 )
		{
			size = ( length + 1 ) >> 1;
		}
		else if ( ( size << 1 ) < length )
		{
			throw new IllegalArgumentException( String.format(
					"Cannot encode number %s within %d bytes", value, size ) );
		}

		final byte[ ] result = new byte[ size ];
		final int offset = size - valueSize;

		int charIndex = 0;
		for ( int i = offset; i < result.length; ++i )
		{
			charIndex = ( i - offset ) << 1;
			result[ i ] = ( byte ) ( ( ( value.charAt( charIndex ) - ZERO ) << BITS_4 ) | ( value
					.charAt( charIndex + 1 ) - ZERO ) );
		}

		return result;
	}
}