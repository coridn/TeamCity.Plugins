package expressions.helpers;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import expressions.FunctionExpression;

public class FunctionExpressionHelper
{
	public static FunctionExpression createFromString(String functionString)
	{
		//Remove FUNCTION( from the beginning and the last )
		int functionParenLength = "FUNCTION(".length();
		functionString = functionString.substring(functionParenLength, functionString.length());
		functionString = functionString.substring(0, functionString.length() - 1);
		//Walk the string to find the parameters.
		int startIndex = 0;
		int endIndex = readUntilCondition(startIndex, functionString, (ch) ->
		{
			return ch == ',';
		});
		String firstParameterString = functionString.substring(startIndex, endIndex).trim();
		Object firstParameter = null;
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition p = new ParsePosition(0);
		Number firstParamNumber = formatter.parse(firstParameterString, p);
		if(p.getErrorIndex() == -1)//If this fails, then it must be a key path.
		{
			firstParameter = firstParamNumber;
		}
		else
		{
			firstParameter = firstParameterString;
		}
		startIndex = ++endIndex;//increment to skip the comma
		endIndex = readUntilCondition(startIndex, functionString, (ch) -> 
		{
			return ch == ',';
		});
		String methodName = functionString.substring(startIndex, endIndex).trim().replace("'", "").replace("\"", "").replace(":", "");
		List<Object> params = new ArrayList<>();
		//Is there anthing left?
		if(functionString.length() != endIndex)
		{
			startIndex = ++endIndex;
			//The remainder is the additional parameters.
			String remainingParams = functionString.substring(endIndex).trim();
			startIndex = 0;
			while (remainingParams.length() != 0)
			{
				if(remainingParams.startsWith("FUNCTION"))
				{
					//special function checks
					int[] numberOfOpenParens = new int[]{0};
					endIndex = readUntilCondition(startIndex + 7, remainingParams, (ch) ->
					{
						if(ch == '(')
							numberOfOpenParens[0]++;
						else if(ch == ')')
							numberOfOpenParens[0]--;
						
						return (ch != ')' && numberOfOpenParens[0] == 0);
					});
					String paramFunctionString = remainingParams.substring(startIndex, endIndex);
					params.add(paramFunctionString);
				}
				else
				{
					//read up unto the next comma
					endIndex = readUntilCondition(startIndex, remainingParams, (ch) ->
					{
						return ch == ',';
					});
					String param = remainingParams.substring(startIndex, endIndex).trim();
					p = new ParsePosition(0);
					Number num = formatter.parse(param, p);
					if(p.getErrorIndex() == -1)
					{
						params.add(num);
					}
					else
					{
						params.add(param);
					}
				}
				
				remainingParams = remainingParams.substring(endIndex, remainingParams.length());
			}
		}
		return new FunctionExpression(firstParameter, methodName, params.toArray());
	}
	
	private static int readUntilCondition(int startAt, String stringToRead, Function<Character, Boolean> condition) 
	{
		int endIndex = startAt;
		while(true) 
		{
			if(++startAt == stringToRead.length())
			{
				//If adding one to this would cause it to be the same as the length, just return
				return startAt;
			}
			char c = stringToRead.charAt(startAt);
			if(condition.apply(c)) 
			{
				endIndex = startAt;
				break;
			}
		}
		
		return endIndex;
	}
}
