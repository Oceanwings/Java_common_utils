package com.erich.util;

public final class NumberConverter
{
	private static final int MASK_BYTE = 0xFF;
	private static final int MASK_HIGHEST_BYTE = 0xFF000000;

	private static final int BYTES_INTEGER = Integer.SIZE / Byte.SIZE;
	private static final int BYTES_LONG = Long.SIZE / Byte.SIZE;

	private NumberConverter( )
	{}

	private static int calculateIntByteSize( int number )
	{
		int size = BYTES_INTEGER;
		while ( ( number & MASK_HIGHEST_BYTE ) == 0 )
		{
			--size;
			number <<= Byte.SIZE;
		}

		return size;
	}

	public static byte[ ] intToBytes( final int originalNumber )
	{
		return intToBytes( originalNumber, calculateIntByteSize( originalNumber ) );
	}

	public static byte[ ] intToBytes( final int originalNumber, int size )
	{
		size = ( size <= 0 || size > BYTES_INTEGER ) ? BYTES_INTEGER : size;

		final byte[ ] result = new byte[ size ];
		int index = result.length - 1;
		int number = originalNumber;
		while ( index >= 0 && number != 0 )
		{
			result[ index-- ] = ( byte ) ( number & MASK_BYTE );
			number >>>= Byte.SIZE;
		}

		if ( number != 0 )
		{
			throw new IllegalArgumentException( String.format(
					"Could not convert int value %d into %d bytes.", originalNumber, size ) );
		}

		return result;
	}

	public static int bytesToInt( final byte[ ] bytesInt )
	{
		if ( ByteArrayUtil.isEmpty( bytesInt ) )
		{
			throw new IllegalArgumentException( "Empty byte array." );
		}

		return bytesToInt( bytesInt, 0, bytesInt.length );
	}

	public static int bytesToInt( final byte[ ] bytesInt, int offset )
	{
		if ( ByteArrayUtil.isEmpty( bytesInt ) )
		{
			throw new IllegalArgumentException( "Empty byte array." );
		}

		return bytesToInt( bytesInt, offset, BYTES_INTEGER );
	}

	public static int bytesToInt( final byte[ ] bytesInt, final int offset, int length )
	{
		if ( ByteArrayUtil.isEmpty( bytesInt ) )
		{
			throw new IllegalArgumentException( "Empty byte array." );
		}
		else if ( offset < 0 || offset > bytesInt.length )
		{
			throw new ArrayIndexOutOfBoundsException( offset );
		}
		else if ( length <= 0 )
		{
			throw new IllegalArgumentException( "Invalid length value: " + length );
		}

		length = ( length > BYTES_INTEGER ) ? BYTES_INTEGER : length;
		int end = offset + length;
		end = ( end >= bytesInt.length ) ? bytesInt.length : end;
		int result = 0;
		for ( int i = offset; i < end; ++i )
		{
			result <<= Byte.SIZE;
			result |= ( bytesInt[ i ] & MASK_BYTE );
		}
		return result;
	}

	private static int calculateLongByteSize( long number )
	{
		int size = BYTES_INTEGER;
		while ( ( number & MASK_HIGHEST_BYTE ) == 0 )
		{
			--size;
			number <<= Byte.SIZE;
		}

		return size;
	}

	public static byte[ ] longToBytes( final long originalNumber )
	{
		return longToBytes( originalNumber, calculateLongByteSize( originalNumber ) );
	}

	public static byte[ ] longToBytes( final long originalNumber, int size )
	{
		size = ( size <= 0 || size > BYTES_LONG ) ? BYTES_LONG : size;

		final byte[ ] result = new byte[ size ];
		int index = result.length - 1;
		long number = originalNumber;
		while ( index >= 0 && number != 0 )
		{
			result[ index-- ] = ( byte ) ( number & MASK_BYTE );
			number >>>= Byte.SIZE;
		}

		if ( number != 0 )
		{
			throw new IllegalArgumentException( String.format(
					"Could not convert long value %d into %d bytes.", number, size ) );
		}

		return result;
	}

	public static long bytesToLong( final byte[ ] bytesLong )
	{
		if ( ByteArrayUtil.isEmpty( bytesLong ) )
		{
			throw new IllegalArgumentException( "Empty byte array." );
		}

		return bytesToInt( bytesLong, 0, bytesLong.length );
	}

	public static long bytesToLong( final byte[ ] bytesLong, int offset )
	{
		if ( ByteArrayUtil.isEmpty( bytesLong ) )
		{
			throw new IllegalArgumentException( "Empty byte array." );
		}

		return bytesToInt( bytesLong, offset, BYTES_LONG );
	}

	public static long bytesToLong( final byte[ ] bytesLong, final int offset, int length )
	{
		if ( ByteArrayUtil.isEmpty( bytesLong ) )
		{
			throw new IllegalArgumentException( "Empty byte array." );
		}
		else if ( offset < 0 || offset > bytesLong.length )
		{
			throw new ArrayIndexOutOfBoundsException( offset );
		}
		else if ( length <= 0 )
		{
			throw new IllegalArgumentException( "Invalid length value: " + length );
		}

		length = ( length > BYTES_LONG ) ? BYTES_LONG : length;
		int end = offset + length;
		end = ( end >= bytesLong.length ) ? bytesLong.length : end;
		long result = 0;
		for ( int i = offset; i < end; ++i )
		{
			result <<= Byte.SIZE;
			result |= ( bytesLong[ i ] & MASK_BYTE );
		}
		return result;
	}
}