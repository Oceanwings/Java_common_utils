package com.erich.hash;

public final class HashUtil
{
	private static final int BITS_2 = 2;
	private static final int BITS_3 = 3;
	private static final int BITS_4 = 4;
	private static final int BITS_5 = 5;
	private static final int BITS_6 = 6;
	private static final int BITS_7 = 7;
	private static final int BITS_10 = 10;
	private static final int BITS_11 = 11;
	private static final int BITS_13 = 13;
	private static final int BITS_15 = 15;
	private static final int BITS_16 = 16;
	private static final int BITS_17 = 17;
	private static final int BITS_20 = 20;
	private static final int BITS_24 = 24;
	private static final int BITS_27 = 27;
	private static final int BITS_28 = 28;
	private static final int BITS_32 = 32;

	private HashUtil( )
	{}

	public static int additionHash( final String string, final int prime )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash += string.charAt( i );
		}

		return ( hash % prime );
	}

	public static int rotateHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash = ( hash << BITS_4 ) ^ ( hash >> BITS_28 ) ^ string.charAt( i );
		}

		return ( hash ^ ( hash >> BITS_10 ) ^ ( hash >> BITS_20 ) );
	}

	private static final int M_MASK = 0x8765fed1;

	public static int oneByOneHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash += string.charAt( i );
			hash += ( hash << BITS_10 );
			hash ^= ( hash >> BITS_6 );
		}
		hash += ( hash << BITS_3 );
		hash ^= ( hash >> BITS_11 );
		hash += ( hash << BITS_15 );

		return ( hash & M_MASK );
	}

	private static final int BERNSTEIN_CONSTANT = 33;// 31

	public static int bernsteinHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash *= BERNSTEIN_CONSTANT;
			hash += string.charAt( i );
		}

		return hash;
	}

	private static final int DEFAULT_SHIFT = 0;
	private static final long INIT_HASH_VALUE = 2166136261L;
	private static final int FNV_CONSTANT = 16777619;

	public static int FNV32Hash( final String string )
	{
		return FNV32Hash( string, DEFAULT_SHIFT );
	}

	public static int FNV32Hash( final String string, final int shift )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = ( int ) INIT_HASH_VALUE;
		for ( int i = 0; i < length; ++i )
		{
			hash *= FNV_CONSTANT;
			hash ^= string.charAt( i );
		}

		return ( shift == 0 ) ? hash : ( hash ^ ( hash >> shift ) & M_MASK );
	}

	public static int FNV32EnhanceHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = ( int ) INIT_HASH_VALUE;
		for ( int i = 0; i < length; ++i )
		{
			hash ^= string.charAt( i );
			hash *= FNV_CONSTANT;
		}
		hash += ( hash << BITS_13 );
		hash ^= ( hash >> BITS_7 );
		hash += ( hash << BITS_3 );
		hash ^= ( hash >> BITS_17 );
		hash += ( hash << BITS_5 );

		return hash;
	}

	private static final int RS_HASH_A = 63689;
	private static final int RS_HASH_B = 378551;

	public static int RSHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0, temp = RS_HASH_A;
		for ( int i = 0; i < length; ++i )
		{
			hash *= temp;
			hash += string.charAt( i );
			temp *= RS_HASH_B;
		}

		return ( hash & FINAL_MASK );
	}

	private static final int INIT_JS_HASH_VALUE = 1315423911;

	public static int JSHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = INIT_JS_HASH_VALUE;
		for ( int i = 0; i < length; ++i )
		{
			hash ^= ( ( hash << BITS_5 ) + string.charAt( i ) + ( hash >> BITS_2 ) );
		}

		return ( hash & FINAL_MASK );
	}

	private static final int BITS_IN_UNSIGNED_INT = 32;
	private static final int THREE_FOURTH = ( ( BITS_IN_UNSIGNED_INT << 1 ) + BITS_IN_UNSIGNED_INT ) >> BITS_2;
	private static final int ONE_EIGHTH = BITS_IN_UNSIGNED_INT >> BITS_3;
	private static final int HIGH_BITS = 0xFFFFFFFF << ( BITS_IN_UNSIGNED_INT - ONE_EIGHTH );
	private static final int FINAL_MASK = 0x7FFFFFFF;

	public static int PWJHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0, temp = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash <<= ONE_EIGHTH;
			hash += string.charAt( i );

			if ( ( temp = hash & HIGH_BITS ) != 0 )
			{
				hash = ( hash ^ ( temp >> THREE_FOURTH ) ) & ( ~HIGH_BITS );
			}
		}

		return ( hash & FINAL_MASK );
	}

	private static final int ELF_MASK = 0xF0000000;

	public static int ELFHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0, temp = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash <<= BITS_4;
			hash += string.charAt( i );

			temp = hash & ELF_MASK;
			if ( temp != 0 )
			{
				hash ^= ( temp >> BITS_24 );
				hash &= ~temp;
			}
		}

		return ( hash & FINAL_MASK );
	}

	private static final int BKDR_SEED = 131; // 31 131 1313 13131 131313 etc..

	public static int BKDRHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash *= BKDR_SEED;
			hash += string.charAt( i );
		}

		return ( hash & FINAL_MASK );
	}

	public static int SDBMHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash = string.charAt( i ) + ( hash << BITS_6 ) + ( hash << BITS_16 ) - hash;
		}

		return ( hash & FINAL_MASK );
	}

	public static int DJBHHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash = ( ( hash << BITS_5 ) + hash );
			hash += string.charAt( i );
		}

		return ( hash & FINAL_MASK );
	}

	public static int DEKHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash = ( hash << BITS_5 ) ^ ( hash >> BITS_27 );
			hash ^= string.charAt( i );
		}

		return ( hash & FINAL_MASK );
	}

	public static int APHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		final int length = string.length( );
		int hash = 0;
		for ( int i = 0; i < length; ++i )
		{
			hash ^= ( ( i & 1 ) == 0 ) ? ( ( hash << BITS_7 ) ^ string.charAt( i ) ^ ( hash >> BITS_3 ) )
					: ~( ( hash << BITS_11 ) ^ string.charAt( i ) ^ ( hash >> BITS_5 ) );
		}

		return hash;
		// return ( hash & FINAL_MASK );
	}

	public static int sampleHash( final String string )
	{
		if ( string == null || string.length( ) == 0 )
		{
			return 0;
		}

		int hash = string.hashCode( );
		hash <<= BITS_32;
		hash |= FNV32EnhanceHash( string );
		return hash;
	}

	public static int intHash( int number )
	{
		number += ~( number << BITS_15 );
		number ^= ( number >>> BITS_10 );
		number += ( number << BITS_3 );
		number ^= ( number >>> BITS_6 );
		number += ~( number << BITS_11 );
		number ^= ( number >>> BITS_16 );
		return number;
	}
}