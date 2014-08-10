package com.erich.util;

public final class CommonUtil
{
	private static final int E2 = 100;

	private CommonUtil( )
	{}

	public static long doubleToLongE2( final double number )
	{
		return ( long ) ( number * E2 );
	}

	public static long floatToLongE2( final float number )
	{
		return ( long ) ( number * E2 );
	}

	public static double longE2ToDouble( final long number )
	{
		return number / ( double ) E2;
	}

	public static float longE2ToFloat( final long number )
	{
		return number / ( float ) E2;
	}
}