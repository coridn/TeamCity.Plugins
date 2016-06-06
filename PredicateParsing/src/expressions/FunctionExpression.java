package expressions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import expressions.helpers.FunctionExpressionHelper;
import keyvaluecoding.IKVCObject;
import utils.NumberPredicateMethods;
import utils.StringPredicateMethods;

public class FunctionExpression extends ExpressionBase
{
	private final Object firstParameter;
	private final String methodName;
	private final Object[] additionalParameters;
	
	public FunctionExpression(Object firstParameter, String methodName, Object ...additionalParameters)
	{
		this.firstParameter = firstParameter;
		this.methodName = methodName;
		this.additionalParameters = additionalParameters;
	}
	
	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		//Figure out the type of the initial keypath so we know what to call.
		//TODO: Check if the keypaths are other functions as well.
		Object[] params = new Object[this.additionalParameters.length + 1];
		Class<?>[] classParams = new Class[this.additionalParameters.length + 1];
		Object firstParameterValue = null;
		//It's possible that the first parameter is an embedded function
		if(this.firstParameter instanceof String)
		{
			String firstParamString = (String)this.firstParameter;
			if(firstParamString.toUpperCase().startsWith("FUNCTION"))
			{
				FunctionExpression emdeddedExp = FunctionExpressionHelper.createFromString(firstParamString);
				firstParameterValue = emdeddedExp.getValueWithObject(object);
			}
			else
			{
				firstParameterValue = object.valueForKey(firstParamString);
				if(firstParameterValue instanceof Number)
					classParams[0] = Number.class;
				else
					classParams[0] = firstParameterValue.getClass();
			}
		}
		else
		{
			firstParameterValue = this.firstParameter;
			classParams[0] = Number.class;
		}
		params[0] = firstParameterValue;
		for (int i = 0; i < additionalParameters.length; i++)
		{
			Object param = additionalParameters[i];
			if(param instanceof Number)
			{
				params[i + 1] = param;
				classParams[i + 1] = Number.class;
			}
			else
			{
				//This might be an embedded function
				String paramString = (String)param;
				Object value = null;
				if(paramString.toUpperCase().startsWith("FUNCTION("))
				{
					FunctionExpression expression = FunctionExpressionHelper.createFromString(paramString);
					value = expression.getValueWithObject(object);
				}
				else
				{
					value = object.valueForKey(paramString); 
				}
				classParams[i +1] = value.getClass();
				if(value instanceof Number)
				{
					classParams[i +1] = Number.class;
				}
				params[i + 1] = value;
			}
		}
		Object resultValue = null;
		Class<?> classToCallOn = null;
		if(firstParameterValue instanceof String)
		{
			classToCallOn = StringPredicateMethods.class;
		}
		else if(firstParameterValue instanceof Number)
		{
			classToCallOn = NumberPredicateMethods.class;
		}
		try
		{
			Method m = classToCallOn.getMethod(this.methodName, classParams);
			if(m != null)
			{
				resultValue = m.invoke(null, params);
			}
		} catch (NoSuchMethodException | 
				SecurityException | 
				IllegalAccessException | 
				IllegalArgumentException | 
				InvocationTargetException e)
		{
			throw new UnsupportedOperationException(
					String.format("Reflection failed.  Either method {0} doesn't exist or does not have the correct parameters.", 
							this.methodName), e);
		}
		return resultValue;
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public ExpressionBase getLhsExpression()
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		throw new UnsupportedOperationException("Not implemented.");
	}
}
