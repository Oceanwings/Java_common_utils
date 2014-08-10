package com.erich.util;

import java.util.Calendar;

public final class CalendarUtil
{
	private static final int LENGTH_DATE_STRING = 10;
	private static final int LENGTH_TIME_STRING = 20;

	public static final int MONTH_JAN = Calendar.JANUARY + 1;
	public static final int MONTH_DEC = Calendar.DECEMBER + 1;

	private static final String ERROR_MSG_YEAR = "Invalid value of year: ";
	private static final String ERROR_MSG_MONTH = "Invalid value of month: ";

	private CalendarUtil( )
	{}

	public static int getCurrentYear( )
	{
		return Calendar.getInstance( ).get( Calendar.YEAR );
	}

	public static int getCurrentMonth( )
	{
		return Calendar.getInstance( ).get( Calendar.MONTH ) + 1;
	}

	public static String getSubmitTimeString( )
	{
		StringBuilder buffer = new StringBuilder( LENGTH_TIME_STRING );

		Calendar calendar = Calendar.getInstance( );
		buffer.append( calendar.get( Calendar.YEAR ) );
		buffer.append( calendar.get( Calendar.MONTH ) + 1 );
		buffer.append( calendar.get( Calendar.DAY_OF_MONTH ) );
		buffer.append( calendar.get( Calendar.HOUR_OF_DAY ) );
		buffer.append( calendar.get( Calendar.MINUTE ) );
		buffer.append( calendar.get( Calendar.SECOND ) );

		return buffer.toString( ).trim( );
	}

	public static String createDateString( int year, int month )
	{
		if ( year <= 0 )
		{
			throw new IllegalArgumentException( ERROR_MSG_YEAR + year );
		}
		else if ( month < MONTH_JAN || month > MONTH_DEC )
		{
			throw new IllegalArgumentException( ERROR_MSG_MONTH + month );
		}

		StringBuilder buffer = new StringBuilder( LENGTH_DATE_STRING );
		if ( month < 10 )
		{
			buffer.append( '0' );
		}
		buffer.append( month );
		buffer.append( '/' );
		buffer.append( year );
		return buffer.toString( ).trim( );
	}

	public static boolean isPastDate( final int year, final int month )
	{
		if ( month < MONTH_JAN || month > MONTH_DEC )
		{
			throw new IllegalArgumentException( "Invalid month value: " + month );
		}

		final int currentYear = getCurrentYear( );
		final int currentMonth = getCurrentMonth( );
		return ( year < currentYear || ( ( year == currentYear ) && ( month < currentMonth ) ) );
	}
}