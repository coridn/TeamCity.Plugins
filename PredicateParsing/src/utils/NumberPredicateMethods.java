package utils;

public class NumberPredicateMethods
{
	public static Number convertLengthToFeet(Number value, Number unit)
	{
		return 1;
	}
	
	public static Number convertLengthTypeToEnumValue(Number value)
	{
		return 1;
	}
	
	public static Number convertPressureToBar(Number value, Number unit)
	{
		if(unit.intValue() == 40)
		{
			return value;
		}
		else return value;
	}
}
